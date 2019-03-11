package com.xxm.testmodel.pattern.creational.factory.factory3;

import com.xxm.testmodel.pattern.creational.factory.IFactory;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class Factory1Test1 implements IFactory {

    @Override
    public void onPubMethod(String content) {
        System.out.println("---Factory1Test1---" + content);
    }
}
