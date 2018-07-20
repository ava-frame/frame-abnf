package com.ava.frame.abnf.element.basic;

import com.ava.frame.abnf.element.Recognition;
import org.springframework.util.StringUtils;

/**
 * Created by ava on 2017/6/7.
 * email:zhyx2014@yeah.net
 */
public class ElementNode {
    private Element element;
    private boolean match;
    private int offset = 0;
    private String words;

    public ElementNode(Element element, int offset) {
        this.element = element;
        this.offset = offset;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public String getWords() {
        return words;
    }

    public boolean match(AbnfFuzzer fuzzer, Recognition recognition) {

        match = element.match(fuzzer, recognition);
        if (match) {
            words = recognition.getLastParamMatch();
            if (!StringUtils.isEmpty(words)) {
                if (offset + words.length() <= recognition.getParam().length())
                    recognition.resetIndex(offset + words.length());
                if (element instanceof Rule) {
                    recognition.putRule(((Rule) element).getRuleName(), words);
                }
            }
        } else {
            recognition.resetIndex(offset);
//            母规则不匹配时，去除子规则
            for (Element e : element.elements) {
                if (e instanceof Rule) {
                    recognition.getRules().remove(((Rule) e).getRuleName());
                }
            }
        }
        return match;
    }

    @Override
    public String toString() {
        return "ElementNode{" +
                element +
                '}';
    }
}
