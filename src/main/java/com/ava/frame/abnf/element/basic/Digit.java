package com.ava.frame.abnf.element.basic;

import com.ava.frame.abnf.element.Recognition;
import com.ava.frame.core.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * %x30-39. 0-9.
 *
 * @author Nick Radov
 */
final class Digit extends Rule {
    private final static List<String> han = CollectionUtils.arrayToList(new String[]{"零", "一", "二","两","俩","三", "四", "五", "六", "七", "八", "九", "十","百","千","万","亿","兆"
    });


    @Override
    public byte[] generate(final AbnfFuzzer f, final Random r,
                           final Set<String> exclude) {
//        return new byte[]{(byte) (r.nextInt(10) + 0x30)};
//        !!!!!!
//          datatool 生成redis数据时，不需要数字
        return new byte[]{};
    }

    @Override
    public boolean match(AbnfFuzzer f, Recognition recognition){
            String str = recognition.subParamFromIndex(1);
            if (StringUtils.isBlank(str)) return false;
            if (han.contains(str) || StringUtils.isNumber(str)) {
                recognition.addIndex(1);
                return true;
            }
        return false;
    }
}
