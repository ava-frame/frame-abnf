package com.ava.frame.abnf.element.basic;

/**
 * %x0A. Linefeed.
 *
 * @author Nick Radov
 *
 */
class Lf extends Fixed {

    private static final byte[] LF = new byte[] { '\n' };

    public Lf() {
        super(LF);
    }

}
