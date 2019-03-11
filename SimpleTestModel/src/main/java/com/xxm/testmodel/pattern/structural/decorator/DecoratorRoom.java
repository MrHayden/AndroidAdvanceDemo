package com.xxm.testmodel.pattern.structural.decorator;

/**
 * Created by xxm on 2019/3/9 0009
 * 抽象装饰角色
 */
public class DecoratorRoom extends Room {

    private Room room;

    public DecoratorRoom(Room room) {
        this.room = room;
    }

    @Override
    public void fitment() {
        room.fitment();
    }
}
