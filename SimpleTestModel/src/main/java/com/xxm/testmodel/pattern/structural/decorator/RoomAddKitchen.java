package com.xxm.testmodel.pattern.structural.decorator;

/**
 * Created by xxm on 2019/3/9 0009
 * 装饰者类
 */
public class RoomAddKitchen extends DecoratorRoom {

    public RoomAddKitchen(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        addKitchen();
    }

    private void addKitchen() {
        System.out.println("---在新房间增加一个厨房----");
    }
}
