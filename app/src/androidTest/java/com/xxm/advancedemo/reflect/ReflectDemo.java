package com.xxm.advancedemo.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2019/2/27 0027
 * java 反射的使用
 */
public class ReflectDemo {

    public static void main(String... arg) {

//        getReflectFieldInfo(BClass.class);
//
//        getReflectMethodInfo(BClass.class);

//        invokeMethod(new BClass());

        fyField(new BClass());
    }

    /**
     * 获取反射字段
     *
     * @param mClass
     */
    public static void getReflectFieldInfo(Class<?> mClass) {
        System.out.println("---反射字段信息---");
        Field[] fields;
        //获取所有public字段（包括父类的）
        fields = mClass.getFields();
        //获取本类的所有字段
//        fields = mClass.getDeclaredFields();
        for (Field field : fields) {
            //获取访问权限并输出
            int modifiers = field.getModifiers();
            System.out.println("----modifiers---" + Modifier.toString(modifiers));
            System.out.println("---typeName:" + field.getType().getName() + "---name:" + field.getName() + "\n");
        }
    }

    /**
     * 获取反射方法
     *
     * @param mClass
     */
    public static void getReflectMethodInfo(Class<?> mClass) {
        System.out.println("---反射方法信息---");
        Method[] methods;
        //获取所有public方法（包括父类的）
        methods = mClass.getMethods();
        //获取本类的所有方法
//        methods = mClass.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            System.out.println("----modifiers--" + Modifier.toString(modifiers));
            System.out.println("----returnType" + method.getReturnType().getName() + "----name:" + method.getName() + "\n");
        }
    }

    /**
     * 调用反射方法
     *
     * @param cls 实例对象
     * @param <T>
     */
    public static <T> void invokeMethod(T cls) {
        Class mClass = cls.getClass();
        try {
            Method method = mClass.getDeclaredMethod("getB_PubInfo", String.class, int.class, String.class);
            if (method != null) {
                //获取私有方法的访问权
                method.setAccessible(true);
                try {
                    method.invoke(cls, "测试name", 88, "测试msg");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     *修改私有字段内容
     */
    public static <T> void fyField(T cls) {
        Class mClass = cls.getClass();
        Field field;
        try {
//            field = mClass.getDeclaredField("bMsg");
            field = mClass.getDeclaredField("finalStr");
            if (field != null) {
                field.setAccessible(true);
                try {
                    if (cls instanceof BClass){
                        System.out.println("---修改前内容---"+((BClass) cls).getFinalStr());
                        field.set(cls,"内容修改了");
                        System.out.println("---修改后内容---"+((BClass) cls).getFinalStr());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
