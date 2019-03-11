package com.xxm.testmodel.pattern.behaviour;

import com.xxm.testmodel.pattern.behaviour.responsibility.BeiJingPostMan;
import com.xxm.testmodel.pattern.behaviour.responsibility.GuangZhouPostMan;
import com.xxm.testmodel.pattern.behaviour.responsibility.ShangHaiPostMan;
import com.xxm.testmodel.pattern.behaviour.state.StateUtil;
import com.xxm.testmodel.pattern.behaviour.strategy.StrategyA;
import com.xxm.testmodel.pattern.behaviour.strategy.StrategyB;
import com.xxm.testmodel.pattern.behaviour.strategy.StrategyC;
import com.xxm.testmodel.pattern.behaviour.strategy.StrategyUtil;
import com.xxm.testmodel.pattern.behaviour.template.PostA;
import com.xxm.testmodel.pattern.behaviour.template.PostB;

/**
 * Created by xxm on 2019/3/11 0011
 * 行为型模式  测试类
 */
public class BehaviourMain {

    public static void main(String[] arg) {
//        templatePattern();
//        strategyPattern();
//        statePattern();
        responsibilityPattern();
    }

    /**
     * 模板方法模式
     * 源码：自定义view时继承View，重写ondraw()等方法；
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

    /**
     * 策略模式：
     * <p>
     * 策略模式提供了一组算法给客户端调用，使得客户端能够根据不同的条件来选择不同的策略来解决不同的问题。
     * 如排序算法，可以使用冒泡排序、快速排序等等。
     * <p>
     * 源码 listview的setAdapter、ValueAnimator的setInterpolator()等
     */
    private static void strategyPattern() {
        StrategyUtil strategyUtil;

        System.out.println("遇到策略A时使用");
        strategyUtil = new StrategyUtil(new StrategyA());
        strategyUtil.onStrategy();

        System.out.println("\n遇到策略B时使用");
        strategyUtil = new StrategyUtil(new StrategyB());
        strategyUtil.onStrategy();

        System.out.println("\n遇到策略C时使用");
        strategyUtil = new StrategyUtil(new StrategyC());
        strategyUtil.onStrategy();
    }

    /**
     * 状态模式：
     * <p>
     * 状态模式中的行为是由状态来决定的，即不同状态下的行为也不同。
     * 状态模式的结构跟策略模式的几乎一样，但其本质是不一样的。策略模式中的行为是彼此独立，能够相互替换的；而状态模式的行为是平行的，不同状态下其行为也是不一样的，具有不可替换性。
     */
    public static void statePattern() {
        StateUtil stateUtil;
        stateUtil = new StateUtil();
        stateUtil.isStateB();
        stateUtil.postStateA();
        stateUtil.postStateB();
        stateUtil.isStateA();
        stateUtil.postStateA();
        stateUtil.postStateB();
    }

    /**
     * 责任链模式：
     * <p>
     * 多个对象中，每个对象都持有下一个对象的引用，这就构成了链这种结构。
     * 一个请求通过链的头部，一直往下传递到链上的每一个结点，直到有某个结点对这个请求做出处理为止，这就是责任链模式。
     * 责任链模式一般分为处理者与请求者。具体的处理者分别处理请求者的行为。
     *
     * 源码：okhttp请求，事件分发机制等使用了责任链模式
     */
    public static void responsibilityPattern() {
        //创建不同的派送员
        GuangZhouPostMan guangZhouPostMan = new GuangZhouPostMan();
        BeiJingPostMan beiJingPostMan = new BeiJingPostMan();
        ShangHaiPostMan shangHaiPostMan = new ShangHaiPostMan();

        //设置下一个结点
        guangZhouPostMan.nextPostMan = beiJingPostMan;
        beiJingPostMan.nextPostMan = shangHaiPostMan;

        //首结点都是从广州开始
        System.out.println("这是一个送往上海的快递");
        guangZhouPostMan.handlerCourier("shanghai");

        System.out.println("\n这是一个送往北京的快递");
        guangZhouPostMan.handlerCourier("beijing");

        System.out.println("\n这是一个送往广州的快递");
        guangZhouPostMan.handlerCourier("guangzhou");
    }
}
