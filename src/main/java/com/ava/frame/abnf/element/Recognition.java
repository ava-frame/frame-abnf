package com.ava.frame.abnf.element;


import com.alibaba.fastjson.JSONObject;
import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.core.utils.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Created by ava on 2017/5/24.
 * email:zhyx2014@yeah.net
 */
public class Recognition implements Cloneable, Serializable {
    //    用户输入原话
    private transient String param;
    //    最终结果
    private boolean match = false;
    //    分解词的位置
    private int index = 0;
    //    正在匹配的字词
    private transient String lastParamMatch = "";
    //    原始根规则,最终匹配的规则名
    private String firstRule;

    //    匹配结果
    private Map<String, String> rules = new HashMap<>();
    // label:list
    private transient Map<String, List<Entity>> entitiesTemp = new HashMap<>();
    private transient List<List<Entity>> entities = new ArrayList<>();
    //    private transient List<Entity> entities = new CopyOnWriteArrayList<>();
    private float score = 1.0f;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public Map<String, List<Entity>> getEntitiesTemp() {
        return entitiesTemp;
    }

    public void setEntitiesTemp(Map<String, List<Entity>> entitiesTemp) {
        this.entitiesTemp = entitiesTemp;
    }

    public boolean onlyUseless(String words) {
        String pattern = "[哎呀嗯啊吗阿西吧唔拉啦了呗的得地滴噻哇哒嘟咔么哪呐嘎没有吗呢哈丫]{1,}";
        if (words.replaceAll(pattern, "").length() == 0) return true;
        return false;
    }

    public String getFirstRule() {
        return firstRule;
    }

    public void setFirstRule(String firstRule) {
        this.firstRule = firstRule;
    }


    public void putRule(String ruleName, String value) {
        if (StringUtils.isBlank(ruleName)) return;
        rules.put(ruleName, value);
    }


    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    @Override
    public Object clone() {
        Recognition obj = null;
        try {
            obj = (Recognition) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public String toString() {
        JSONObject sb = new JSONObject();
        sb.put("param", param);
        for (Map.Entry<String, String> en : rules.entrySet()) {
            try {
                if (StringUtils.isNotBlank(en.getValue())) sb.put(en.getKey(), en.getValue());
            } catch (Exception e) {
            }
        }
        return sb.toJSONString();
    }

    public String getLastParamMatch() {
        return lastParamMatch;
    }

    public void setLastParamMatch(String lastParamMatch) {
        this.lastParamMatch = lastParamMatch;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Recognition(String param, Map<String, List<Entity>> entitiesTemp) {
        this.param = param;
        this.entitiesTemp = entitiesTemp;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    public String containSubRule(String sub){
        for (String rule:this.rules.keySet()){
            if (rule.contains(sub))return rule;
        }
        return null;
    }

    public List<List<Entity>> getEntities() {
        return entities;
    }


    public void setEntities(List<List<Entity>> entities) {
        this.entities = entities;
    }
}
