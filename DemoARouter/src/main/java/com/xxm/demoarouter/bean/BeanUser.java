package com.xxm.demoarouter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xxm on 2019/3/1 0001
 */
public class BeanUser implements Parcelable {

    private int id;
    private String name;
    private int age;
    private int sex;

    public BeanUser(int id, String name, int age, int sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "BeanUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.sex);
    }

    protected BeanUser(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.age = in.readInt();
        this.sex = in.readInt();
    }

    public static final Creator<BeanUser> CREATOR = new Creator<BeanUser>() {
        @Override
        public BeanUser createFromParcel(Parcel source) {
            return new BeanUser(source);
        }

        @Override
        public BeanUser[] newArray(int size) {
            return new BeanUser[size];
        }
    };
}
