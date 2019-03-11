package com.xxm.testmodel.pattern.structural.decorator;

/**
 * Created by xxm on 2019/3/9 0009
 * 具体组件
 */
public class NewRoom extends Room {

    @Override
    public void fitment() {
        System.out.println("---这是一间新房----");
    }
}
