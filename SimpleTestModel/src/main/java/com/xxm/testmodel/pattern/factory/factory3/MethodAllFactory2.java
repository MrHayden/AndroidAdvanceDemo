package com.xxm.testmodel.pattern.factory.factory3;

import com.xxm.testmodel.pattern.factory.IFactory;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class MethodAllFactory2 implements IAllFactory {

    @Override
    public IFactory createFactory() {
        return new Factory1Test2();
    }

    @Override
    public IFactory2 createFactory2() {
        return new Factory2Test2();
    }
}
