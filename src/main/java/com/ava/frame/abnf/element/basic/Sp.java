package com.ava.frame.abnf.element.basic;

/**
 * %x20.
 *
 * @author Nick Radov
 */
final class Sp extends Fixed {

    private static final byte[] SP = new byte[] { ' ' };

    public Sp() {
        super(SP);
    }

}
