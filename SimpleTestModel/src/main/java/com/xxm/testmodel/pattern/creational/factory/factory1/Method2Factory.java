package com.xxm.testmodel.pattern.creational.factory.factory1;

import com.xxm.testmodel.pattern.creational.factory.FactoryTest2;
import com.xxm.testmodel.pattern.creational.factory.IFactory;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class Method2Factory {

    public static IFactory create() {
        return new FactoryTest2();
    }
}
