package com.ava.frame.abnf.element.basic;

import com.changhong.data.semantic.abnffuzzer.element.Recognition;

import java.util.Random;
import java.util.Set;

/**
 * ALPHA = %x41-5A / %x61-7A. A-Z / a-z.
 *
 * @author Nick Radov
 */
final class Alpha extends Rule {

    private static final byte[] CHARS = new byte[52];

    static {
        for (byte b = (byte) 0x41; b <= (byte) 0x5A; b++) {
            CHARS[b - 0x41] = b;
        }
        for (byte b = (byte) 0x61; b <= (byte) 0x7A; b++) {
            CHARS[b - 0x61 + 26] = b;
        }
    }

    @Override
    public boolean match(AbnfFuzzer f, Recognition recognition) {
//        todo
        return true;

    }

    @Override
    public byte[] generate(final AbnfFuzzer f, final Random r,
            final Set<String> exclude) {
        return new byte[] { CHARS[r.nextInt(CHARS.length)] };
    }

}
