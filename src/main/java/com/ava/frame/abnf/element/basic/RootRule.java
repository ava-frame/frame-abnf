
package com.ava.frame.abnf.element.basic;

import com.changhong.data.semantic.abnffuzzer.element.Recognition;
import com.changhong.data.semantic.core.type.AbnfType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

class RootRule extends Rule {
    private final List<String> rules = new ArrayList<>();
    private final static Logger log = LoggerFactory.getLogger(RootRule.class);
//    private final static ExecutorService scheduledExecutorService = Executors.newCachedThreadPool();

    @Override
    public  boolean match(AbnfFuzzer f, Recognition recognition) {

//        Executors.newFixedThreadPool(5);
        boolean match = false;
//        if (this.getRuleName().equalsIgnoreCase("mid_TIME_week")) {
//            LogUtil.printErr(this.getRuleName());
//        }
        try {
            long now = System.currentTimeMillis();
//            final CountDownLatch latch = new CountDownLatch(rules.size());
            final List<Recognition> list = new CopyOnWriteArrayList<>();
            for (String ruleName : rules) {
                Recognition r = new Recognition(recognition.lastallParam(), recognition.getEntitiesTemp());
                r.setRecognitionRuleMap(recognition.getRecognitionRuleMap());
                try{
                    if (f.match(ruleName, r)) {
                        list.add(r);
                    }
                }catch (Exception e){
                    continue;
                }

            }

//                                return r;
//                            }
//                Recognition recognition1 = scheduledExecutorService.submit(new Callable<Recognition>() {
//                    @Override
//                    public Recognition call() throws Exception {
//                        Recognition r = new Recognition(recognition.lastallParam(), recognition.getEntitiesTemp());
//                        try {
//                            if (f.match(ruleName, r)) {
//                                return r;
//                            }
////                            if (ruleName.equalsIgnoreCase("VIDEO")){
////                                LogUtil.printErr(ruleName);
////                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
////                            latch.countDown();
//                        }
//                        return null;
//                    }
//                }).get();

//                if (recognition1 != null) {
//                    list.add(recognition1);
//                }
//            }
//            scheduledExecutorService.shutdown();
//            latch.await(1000, TimeUnit.MILLISECONDS);
//            latch.await();
//            scheduledExecutorService.shutdown();
            Recognition matchRecog = null;
          /*  if (getRuleName().equals("mid_direct_bright")){
                LogUtil.printErr(getRuleName());
            }*/
            for (Recognition r : list) {
                if (matchRecog == null || matchRecog.getIndex() < r.getIndex()) {
                    match = true;
                    matchRecog = r;
                } else if (matchRecog.getIndex() == r.getIndex()
                        && !r.getRules().containsKey(AbnfType.anyword.name())
                        && levelCompare(matchRecog, r)
                        ) {
                    matchRecog = r;
                }
            }

            if (match) {
                recognition.addRecognition(matchRecog);
            }
//            long delay=System.currentTimeMillis()-now;
//            if (delay>10) {
//                LogUtil.debug(this.getRuleName(), System.currentTimeMillis() - now);
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return match;
    }

    /**
     * 匹配字数相同的情况下;
     * 判断规则的优先级,按前后顺序来判定
     *
     * @param matchRecog
     * @param r
     * @return
     */
    private boolean levelCompare(Recognition matchRecog, Recognition r) {
        for (String rule : rules) {
//                从前往后，如果字数相同,r出现在前面则匹配
            if (matchRecog.getRules().containsKey(rule)) {
                return false;
            }
            if (r.getRules().containsKey(rule)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public byte[] generate(AbnfFuzzer f, Random r, Set<String> exclude) {
        return f.generate(rules.get(r.nextInt(rules.size())), exclude, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
    }

    public RootRule(String ruleName) {
        super(ruleName);
    }

    public void addRule(String rule) {
        rules.add(rule);
    }

    public List<String> getRules() {
        return rules;
    }
}
