package com.ava.frame.abnf.element;


import com.alibaba.fastjson.JSONObject;
import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.core.utils.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ava on 2017/5/24.
 * email:zhyx2014@yeah.net
 */
public class Recognition implements Cloneable, Serializable {
    //    用户输入原话
    private transient String param;
    //    最终结果
    private boolean match;
    //    分解词的位置
    private AtomicInteger index = new AtomicInteger(0);
    //    正在匹配的字词
    private transient String lastParamMatch = "";
    //    原始根规则,最终匹配的规则名
    private String firstRule;

    //    匹配结果
    private Map<String, String> rules = new ConcurrentHashMap<>();

    private transient List<Entity> entitiesTemp = new CopyOnWriteArrayList<>();
    private transient Map<String, List<Entity>> entities = new HashMap<>();
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

    public List<Entity> getEntitiesTemp() {
        return entitiesTemp;
    }

    public void setEntitiesTemp(List<Entity> entitiesTemp) {
        this.entitiesTemp = entitiesTemp;
    }


    public String getFirstRule() {
        return firstRule;
    }

    public void setFirstRule(String firstRule) {
        this.firstRule = firstRule;
    }

    public void resetIndex(int offset) {
        index.set(offset);
    }

    public int getIndex() {
        return index.get();
    }


    public void putRule(String ruleName, String value) {
        if (StringUtils.isBlank(ruleName)) return;
        rules.put(ruleName, value);
    }


    public void setIndex(AtomicInteger index) {
        this.index = index;
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
        for (String key : entities.keySet()) {
            sb.put(key, entities.get(key).size());
        }
        return sb.toJSONString();
    }

    public String getLastParamMatch() {
        return lastParamMatch;
    }

    public void setLastParamMatch(String lastParamMatch) {
        this.lastParamMatch = lastParamMatch;
    }

    public String subParamFromIndex(int length) {
        try {
            if (index.get() + length > param.length()) return null;
            return param.substring(index.get(), length + index.get());
        } catch (Exception e) {
            return null;
        }

    }

    public void addIndex(int length) {
        try {
            lastParamMatch = param.substring(index.get(), index.get() + length);
            index.addAndGet(length);
        } catch (Exception e) {

        }
    }
    public Recognition(String param, List<Entity> entitiesTemp) {
        this.param = param;
        this.entitiesTemp = entitiesTemp;
    }

    public String lastallParam() {
        return subParamFromIndex(this.param.length() - this.getIndex());
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    public void addRecognitionNoIndex(Recognition matchRecog) {
        rules.putAll(matchRecog.getRules());
        entities.putAll(matchRecog.getEntities());
    }

    public Map<String, List<Entity>> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, List<Entity>> entities) {
        this.entities = entities;
    }

    public int retainLastLen() {
        return param.length() - index.get();
    }


    /**
     * 添加某个label的实体
     *
     * @param matchName
     * @param list
     */
    public void putEntity(String matchName, List<Entity> list) {
        List<Entity> entityList = entities.get(matchName);
        if (entityList == null) {
            entities.put(matchName, list);
        } else {
            entityList.addAll(list);
        }
    }

    /**
     * 添加某个label的实体
     *
     * @param one
     */
    public void putEntity(Entity one) {
        List<Entity> entityList = entities.get(one.getMatchName());
        if (entityList == null) {
            entityList = new ArrayList<>();
            entities.put(one.getMatchName(), entityList);
        }
        entityList.add(one);
    }
}
