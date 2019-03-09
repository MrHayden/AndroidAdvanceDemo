package com.xxm.testmodel.pattern.factory.factory1;

import com.xxm.testmodel.pattern.factory.FactoryTest1;
import com.xxm.testmodel.pattern.factory.IFactory;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class Method1Factory {

    public static IFactory create() {
        return new FactoryTest1();
    }
}
