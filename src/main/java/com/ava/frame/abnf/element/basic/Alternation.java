/*
 * AbnfFuzzer: Java library for fuzz testing ABNF implementations
 * Copyright (C) 2016  Nick Radov
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.ava.frame.abnf.element.basic;

/**
 * Alternatives: Rule1 / Rule2.
 *
 * @author Nick Radov
 * @see <a href="https://tools.ietf.org/html/rfc5234#section-3.2" target="_">
 *      IETF RFC 5234: 3.2. Alternatives: Rule1 / Rule2</a>
 */


import com.ava.frame.abnf.antlr4.AbnfParser.*;
import com.ava.frame.abnf.element.Recognition;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

class Alternation extends Element {

    /**
     * Create a new {@code Alternation} from an ANTLR context.
     *
     * @param elements ANTLR context
     */
    public Alternation(final AlternationContext elements) {
        super(elements);
    }

    @Override
    public boolean match(final AbnfFuzzer f, ElementNode fatherNode) {
        if (elements.isEmpty()) {
            throw new IllegalStateException();
        }
        final List<Element> filtered = elements.stream()
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            throw new IllegalArgumentException("all elements excluded");
        }
        boolean match = false;
        for (Element e : filtered) {
            ElementNode node=new ElementNode(e,fatherNode.lastWords(),fatherNode.getEntitiesTemp());
            match =node.match(f);
            if(match){
                fatherNode.addSunNode(node);
                break;
            }
        }
        return match;
    }

    @Override
    public byte[] generate(final AbnfFuzzer f, final Random r,
                           final Set<String> exclude) {
        if (elements.isEmpty()) {
            throw new IllegalStateException();
        }
        final List<Element> filtered = elements.stream()
                .filter(e -> !exclude.contains(e.toString()))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            throw new IllegalArgumentException("all elements excluded");
        }
        return filtered.get(r.nextInt(filtered.size())).generate(f, r, exclude);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (elements != null && !elements.isEmpty()) {
            for (final Element e : elements.subList(0, elements.size() - 1)) {
                sb.append(e).append(" / ");
            }
            sb.append(elements.get(elements.size() - 1));
        }
        return sb.toString().trim();
    }

}
