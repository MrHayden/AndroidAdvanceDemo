package com.xxm.testmodel;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.xxm.testmodel.pattern.creational.single.ISingleTest;

import java.lang.reflect.Constructor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //反射和Serializable序列化会破坏单例模式
        try {
            Constructor constructor = ISingleTest.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ISingleTest singleTest = (ISingleTest) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("this is title")
                .setMessage("this is message").create().show();


    }

}
