package com.ava.frame.abnf.element.basic;


import com.ava.frame.abnf.antlr4.AbnfParser;
import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.Recognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rule definition.
 *
 * @author Nick Radov
 * @see <a href="https://tools.ietf.org/html/rfc5234#section-2" target="_"> IETF
 * RFC 5234: 2. Rule Definition</a>
 */
public class Rule extends Element {
    private transient String ruleName;


    /**
     * Create a new {@code Rule}.
     */
    protected Rule() {

    }

    protected Rule(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * Create a new rule from an ANTLR context.
     *
     * @param elements ANTLR context
     */
    public Rule(String ruleName, final AbnfParser.ElementsContext elements) {
        super(elements);
        this.ruleName = ruleName;
    }

    @Override
    public boolean match(final AbnfFuzzer f, Recognition recognition) {

        List<Entity> oldEntity=new ArrayList<>();
        oldEntity.addAll(recognition.getEntities());
        Map<String, String> oldRule = new HashMap<String, String>();
        oldRule.putAll(recognition.getRules());

        boolean match = true;
        StringBuilder words = new StringBuilder();

        for (Element e : elements) {
            ElementNode node = new ElementNode(e, recognition.getIndex());
            match = match && node.match(f, recognition);
            if (!match) break;
            words.append(node.getWords());
        }
//
        recognition.setLastParamMatch(words.toString());
//      更新cache
        List<Entity> newEntity=new ArrayList<>();
        newEntity.addAll(recognition.getEntities());
        for (Entity e:oldEntity){
            newEntity.remove(e);
        }
        Map<String, String> newRule = new HashMap<String, String>();
        newRule.putAll(recognition.getRules());
        for (String key:oldRule.keySet()){
            if (newRule.get(key).equals(oldRule.get(key)))newRule.remove(key);
        }

        return match;
    }


}
