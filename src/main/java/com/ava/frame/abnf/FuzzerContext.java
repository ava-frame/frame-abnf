package com.ava.frame.abnf;

import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.Recognition;
import com.ava.frame.abnf.element.basic.AbnfFuzzer;
import com.ava.frame.abnf.service.AbsVarRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
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
     * 匹配规则
     *
     * @param ruleName
     * @param words
     * @param f
     * @param entityList
     * @return
     */
    private Recognition match(String ruleName, String words, AbnfFuzzer f, List<Entity> entityList) {
        Recognition recognition = new Recognition(words, entityList);
        recognition.setFirstRule(ruleName);
        try {
            boolean match = f.match(ruleName, recognition);
            recognition.setMatch(match);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return recognition;
    }

    /**
     * 解释器初始化
     */
    public void init() {
        try {

            String abnfPath = this.getClass().getClassLoader().getResource("abnf").getPath();
            File abnfs = new File(abnfPath);
            if (abnfs.exists() && abnfs.isDirectory()) {
                for (File channel : abnfs.listFiles()) {
//                LogUtil.debug(channel.getName());
                    if (channel.isDirectory()) {
                        AbnfFuzzer f =getNoAddAbnfFuzzer(channel.getName());
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
            log.error("FuzzerContext下的解释器初始化失败");
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
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
