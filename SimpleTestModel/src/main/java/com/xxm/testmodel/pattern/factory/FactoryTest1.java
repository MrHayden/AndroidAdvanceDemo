package com.xxm.testmodel.pattern.factory;


/**
 * Created by xxm on 2019/3/9 0009
 */
public class FactoryTest1 implements IFactory {

    @Override
    public void onPubMethod(String content) {
        System.out.println(content);
    }
}
