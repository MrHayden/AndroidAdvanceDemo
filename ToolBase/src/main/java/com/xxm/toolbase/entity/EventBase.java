package com.xxm.toolbase.entity;

import java.io.Serializable;

/**
 * Created by lwxkey on 15/12/16.
 */
public class EventBase implements Serializable {

    private int type;// type <10000  代表是全局广播,  大于10000,代表是当前页面的广播
    private Object[] objs;

    private String actTag;

    public EventBase(int type) {
        this.type = type;
        this.objs = null;
    }

    public EventBase(int type, Object... objs) {
        this.type = type;
        this.objs = objs;
    }

    public EventBase(int type, String actTag, Object[] objs) {
        this.type = type;
        this.objs = objs;
        this.actTag = actTag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }


    public String getActTag() {
        return actTag;
    }

    public void setActTag(String actTag) {
        this.actTag = actTag;
    }
}
