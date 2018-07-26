package com.ava.frame.abnf;

import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.Recognition;
import com.ava.frame.abnf.element.basic.AbnfFuzzer;
import com.ava.frame.abnf.element.basic.Rule;
import com.ava.frame.abnf.service.AbsVarRuleService;
import com.ava.frame.core.SpringApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析器管理
 * Created by ava on 2017/6/8.
 * email:zhyx2014@yeah.net
 */
@Component
public class FuzzerContext implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(FuzzerContext.class);
    //    不同渠道有不同的规则
    private Map<String, AbnfFuzzer> fuzzerMap = new HashMap<>();
    private AbsVarRuleService varRuleService;

    /**
     * 匹配规则 :一条
     *
     * @param ruleName
     * @param words
     * @param channel
     * @param entities
     * @return
     */
    public Recognition match(String ruleName, String words, String channel, Map<String, List<Entity>> entities) {
        return match(ruleName, words, getNoAddAbnfFuzzer(channel), entities);
    }

    private Recognition match(String ruleName, String words, AbnfFuzzer f, Map<String, List<Entity>> entities) {
        Recognition recognition = new Recognition(words, entities);
        recognition.setFirstRule(ruleName);
        try {
            f.match(ruleName, recognition);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return recognition;
    }

    /**
     * 匹配多条规则
     *
     * @param words
     * @param channel
     * @param entities
     * @param rules
     * @return
     */
    public List<Recognition> match(String words, String channel, Map<String, List<Entity>> entities, String... rules) {
        List<Recognition> recognitions = new ArrayList<>();
        int maxMatchCount = 1;
        for (String rule : rules) {
            Recognition recognition = match(rule, words, getNoAddAbnfFuzzer(channel), entities);
            maxMatchCount = compare(recognition, recognitions, maxMatchCount);
        }
        return recognitions;
    }

    private int compare(Recognition recognition, List<Recognition> recognitions, int maxMatchCount) {
        if (recognition.getIndex() == maxMatchCount) {
            recognitions.add(recognition);
        } else if (recognition.getIndex() > maxMatchCount) {
            recognitions.clear();
            maxMatchCount = recognition.getIndex();
            recognitions.add(recognition);
        }
        return maxMatchCount;
    }

    /**
     * 匹配全部规则
     *
     * @param words
     * @param channel
     * @param entities
     * @return
     */
    public List<Recognition> match(String words, String channel, Map<String, List<Entity>> entities) {
        List<Recognition> recognitions = new ArrayList<>();
        int maxMatchCount = 1;
        AbnfFuzzer f = getNoAddAbnfFuzzer(channel);
        for (Rule rule : f.getRules()) {
            if (rule.getRuleName().startsWith("rule_")) {
                Recognition recognition = match(rule.getRuleName(), words, f, entities);
                maxMatchCount = compare(recognition, recognitions, maxMatchCount);
            }
        }
        return recognitions;
    }

    /**
     * 解释器初始化
     */
    private void init() {
        try {
            setVarRuleService(SpringApplicationContext.getBean("commonVarRuleService"));
            String abnfPath = this.getClass().getClassLoader().getResource("abnf").getPath();
            File abnfs = new File(abnfPath);
            if (abnfs.exists() && abnfs.isDirectory()) {
                for (File channel : abnfs.listFiles()) {
                    if (channel.isDirectory()) {
                        AbnfFuzzer f = getNoAddAbnfFuzzer(channel.getName());
                        for (File file : channel.listFiles()) {
                            if (file.isFile()) {
                                try {
                                    if ("word.txt".equals(file.getName())) {
//                                近义词
                                        f.addSynWords(new FileInputStream(file));
                                    } else if (file.getName().contains(".properties")) {
//                                    文法规则
                                        f.addRules(new FileInputStream(file));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        fuzzerMap.put(channel.getName(), f);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增渠道使用
     *
     * @param channel
     * @return
     */
    public AbnfFuzzer getNoAddAbnfFuzzer(String channel) {
        AbnfFuzzer f = fuzzerMap.get(channel);
        if (f == null) {
            f = new AbnfFuzzer();
//                    实体查询方式
            f.setVarRuleService(varRuleService);
            fuzzerMap.put(channel, f);
        }
        return f;
    }

    public void setVarRuleService(AbsVarRuleService varRuleService) {
        this.varRuleService = varRuleService;
        for (AbnfFuzzer f : fuzzerMap.values()) {
            f.setVarRuleService(varRuleService);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
