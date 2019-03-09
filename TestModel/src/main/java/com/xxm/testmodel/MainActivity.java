package com.xxm.testmodel;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.xxm.testmodel.pattern.builder.BuilderTest;
import com.xxm.testmodel.pattern.factory.FactoryTest1;
import com.xxm.testmodel.pattern.factory.IFactory;
import com.xxm.testmodel.pattern.factory.factory1.Method1Factory;
import com.xxm.testmodel.pattern.factory.factory2.MethodFactory;
import com.xxm.testmodel.pattern.factory.factory3.IAllFactory;
import com.xxm.testmodel.pattern.factory.factory3.MethodAllFactory1;
import com.xxm.testmodel.pattern.factory.factory3.MethodAllFactory2;
import com.xxm.testmodel.pattern.single.ISingleTest;

import java.lang.reflect.Constructor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //反射和Serializable序列化会破坏单例模式
        try {
            Constructor constructor = ISingleTest.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ISingleTest singleTest = (ISingleTest) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("this is title")
                .setMessage("this is message").create().show();


    }

    public static void main(String[] arg) {
//        singlePattern();
//        builderPattern();
        factoryPattern();
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
}
