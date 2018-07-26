package com.ava.frame.abnf.element.basic;

import com.ava.frame.abnf.antlr4.AbnfLexer;
import com.ava.frame.abnf.antlr4.AbnfParser;
import com.ava.frame.abnf.antlr4.AbnfParser.*;
import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.EntityRule;
import com.ava.frame.abnf.element.Recognition;
import com.ava.frame.abnf.service.AbsVarRuleService;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class for fuzz testing. Instantiate this class with a set of ABNF rules
 * and then call one of the {@code generate} methods to create random output
 * suitable for use in a test.
 *
 * @author Nick Radov
 */
public class AbnfFuzzer {
    private static Logger log = LoggerFactory.getLogger(AbnfFuzzer.class);
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -5734957539715862213L;
    private transient Random random;
    private AbsVarRuleService varRuleService;

    /**
     * Map of rule names to their elements.
     */
    private final Map<String, Rule> ruleList = new RuleList();

    /**
     * 近义词 列表
     */
    private final Map<String, String> synWordMap = new HashMap<>();

    public Collection<Rule> getRules() {
        return ruleList.values();
    }

    /**
     * 添加正则表达规则模板文件
     *
     * @throws Exception
     */
    public void addSynWords(final InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String regex;
        while ((regex = reader.readLine()) != null) {
            addSynWord(regex);
        }
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 添加一条正则表达模板
     *
     * @param regex
     * @throws Exception
     */
    public void addSynWord(final String regex) throws Exception {
        String[] arr = regex.split("[\t\\s]+");
        if (arr.length < 2) return;
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            value.append(arr[i]).append("|");
        }
        synWordMap.put(arr[0], value.substring(0, value.length() - 1));
    }

    /**
     * 添加一条文法规则
     *
     * @param rule
     * @throws java.io.IOException
     */
    public void addRules(final String rule) throws IOException {
        this.addRules(new StringReader(rule));
    }

    public void delRule(final String ruleName) throws IOException {
        this.ruleList.remove(ruleName);
    }

    /**
     * 添加文法规则文档
     *
     * @param is
     * @throws java.io.IOException
     */
    public void addRules(final InputStream is) throws IOException {
        this.addRules(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    /**
     * 添加文法规则文档
     *
     * @param rules
     * @throws java.io.IOException
     */
    public void addRules(final Reader rules) throws IOException {
        final AbnfLexer l = new AbnfLexer(new ANTLRInputStream(rules));
        final CommonTokenStream tokens = new CommonTokenStream(l);
        final AbnfParser p = new AbnfParser(tokens);

        p.setBuildParseTree(true);

        final ParseTree tree = p.rulelist();
        ruleList.putAll(
//                Collections.unmodifiableMap(
                new RuleList() {
                    {
                        for (int i = 0; i < tree.getChildCount(); i++) {
                            final ParseTree child = tree.getChild(i);
                            if (child instanceof Rule_Context) {
                                if (child.getChildCount() == 3) {
                                    // rule definition
                                    final ParseTree name = child.getChild(0);
                                    if (!(name instanceof TerminalNode)) {
                                        throw new IllegalArgumentException();
                                    }
                                    final ParseTree equalSign = child.getChild(1);
                                    if (!(equalSign instanceof TerminalNode)
                                            || !"=".equals(equalSign.toString())) {
                                        throw new IllegalArgumentException();
                                    }
                                    final ParseTree elements = child.getChild(2);
                                    if (!(elements instanceof ElementsContext)) {
                                        throw new IllegalArgumentException();
                                    }
                                    put(name.toString(),
                                            new Rule(name.toString(), (ElementsContext) elements));
                                }
                            }
                        }
                    }
                }
//        )
        );
    }

    /**
     * Generate a random sequence of bytes which matches a named ABNF rule. The
     * output will be suitable for use in a fuzz test.
     *
     * @param ruleName ABNF rule name
     * @return random sequence which matches the specified rule
     * @throws IllegalArgumentException if {@code ruleName} doesn't exist
     * @throws IllegalStateException    if any defined rule references another rule which doesn't
     *                                  exist
     * @see #generate(String, java.util.Set)
     */
    public byte[] generate(final String ruleName) {
        return generate(ruleName, Collections.<String>emptySet());
    }

    /**
     * Generate a random sequence of characters which matches a named ABNF rule.
     * The output will be suitable for use in a fuzz test.
     *
     * @param ruleName ABNF rule name
     * @param exclude  ABNF rule names to exclude during alternative selection; this
     *                 allows for testing code that implements only a subset of an
     *                 RFC
     * @return random sequence of characters which matches the specified rule
     * encoded in the US_ASCII character set
     * @throws IllegalArgumentException if {@code ruleName} doesn't exist
     * @throws IllegalStateException    if any defined rule references another rule which doesn't
     *                                  exist
     */
    public String generateAscii(final String ruleName,
                                final Set<String> exclude) {
        return generate(ruleName, exclude, StandardCharsets.US_ASCII);
    }

    /**
     * Generate a random sequence of characters which matches a named ABNF rule.
     * The output will be suitable for use in a fuzz test.
     *
     * @param ruleName ABNF rule name
     * @return random sequence of characters which matches the specified rule
     * encoded in the US_ASCII character set
     * @throws IllegalArgumentException if {@code ruleName} doesn't exist
     * @throws IllegalStateException    if any defined rule references another rule which doesn't
     *                                  exist
     */
    public String generateAscii(final String ruleName) {
        return generateAscii(ruleName, Collections.<String>emptySet());
    }

    /**
     * Generate a random sequence of bytes which matches a named ABNF rule. The
     * output will be suitable for use in a fuzz test.
     *
     * @param ruleName ABNF rule name
     * @param exclude  rule names to exclude when generating output
     * @return random sequence of bytes which matches the specified rule
     * @throws IllegalArgumentException if {@code ruleName} doesn't exist
     * @throws IllegalStateException    if any defined rule references another rule which doesn't
     *                                  exist
     */
    public byte[] generate(final String ruleName, final Set<String> exclude) {
        return getRule(ruleName).generate(this, getRandom(), exclude);
    }

    /**
     * 匹配某条规则
     *
     * @param ruleName
     * @param recognition
     * @return
     */
    public ElementNode match(String ruleName, Recognition recognition) {
        ElementNode node = new ElementNode(getRule(ruleName), recognition.getParam(), recognition.getEntitiesTemp());
        recognition.setMatch(node.match(this)&& recognition.onlyUseless(node.lastWords()));
        recognition.setEntities(node.getEntities());
        recognition.setRules(node.getRules());
        recognition.setIndex(node.getMatchWords().length());
        return node;
    }

    /**
     * Generate a random sequence of characters which matches a named ABNF rule.
     * The output will be suitable for use in a fuzz test.
     *
     * @param ruleName ABNF rule name
     * @param exclude  rule names to exclude when generating output
     * @param charset  encoding for the return value
     * @return random sequence of characters which matches the specified rule
     * encoded in the specified character set
     * @throws IllegalArgumentException if {@code ruleName} doesn't exist
     * @throws IllegalStateException    if any defined rule references another rule which doesn't
     *                                  exist
     */
    public String generate(final String ruleName, final Set<String> exclude,
                           final Charset charset) {
        return new String(generate(ruleName, exclude), charset);
    }

    // built in rules

    @SuppressWarnings("serial")
    private static final Map<String, Rule> BUILT_IN_RULES = Collections
            .unmodifiableMap(new HashMap<String, Rule>() {
                {
                    put("DIGIT", new Digit());

                }
            });
    private Map<String, EntityRule> entityRules = new HashMap<>();

    /**
     * Get a defined ABNF rule.
     *
     * @param ruleName rule name (could be one of the core rules defined in RFC 5234)
     * @return the rule
     * @throws IllegalArgumentException if the rule name isn't defined
     */
    public Rule getRule(final String ruleName) {
        if (BUILT_IN_RULES.containsKey(ruleName)) {
            return BUILT_IN_RULES.get(ruleName);
        } else if (ruleList.containsKey(ruleName)) {
            return ruleList.get(ruleName);
        } else if (entityRules.containsKey(ruleName)) {
            return entityRules.get(ruleName);
        } else if (ruleName.contains("en_")) {
            synchronized (this) {
                if (!entityRules.containsKey(ruleName))
                    entityRules.put(ruleName, new EntityRule(ruleName.replaceFirst("en_", "")));
            }
            return entityRules.get(ruleName);
        }

        throw new IllegalArgumentException("no rule \"" + ruleName + "\"");
    }

    /**
     * Get the random number generator used to pick between options and
     * alternatives.
     *
     * @return random number generator
     * @see #setRandom(java.util.Random)
     */
    public Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    /**
     * Set the random number generator used to pick between alternatives. If
     * this isn't set then a default implementation will be used.
     *
     * @param r random number generator
     * @see #getRandom()
     */
    public void setRandom(final Random r) {
        if (r == null) {
            throw new IllegalArgumentException("null");
        }
        random = r;
    }

    public List<Entity> matchEntity(Map<String, List<Entity>> temp, String param, String labelType) {
        return varRuleService.match(temp, param, labelType);
    }

    /**
     * 实体规则前 有部分* 适用于 正则表达式
     *
     * @param labelType
     * @return
     */
    public List<Entity> matchRegexEntity4AllParam(ElementNode node, String labelType) {
        return varRuleService.matchRegexEntity4AllParam(node.getEntitiesTemp(), node.lastWords(), labelType);
    }


    public void clearRules() {
        ruleList.clear();
    }

    public Map<String, String> getSynWordMap() {
        return synWordMap;
    }

    public void setVarRuleService(AbsVarRuleService varRuleService) {
        this.varRuleService = varRuleService;
    }
}
