package com.xxm.testmodel.pattern.behaviour;

import com.xxm.testmodel.pattern.behaviour.template.PostA;
import com.xxm.testmodel.pattern.behaviour.template.PostB;

/**
 * Created by xxm on 2019/3/11 0011
 * 行为型模式  测试类
 */
public class BehaviourMain {

    public static void main(String[] arg) {
//        templatePattern();
    }

    /**
     * 模板方法模式
     */
    private static void templatePattern() {

        //收件人A的流程
        PostA postA = new PostA();
        postA.post();

        System.out.println("\n");

        //收件人B的流程
        PostB postB = new PostB();
        postB.post();
    }
}
