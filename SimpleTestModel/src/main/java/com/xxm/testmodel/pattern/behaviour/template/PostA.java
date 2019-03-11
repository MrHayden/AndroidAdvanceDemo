package com.xxm.testmodel.pattern.behaviour.template;

/**
 * Created by xxm on 2019/3/11 0011
 */
public class PostA extends Postman {

    @Override
    protected void call() {
        System.out.println("---联系收件人A----");
    }
}
