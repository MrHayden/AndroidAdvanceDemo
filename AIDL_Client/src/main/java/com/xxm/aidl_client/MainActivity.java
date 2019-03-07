package com.xxm.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xxm.aidlserver.UserController;
import com.xxm.aidlserver.bean.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private final String tag = getClass().getSimpleName();

    private UserController userController;
    private boolean isConnect;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(tag, "服务绑定成功");
            userController = UserController.Stub.asInterface(iBinder);
            isConnect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(tag, "服务绑定失败");
            isConnect = false;
        }
    };

    public void onBindService(View view) {
        bindService();
    }

    private void bindService() {
        if (isConnect) return;
        Intent intent = new Intent();
        intent.setAction("com.xxm.aidl");
        intent.setPackage("com.xxm.aidlserver");
//        intent.setComponent(new ComponentName("com.xxm.aidl", "com.xxm.aidlserver.AIDLService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void getUserInfo(View view) {
        if (isConnect)
            try {
                userList = userController.getUserList();
                if (userList != null && userList.size() > 0)
                    Log.e(tag, "获取到了用户数据");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    public void userInOut(View view) {
        if (isConnect)
            try {
                // inout 则表示数据可在服务端与客户端之间双向流通
                User userInOut = userController.getUserInOut(new User("userInOut"));
                Log.e(tag, "inout 则表示数据可在服务端与客户端之间双向流通:" + userInOut == null ? "user为null" : userInOut.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    public void userIn(View view) {
        if (isConnect)
            try {
                // in 表示数据只能由客户端流向服务端
                User userIn = userController.getUserIn(new User("userIn"));
                Log.e(tag, "in 表示数据只能由客户端流向服务端:" + userIn == null ? "user为null" : userIn.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    public void userOut(View view) {
        if (isConnect)
            try {
                // out 表示数据只能由服务端流向客户端
                User userOut = userController.getUserOut(new User("userOut"));
                Log.e(tag, "out 表示数据只能由服务端流向客户端:" + userOut == null ? "user为null" : userOut.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConnect)
            unbindService(serviceConnection);
    }
}
