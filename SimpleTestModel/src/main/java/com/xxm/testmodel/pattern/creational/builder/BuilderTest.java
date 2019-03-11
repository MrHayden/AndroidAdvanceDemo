package com.xxm.testmodel.pattern.creational.builder;

/**
 * Created by xxm on 2019/3/9 0009
 */
public class BuilderTest {

    private Builder builder;

    public BuilderTest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public String toString() {
        return "BuilderTest{" +
                "builder=" + builder.toString() +
                '}';
    }

    public static final class Builder {
        private String name;
        private String alias;
        private int age;
        private int sex;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setSex(int sex) {
            this.sex = sex;
            return this;
        }

        public BuilderTest builder() {
            return new BuilderTest(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "name='" + name + '\'' +
                    ", alias='" + alias + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    '}';
        }
    }
}
