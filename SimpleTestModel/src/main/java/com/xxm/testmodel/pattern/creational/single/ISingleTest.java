package com.xxm.testmodel.pattern.creational.single;

/**
 * Created by xxm on 2019/3/9 0009
 * 单例模式 - 饿汉式 (线程安全)
 * <p>
 * 优点：写法简单，线程安全。
 * 缺点：没有懒加载的效果，如果没有使用过的话会造成内存浪费。
 * <p>
 * 反射和Serializable序列化会破坏单例模式
 * <p>
 * Serializable可以通过重写readResolve()
 */
public class ISingleTest {

    //在类初始化的时候就已经实例化了，是线程安全的。
    private static final ISingleTest I_SINGLE_TEST = new ISingleTest();

    private ISingleTest() {
    }

    public static ISingleTest getInstance() {
        return I_SINGLE_TEST;
    }


//    private Object readResolve() throws ObjectStreamException {//重写readResolve()
//        return I_SINGLE_TEST;//直接返回单例对象
//    }



    public void testMethod(){
        System.out.println("---单例模式");
    }
}

