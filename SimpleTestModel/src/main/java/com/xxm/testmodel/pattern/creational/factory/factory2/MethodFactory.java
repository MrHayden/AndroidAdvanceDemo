package com.xxm.testmodel.pattern.creational.factory.factory2;

import com.xxm.testmodel.pattern.creational.factory.FactoryTest1;
import com.xxm.testmodel.pattern.creational.factory.FactoryTest2;
import com.xxm.testmodel.pattern.creational.factory.IFactory;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class MethodFactory {

    public enum TestType {
        TEST1, TEST2
    }

    public static IFactory create(TestType testType) {
        switch (testType) {
            case TEST1:
                return new FactoryTest1();
            case TEST2:
                return new FactoryTest2();
        }
        return new FactoryTest1();
    }


    //反射实现工厂类
    public static <T extends IFactory> T create(Class<T> cls) {
        IFactory iFactory = null;
        if (cls != null) {
            try {
                //反射
//                Constructor constructor = cls.getDeclaredConstructor();
//                constructor.setAccessible(true);
//                iFactory = (IFactory) constructor.newInstance();
//                或
                iFactory = (IFactory) Class.forName(cls.getName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) iFactory;
    }

}
