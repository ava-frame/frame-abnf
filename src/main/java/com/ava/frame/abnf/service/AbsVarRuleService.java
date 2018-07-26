package com.ava.frame.abnf.service;


import com.ava.frame.abnf.domain.Entity;
import com.ava.frame.abnf.element.Recognition;

import java.util.List;
import java.util.Map;

/**
 * Created by redred on 2017/7/28.
 * email:zhyx2014@yeah.net
 */
public abstract class AbsVarRuleService {
    public abstract List<Entity>  match(Map<String, List<Entity>> temp,String param,String labelType);
    public abstract List<Entity> matchRegexEntity4AllParam(Map<String, List<Entity>> temp,String param, String labelType);
}
