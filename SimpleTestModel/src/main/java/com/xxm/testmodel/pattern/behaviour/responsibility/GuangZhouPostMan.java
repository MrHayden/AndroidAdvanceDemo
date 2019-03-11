package com.xxm.testmodel.pattern.behaviour.responsibility;

/**
 * Created by xxm on 2019/3/11 0011
 */
public class GuangZhouPostMan extends PostMan {

    @Override
    public void handlerCourier(String address) {
        if ("guangzhou".equals(address)) {
            System.out.println("派送到了广州");
        } else {
            if (nextPostMan != null)
                nextPostMan.handlerCourier(address);
        }
    }
}
