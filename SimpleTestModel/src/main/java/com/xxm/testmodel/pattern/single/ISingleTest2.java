package com.xxm.testmodel.pattern.single;

/**
 * Created by xxm on 2019/3/9 0009
 * 单例模式 -- 懒汉式(
 */
public class ISingleTest2 {

    //volatile能够防止代码的重排序，保证的得到的对象是初始化的。
    private static volatile ISingleTest2 iSingleTest2;

    private ISingleTest2() {
    }

    /**
     * 优点：实现了懒加载的效果。
     * 缺点：线程不安全。
     */
//线程不安全
//    public static ISingleTest2 getInstance() {
//        if (iSingleTest2 == null) {
//            //在第一次调用的时候才实例化，实现了懒加载。
//            iSingleTest2 = new ISingleTest2();
//        }
//        return iSingleTest2;
//    }

    /**
     * 优点：实现了懒加载的效果，线程安全。
     * 缺点：使用synchronized会造成不必要的同步开销，而且大部分时候我们是用不到同步的。
     */
    //加上synchronized同步锁。线程安全。
//    public static synchronized ISingleTest2 getInstance() {
//        if (iSingleTest2 == null) {
//            //在第一次调用的时候才实例化，实现了懒加载。
//            iSingleTest2 = new ISingleTest2();
//        }
//        return iSingleTest2;
//    }

    /**
     * 优点：懒加载，线程安全，效率较高
     * 缺点：volatile影响一点性能，高并发下有一定的缺陷，某些情况下DCL会失效，虽然概率较小。
     * @return
     */
    public static ISingleTest2 getInstance() {
        if (iSingleTest2 == null) {//第一次检查，避免不必要的同步
            synchronized (ISingleTest2.class) {//同步
                if (iSingleTest2 == null)//第二次检查，为null时才创建实例
                    iSingleTest2 = new ISingleTest2();
            }
        }
        return iSingleTest2;
    }
}
