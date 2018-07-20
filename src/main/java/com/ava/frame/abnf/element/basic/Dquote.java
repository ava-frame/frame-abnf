package com.ava.frame.abnf.element.basic;

/**
 * %x22. " (Double Quote)
 *
 * @author Nick Radov
 */
final class Dquote extends Fixed {

    private static final byte[] DQUOTE = new byte[]{'"'};


    public Dquote() {
        super(DQUOTE);
    }

}
