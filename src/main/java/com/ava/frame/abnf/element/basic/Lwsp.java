package com.ava.frame.abnf.element.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * *(WSP / CRLF WSP). Use of this linear-white-space rule permits lines
 * containing only white space that are no longer legal in mail headers and have
 * caused interoperability problems in other contexts. Do not use when defining
 * mail headers and use with caution in other contexts.
 *
 * @author Nick Radov
 */
class Lwsp extends Rule {

    private static final Wsp WSP = new Wsp();
    private static final CrLf CRLF = new CrLf();

    @Override
    public byte[] generate(final AbnfFuzzer f, final Random r,
            final Set<String> exclude) {
        final List<byte[]> childContent = new ArrayList<>(0);
        while (r.nextBoolean()) {
            if (r.nextBoolean()) {
                childContent.add(WSP.generate(f, r, exclude));
            } else {
                childContent.add(CRLF.generate(f, r, exclude));
                childContent.add(WSP.generate(f, r, exclude));
            }
        }
        return concatenate(childContent);
    }

}
