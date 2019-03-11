package com.xxm.testmodel.pattern.decorator;

/**
 * Created by xxm on 2019/3/9 0009
 * 装饰类
 */
public class RoomAddBed extends DecoratorRoom {

    public RoomAddBed(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        addBed();
    }

    private void addBed() {
        System.out.println("-----在新房间增加一张床-----");
    }
}
