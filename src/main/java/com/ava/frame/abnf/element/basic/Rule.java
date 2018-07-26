package com.ava.frame.abnf.element.basic;


import com.ava.frame.abnf.antlr4.AbnfParser;

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
    public boolean match(final AbnfFuzzer f,ElementNode fatherNode) {
        boolean match = true;
        for (Element e : elements) {
            ElementNode sunNode = new ElementNode(e, fatherNode.lastWords(), fatherNode.getEntitiesTemp());
            match = match && sunNode.match(f);
            if (!match) break;
            fatherNode.addSunNode(sunNode);
        }
        return match;
    }


}
