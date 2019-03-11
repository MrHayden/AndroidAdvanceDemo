package com.xxm.testmodel.pattern.template;

/**
 * Created by xxm on 2019/3/11 0011
 * 模板方法模式
 */
public abstract class Postman {


    public final void post() {//执行流程
        prepare();
        call();
        if (isSign())
            sign();
        else
            unSign();
    }


    protected void prepare() {
        System.out.println("----准备派送快递---");
    }

    protected abstract void call();//联系收件人


    protected boolean isSign() {//是否签收快递
        return true;
    }

    protected void sign() {
        System.out.println("---签收了快递---");
    }

    protected void unSign() {
        System.out.println("----拒收了快递----");
    }

}
