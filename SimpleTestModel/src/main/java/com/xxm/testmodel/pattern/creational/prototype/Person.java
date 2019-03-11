package com.xxm.testmodel.pattern.creational.prototype;

/**
 * Created by xxm on 2019/3/11 0011
 * 原型模式
 */
public class Person implements Cloneable {

    private int id;

    private PersonInfo personInfo = new PersonInfo();

    public Person() {
        System.out.println("---执行person构造函数---");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(String name, int age) {
        this.personInfo.setName(name);
        this.personInfo.setAge(age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personInfo=" + personInfo +
                '}';
    }

    //重写clone()方法，clone()方法不是Cloneable接口里面的，而是Object里面的
    @Override
    public Person clone() throws CloneNotSupportedException {
        System.out.println("----clone时不执行构造函数--");
        //浅拷贝
//        super (Person) super.clone();
        //深拷贝
        Person person = (Person) super.clone();
        person.personInfo = personInfo.clone();
        return person;
    }

    public class PersonInfo implements Cloneable {
        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "PersonInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public PersonInfo clone() throws CloneNotSupportedException {
            return (PersonInfo) super.clone();
        }
    }
}
