package com.xxm.toolbase.entity;

/**
 * Created by Administrator on 2019/1/10 0010
 */
public class LiveDataBusBean {

    private String keyTag;
    private Object valueTag;
    private Object[] objects;

    public LiveDataBusBean(String keyTag, Object valueTag) {
        this.keyTag = keyTag;
        this.valueTag = valueTag;
    }

    public LiveDataBusBean(String keyTag, Object[] objects) {
        this.keyTag = keyTag;
        this.objects = objects;
    }

    public String getKeyTag() {
        return keyTag;
    }

    public void setKeyTag(String keyTag) {
        this.keyTag = keyTag;
    }

    public Object getValueTag() {
        return valueTag;
    }

    public void setValueTag(Object valueTag) {
        this.valueTag = valueTag;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
