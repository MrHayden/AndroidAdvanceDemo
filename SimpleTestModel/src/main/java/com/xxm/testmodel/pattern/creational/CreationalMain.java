package com.xxm.testmodel.pattern.creational;

import com.xxm.testmodel.pattern.creational.builder.BuilderTest;
import com.xxm.testmodel.pattern.creational.factory.FactoryTest1;
import com.xxm.testmodel.pattern.creational.factory.IFactory;
import com.xxm.testmodel.pattern.creational.factory.factory1.Method1Factory;
import com.xxm.testmodel.pattern.creational.factory.factory2.MethodFactory;
import com.xxm.testmodel.pattern.creational.factory.factory3.IAllFactory;
import com.xxm.testmodel.pattern.creational.factory.factory3.MethodAllFactory1;
import com.xxm.testmodel.pattern.creational.factory.factory3.MethodAllFactory2;
import com.xxm.testmodel.pattern.creational.prototype.Person;
import com.xxm.testmodel.pattern.creational.single.ISingleTest;

/**
 * Created by xxm on 2019/3/11 0011
 * 创建型模式 测试类
 */
public class CreationalMain {

    public static void main(String[] arg) {
//        singlePattern();
//        builderPattern();
//        factoryPattern();
        prototypePattern();
    }


    /**
     * 单例模式
     */
    private static void singlePattern() {
        ISingleTest.getInstance().testMethod();
    }

    /**
     * builder模式
     */
    private static void builderPattern() {
        BuilderTest builderTest = new BuilderTest.Builder()
                .setName("晓明")
                .setAge(24)
                .setAlias("明明是我")
                .setSex(0)
                .builder();
        System.out.println("---builder模式---" + builderTest.toString());
    }

    /**
     * 工厂模式
     * <p>
     * 在工厂方法模式中具体工厂负责生产具体的产品，每一个具体工厂对应一种具体产品，工厂方法具有唯一性。
     * //抽象工厂模式则可以提供多个产品对象，而不是单一的产品对象。
     */
    private static void factoryPattern() {
        IFactory iFactory;
        //工厂方法模式
        iFactory = Method1Factory.create();
        iFactory.onPubMethod("工厂方法模式");
        //简单工厂模式
//        iFactory = MethodFactory.create(MethodFactory.TestType.TEST1);
//        iFactory.onPubMethod("简单工厂模式");

        iFactory = MethodFactory.create(FactoryTest1.class);
        iFactory.onPubMethod("简单工厂模式");


        //抽象工厂模式
        IAllFactory iAllFactory;
        iAllFactory = new MethodAllFactory1();
        iAllFactory.createFactory().onPubMethod("生产联想主机");
        iAllFactory.createFactory2().onPubMethod2("生产联想显示器");

        iAllFactory = new MethodAllFactory2();
        iAllFactory.createFactory().onPubMethod("生产戴尔主机");
        iAllFactory.createFactory2().onPubMethod2("生产戴尔显示器");

    }

    /**
     * 原型模式
     */
    private static void prototypePattern() {
        //浅拷贝(PersonInfo对象不实现cloneable,克隆对象修改perisoninfo对象的值时，原对象（Person）内容也跟着改变)
        // 深拷贝（PersonInfo对象实现cloneable,克隆对象修改perisoninfo对象的值时，原对象（Person）内容不会改变)
        Person person = new Person();
        person.setId(1);
        person.setPersonInfo("张三", 23);
        System.out.println("-----" + person.toString());

        System.out.println("\n");

        try {
            Person person1 = person.clone();
            System.out.println("-----" + person1.toString());

            person1.setId(2);
            person1.setPersonInfo("李四", 25);

            System.out.println("\n改变数据后");

            System.out.println("-----" + person.toString());//浅拷贝，id没变，personinfo内容改变，深拷贝id、personinfo内容都不变
            System.out.println("-----" + person1.toString());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
