package com.ava.frame.abnf.element.basic;

import com.ava.frame.abnf.domain.Entity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by redredava on 2018/7/25.
 * email:zhyx2014@yeah.net
 */
public class Regex extends Element {
    //    正则匹配 参数过滤
    private Pattern pattern = Pattern.compile("(?<=\\{\\{)(.+?)(?=\\}\\})");

    @Override
    public boolean match(AbnfFuzzer f, ElementNode fatherNode) {
        //        正则表达式参数替换
        String regex = fatherNode.getRegex();
        String str = fatherNode.lastWords();
        Matcher matcher = pattern.matcher(regex);
        ElementNode node = null;
        while (matcher.find()) {
            String newRegex = matcher.group();
//            同义词
            String matchName = f.getSynWordMap().get(newRegex);
            if (StringUtils.isEmpty(matchName)) {
//                实体label
                node = new ElementNode(f.getRule(newRegex), fatherNode.lastWords(), fatherNode.getEntitiesTemp());
                matchName = matchRegexEntity(f, node, newRegex);
                if (StringUtils.isEmpty(matchName)) {
//                    文法规则名
                    node = new ElementNode(f.getRule(newRegex), fatherNode.lastWords(), fatherNode.getEntitiesTemp());
                    matchName = matchAbnf(f, node, newRegex);
                }
            }
            if (StringUtils.isEmpty(matchName)) return false;
            matchName = matchName.replace("+", "\\\\+");
            regex = regex.replaceAll("\\{\\{" + newRegex + "\\}\\}", "(" + matchName + ")");
        }
        Matcher matcher1 = Pattern.compile(regex).matcher(str);
        if (matcher1.find()) {
            fatherNode.addMatchWords(matcher1.group());
            if (node != null && node.isMatch()) {
                fatherNode.addRuleAndEntity(node);
            }
            return true;
        }
        return false;
    }

    /**
     * 正则中的实体替换
     *
     * @param newRegex
     * @return
     */
    private String matchRegexEntity(AbnfFuzzer f, ElementNode node, String newRegex) {
        List<Entity> list = f.matchRegexEntity4AllParam(node, newRegex);
        if (CollectionUtils.isEmpty(list)) return null;
//        多个匹配，返回最近的一个
        Entity entity = list.get(0);
        if (list.size() > 1) {
            String param = node.lastWords();
            int indexMin = param.indexOf(entity.getMatchName());
            for (Entity one : list) {
                if (one.getMatchName() == null) continue;
                String matchName = one.getMatchName();
                int index = param.indexOf(matchName);
                if (index < indexMin) {
                    indexMin = index;
                    entity = one;
                }
            }
        }
        node.addEntity(entity);
        node.setMatch(true);
        return entity.getMatchName();
    }

    /**
     * 正则中的规则匹配
     *
     * @param newRegex
     * @return
     */
    private String matchAbnf(AbnfFuzzer f, ElementNode fatherNode, String newRegex) {
        try {
            String str = fatherNode.lastWords();
            for (int i = 0; i < str.length(); i++) {
                String param = str.substring(i, str.length());
                ElementNode node = new ElementNode(f.getRule(newRegex), param, fatherNode.getEntitiesTemp());
                if (node.match(f)) {
                    fatherNode.addSunNode(node);
                    fatherNode.setMatch(true);
                    return node.getMatchWords();
                }
            }
        } catch (Exception e) {
//            entity label no such rule
        }
        return null;
    }
}
