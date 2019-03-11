package com.xxm.testmodel.pattern.creational.factory.factory3;


/**
 * Created by xxm on 2019/3/9 0009
 */
public class Factory2Test1 implements IFactory2 {

    @Override
    public void onPubMethod2(String content) {
        System.out.println("---Factory2Test1---" + content);
    }
}
