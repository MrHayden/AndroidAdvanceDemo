package com.xxm.testmodel.pattern.single;

/**
 * Created by xxm on 2019/3/9 0009
 * 单例模式-静态内部类
 *
 * 优点：懒加载，线程安全，推荐使用
 */
public class ISingleTest3 {

    private ISingleTest3() {
    }

    public static ISingleTest3 getInstance() {
        //第一次调用的时候去加载ISingleHolder，并初始化对象
        return ISingleHolder.SINGLE_TEST_3;
    }

    private static class ISingleHolder {
        public static final ISingleTest3 SINGLE_TEST_3 = new ISingleTest3();
    }
}
