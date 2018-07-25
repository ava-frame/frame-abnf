package com.ava.frame.abnf.element;


import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.basic.AbnfFuzzer;
import com.ava.frame.abnf.element.basic.Rule;

import java.util.List;

/**
 * Created by redred on 2017/7/18.
 * email:zhyx2014@yeah.net
 */

public class EntityRule extends Rule {
//    需要查询的label类型，null为全部查找
    private String label;

    public EntityRule(String label) {
        this.label = label;
    }

    public EntityRule() {
    }

    @Override
    public boolean match(AbnfFuzzer f, Recognition recognition) {
//        不同label可能同名，所以得都返回
        List<Entity> list = f.matchEntity(recognition,label);
        if (list == null||list.isEmpty()) return false;
        String matchName=list.get(0).getMatchName();
        recognition.addEntity(list);
        recognition.addIndex(matchName.length());
        return true;
    }
}
