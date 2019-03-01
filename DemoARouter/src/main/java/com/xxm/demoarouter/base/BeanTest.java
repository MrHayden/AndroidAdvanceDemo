package com.xxm.demoarouter.base;

/**
 * Created by Administrator on 2019/3/1 0001
 */
public class BeanTest {

    public String name;

    public BeanTest() {
    }

    public BeanTest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BeanTest{" +
                "name='" + name + '\'' +
                '}';
    }
}

