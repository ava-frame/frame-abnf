package com.ava.frame.abnf.service;


import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.Recognition;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by redred on 2017/7/28.
 * email:zhyx2014@yeah.net
 */
@Component
public class CommonVarRuleService extends AbsVarRuleService {
    private String[] set2Arr(Set<String> set) {
        if (CollectionUtils.isEmpty(set)) return new String[0];
        String[] arr = new String[set.size()];
        int i = 0;
        for (String str : set) {
            arr[i++] = str;
        }
        return arr;
    }


    @Override
    public List<Entity> match(Recognition recognition, String labelType) {
        return matchEntity(recognition, retainSet(recognition.lastallParam()), labelType);
    }


    private String[] arrWords(String words) {
        Set<String> set = new HashSet<>();
        for (int begin = 0; begin < words.length(); begin++) {
            for (int end = begin + 1; end <= words.length(); end++) {
                set.add(words.substring(begin, end));
            }
        }
        return set2Arr(set);
    }

    /**
     * 匹配实体
     * label匹配或者null全匹配
     *
     * @param recognition
     * @param label
     * @return 字数最大匹配
     */
    private List<Entity> matchEntity(Recognition recognition, Set<String> set, String label) {
        List<Entity> list = recognition.getEntitiesTemp();
        if (CollectionUtils.isEmpty(list)) return null;
        List<Entity> result = new ArrayList<>();
//        最大字数
        String matchMaxParam = null;
        for (Entity one : list) {
            if (labelExtends(label, one.getLabel())) continue;
            Entity matchMaxOne = null;
            for (String anotherName : one.getFormatNames()) {
                if (set.contains(anotherName) && (matchMaxParam == null || anotherName.length() >= matchMaxParam.length())) {
                    matchMaxParam = anotherName;
                    matchMaxOne = (Entity) one.clone();
                }
            }
            if (matchMaxOne != null) {
                matchMaxOne.setMatchName(matchMaxParam);
                if (!result.isEmpty() && result.get(0).getMatchName().length() < matchMaxParam.length()) {
//                    存放的列表的长度小于当前值，重置列表
                    result.clear();
                }
                result.add(matchMaxOne);
            }
        }
        result.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return o2.getLevel() - o1.getLevel();
            }
        });
        return result;
    }


    public Set<String> retainSet(String lastParam) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < lastParam.length(); i++) {
            for (int j = i + 1; j < lastParam.length() + 1; j++) {
                set.add(lastParam.substring(i, j));
            }
        }
        return set;
    }

    /**
     * 匹配之后的任意字符
     *
     * @param recognition
     * @param label
     * @return
     */
    public List<Entity> matchRegexEntity4AllParam(Recognition recognition, String label) {
        return matchEntity(recognition, retainSet4LastAllParam(recognition.lastallParam()), label);
    }

    public Set<String> retainSet4LastAllParam(String lastParam) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < lastParam.length(); i++) {
            for (int j = i + 1; j < lastParam.length() + 1; j++) {
                set.add(lastParam.substring(i, j));
            }
        }
        return set;
    }

    private boolean labelExtends(String label, String one) {
//        无限制，或者限制正确
        if (label == null || label.equals(one)) return false;
        return true;
    }


}
