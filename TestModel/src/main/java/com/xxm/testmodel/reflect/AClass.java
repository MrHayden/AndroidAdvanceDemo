package com.xxm.testmodel.reflect;

/**
 * Created by Administrator on 2019/2/27 0027
 */
public class AClass {

    private String aName;

    public int aAge;

    private void getAInfo(){
        System.out.println("----AClass--getAInfo--aName:  "+ aName+"   aAge:"+aAge);
    }

    public void getAName(){
        System.out.println("-----AClass--getAName--aname:"+aName);
    }
}
