package com.xxm.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xxm.aidlserver.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxm on 2019/3/6 0006
 */
public class AIDL_Service extends Service {

    private final String TAG = "---aidl---";
    private List<User> mUserList;

    @Override
    public void onCreate() {
        super.onCreate();

        initData();
    }

    private final UserController.Stub stub = new UserController.Stub() {
        @Override
        public List<User> getUserList() throws RemoteException {
            return mUserList;
        }

//        @Override
//        public void addUserInOut(User user) throws RemoteException {
//            if (user != null) {
//                Log.e(TAG, "----addUserInOut--" + user.toString());
//                mUserList.add(user);
//            } else {
//                Log.e(TAG, "----addUserInOut--user为null");
//            }
//        }
//
//        @Override
//        public void addUserIn(User user) throws RemoteException {
//            if (user != null) {
//                Log.e(TAG, "----addUserIn--" + user.toString());
//                mUserList.add(user);
//            } else {
//                Log.e(TAG, "----addUserIn--user为null");
//            }
//        }
//
//        @Override
//        public void addUserOut(User user) throws RemoteException {
//            if (user != null) {
//                Log.e(TAG, "----addUserOut--" + user.toString());
//                mUserList.add(user);
//            } else {
//                Log.e(TAG, "----addUserOut--user为null");
//            }
//        }

        @Override
        public User getUserIn(User user) throws RemoteException {
            if (user != null) {
                Log.e(TAG, "----getUserIn--" + user.toString());
                mUserList.add(user);
            }
            return user;
        }

        @Override
        public User getUserOut(User user) throws RemoteException {
            if (user != null) {
                Log.e(TAG, "----getUserOut--" + user.toString());
                mUserList.add(user);
            }
            return user;
        }

        @Override
        public User getUserInOut(User user) throws RemoteException {
            if (user != null) {
                Log.e(TAG, "----getUserInOut--" + user.toString());
                mUserList.add(user);
            }
            return user;
        }
    };

    private void initData() {
        mUserList = new ArrayList<>();
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User();
            user.setName("张三" + i);
            mUserList.add(user);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
