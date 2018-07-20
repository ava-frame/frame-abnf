package com.ava.frame.abnf.element.basic;


import com.ava.frame.abnf.antlr4.AbnfParser;

/**
 * Sequence Group: (Rule1 Rule2).
 *
 * @author Nick Radov
 * @see <a href="https://tools.ietf.org/html/rfc5234#section-3.5" target="_">
 * IETF RFC 5234: 3.5. Sequence Group: (Rule1 Rule2)</a>
 */
class Group extends Element {

    /**
     * Create a new {@code Group} from an ANTLR context.
     *
     * @param elements ANTLR context
     */
    public Group(final AbnfParser.GroupContext elements) {
        super(elements);
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }

}
