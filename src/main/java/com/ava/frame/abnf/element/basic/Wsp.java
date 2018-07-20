package com.ava.frame.abnf.element.basic;

import java.util.Random;
import java.util.Set;

/**
 * SP / HTAB. White space.
 *
 * @author Nick Radov
 */
final class Wsp extends Rule {

    private static final byte[] SP = new byte[]{' '};
    private static final byte[] HTAB = new byte[]{'\t'};

    @Override
    public byte[] generate(final AbnfFuzzer f, final Random r,
                           final Set<String> exclude) {
        if (r.nextBoolean()) {
            return SP;
        } else {
            return HTAB;
        }
    }

}
