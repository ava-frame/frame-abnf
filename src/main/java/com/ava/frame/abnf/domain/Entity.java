package com.ava.frame.abnf.domain;


import java.util.List;

/**
 * Created by redred on 2017/9/7.
 * email:zhyx2014@yeah.net
 */

public class Entity  implements Cloneable {
    //    唯一标识==_key
    private String uuid;
    //    实体类型film figure role
    private String label;
    private int level;
    //  实体别名
    private List<String> formatNames;
    //    匹配名
    private String matchName;


    @Override
    public Object clone() {
        Entity obj = null;
        try {
            obj = (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
    @Override
    public String toString() {
        return "Entity{" +
                "uuid='" + uuid + '\'' +
                ", label=" + label +
                ", formatNames=" + formatNames +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public List<String> getFormatNames() {
        return formatNames;
    }

    public void setFormatNames(List<String> formatNames) {
        this.formatNames = formatNames;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }
}
