package com.xxm.testmodel.pattern.structural;

import com.xxm.testmodel.pattern.structural.decorator.DecoratorRoom;
import com.xxm.testmodel.pattern.structural.decorator.NewRoom;
import com.xxm.testmodel.pattern.structural.decorator.RoomAddBed;
import com.xxm.testmodel.pattern.structural.decorator.RoomAddKitchen;

/**
 * Created by xxm on 2019/3/11 0011
 * 结构型模式   测试类
 */
public class StructuralMain {

    public static void main(String[] arg) {
            decoratorPattern();
    }

    /**
     * 装饰者模式
     */
    private static void decoratorPattern() {
        DecoratorRoom decoratorRoom;
        decoratorRoom = new RoomAddKitchen(new NewRoom());
        decoratorRoom.fitment();

        System.out.println("\n");

        decoratorRoom = new RoomAddBed(new NewRoom());
        decoratorRoom.fitment();
    }

}
