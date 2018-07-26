package com.ava.frame.abnf.element.basic;

import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.Recognition;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ava on 2017/6/7.
 * email:zhyx2014@yeah.net
 */
public class ElementNode {
    private Element element;
    private boolean match=false;
    private String words;
    private String matchWords = "";
    private Map<String, String> rules = new HashMap<>();
    private transient Map<String, List<Entity>> entitiesTemp = new HashMap<>();
    private transient List<List<Entity>> entities = new ArrayList<>();
    private String regex;

    public ElementNode(Element element, String words, Map<String, List<Entity>> entitiesTemp) {
        this.element = element;
        this.words = words;
        this.entitiesTemp = entitiesTemp;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public ElementNode(Element element, String words, Map<String, List<Entity>> entitiesTemp, String regex) {
        this.element = element;
        this.words = words;
        this.entitiesTemp = entitiesTemp;
        this.regex = regex;
    }

    public String lastWords() {
        return words.replaceFirst(matchWords, "");
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }


    public String getMatchWords() {
        return matchWords;
    }

    public void setMatchWords(String matchWords) {
        this.matchWords = matchWords;
    }

    public String getWords() {
        return words;
    }

    public String subParamFromIndex(int length) {
        try {
            return lastWords().substring(0, length);
        } catch (Exception e) {
            return null;
        }

    }

    public boolean match(AbnfFuzzer fuzzer) {
        match = element.match(fuzzer, this);
        if (match && element instanceof Rule) {
            this.rules.put(((Rule) element).getRuleName(), words);
        }
        return match;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public void addMatchWords(String matchWords) {
        this.matchWords += matchWords;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

    public Map<String, List<Entity>> getEntitiesTemp() {
        return entitiesTemp;
    }

    public void setEntitiesTemp(Map<String, List<Entity>> entitiesTemp) {
        this.entitiesTemp = entitiesTemp;
    }

    public List<List<Entity>> getEntities() {
        return entities;
    }

    public void setEntities(List<List<Entity>> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "ElementNode{" +
                element +
                '}';
    }

    public void addSunNode(ElementNode sunNode) {
        this.matchWords += sunNode.getMatchWords();
        this.rules.putAll(sunNode.getRules());
        this.entities.addAll(sunNode.getEntities());
    }
    public void addRuleAndEntity(ElementNode sunNode) {
        this.rules.putAll(sunNode.getRules());
        this.entities.addAll(sunNode.getEntities());
    }
    /**
     * 添加某个label的实体
     *
     * @param list
     */
    public void addEntity(List<Entity> list) {
        entities.add(list);
    }

    /**
     * 添加某个label的实体
     *
     * @param one
     */
    public void addEntity(Entity one) {
        List<Entity> list = new ArrayList<>();
        list.add(one);
        entities.add(list);
    }
}
