package com.xxm.testmodel.pattern.creational.factory.factory3;

import com.xxm.testmodel.pattern.creational.factory.IFactory;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class MethodAllFactory1 implements IAllFactory {

    @Override
    public IFactory createFactory() {
        return new Factory1Test1();
    }

    @Override
    public IFactory2 createFactory2() {
        return new Factory2Test1();
    }
}
