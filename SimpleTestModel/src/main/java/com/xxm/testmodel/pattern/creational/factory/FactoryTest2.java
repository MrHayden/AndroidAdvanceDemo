package com.xxm.testmodel.pattern.creational.factory;


/**
 * Created by xxm on 2019/3/9 0009
 */
public class FactoryTest2 implements IFactory {

    @Override
    public void onPubMethod(String content) {
        System.out.println(content);
    }
}
