package com.ava.frame.abnf.element.basic;

import java.util.Random;
import java.util.Set;

/**
 * %x00-FF. 8 bits of data.
 *
 * @author Nick Radov
 */
final class Octet extends Rule {

    @Override
    public byte[] generate(final AbnfFuzzer f, final Random r,
            final Set<String> exclude) {
        final byte[] bytes = new byte[1];
        r.nextBytes(bytes);
        return bytes;
    }

}
