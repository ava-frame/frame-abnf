package com.ava.frame.abnf.element.basic;

/**
 * %x0D. Carriage return.
 *
 * @author Nick Radov
 */
final class Cr extends Fixed {

    private static final byte[] CR = new byte[]{(byte) '\r'};



    public Cr() {
        super(CR);
    }

}
