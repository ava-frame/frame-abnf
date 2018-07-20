package com.ava.frame.abnf.element;

/**
 * Created by ava on 2017/6/7.
 * email:zhyx2014@yeah.net
 */
public class RuleMatchNode {
    private RuleMatchNode parent;
    private String ruleName;
    private int offset;
    private StringBuilder words = new StringBuilder();

    public RuleMatchNode(String ruleName, int offset, RuleMatchNode parent) {
        this.ruleName = ruleName;
        this.offset = offset;
        this.parent = parent;
    }

    public RuleMatchNode getParent() {
        return parent;
    }

    public void addWords(String word) {
        words.append(word);
//        if (parent != null) parent.addWords(word);
    }

    public int getOffset() {
        return offset;
    }

    public StringBuilder getWords() {
        return words;
    }

    @Override
    public String toString() {
        return ruleName;
    }
}
