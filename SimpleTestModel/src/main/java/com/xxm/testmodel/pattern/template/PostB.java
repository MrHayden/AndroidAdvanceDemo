package com.xxm.testmodel.pattern.template;

/**
 * Created by xxm on 2019/3/11 0011
 */
public class PostB extends Postman {
    @Override
    protected void call() {
        System.out.println("---收件人B----");
    }

    @Override
    protected boolean isSign() {//重写方法
        return false;
    }

    @Override
    protected void unSign() {
        super.unSign();
        System.out.println("---东西不满意，拒收了快递---");
    }
}
