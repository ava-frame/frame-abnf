package com.ava.frame.abnf.element.basic;


import com.ava.frame.abnf.antlr4.AbnfParser;

/**
 * Concatenation: Rule1 Rule2.
 *
 * @author Nick Radov
 * @see <a href="https://tools.ietf.org/html/rfc5234#section-3.1" target="_">
 *      IETF RFC 5234: 3.1. Concatenation: Rule1 Rule2</a>
 */
class Concatenation extends Element {

    /**
     * Create a new {@code Concatenation} from an ANTLR context.
     *
     * @param elements
     *            ANTLR context
     */
    public Concatenation(final AbnfParser.ConcatenationContext elements) {
        super(elements);
    }

}
