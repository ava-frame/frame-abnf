package com.ava.frame.abnf.element.basic;

/**
 * CR LF. Internet standard newline.
 *
 * @author Nick Radov
 */
final class CrLf extends Fixed {

    private static final byte[] CRLF = new byte[]{'\r', '\n'};



    public CrLf() {
        super(CRLF);
    }

}
