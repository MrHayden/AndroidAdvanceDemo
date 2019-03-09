package com.xxm.testmodel.reflect;

/**
 * Created by Administrator on 2019/2/27 0027
 */
public class BClass extends AClass {

    private String bName;

    private int bAge;

    public String bMsg = "DEFAULT_BMSG";

    //直接赋值时。在jvm在编译时直接优化了,运行时反射修改的内容无效
    // null ==null?"DEFAULT_FINAL_CONTENT":null使用三目表达式或者获取实例化对象时赋值可以修改
    public final String finalStr;

    public BClass(){
        finalStr = "DEFAULT_FINAL_CONTENT";
    }

    public void getB_PubInfo(String name, int age, String msg) {
        bName = name;
        bAge = age;
        bMsg = msg;
        System.out.println("----bclass--getB_PubInfo---bname:" + bName + "--bage:" + bAge + "---bmsg:" + bMsg);
    }

    private void getB_PrMethod(){
        bName = "B_PrMethod测试";
        System.out.println("----getB_PrMethod----bName:"+bName);
    }

    public String getbMsg(){
        return bMsg;
    }

    public String getFinalStr(){
        return finalStr;
    }
}
