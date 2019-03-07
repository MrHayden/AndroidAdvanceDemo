// UserController.aidl
package com.xxm.aidlserver;

import com.xxm.aidlserver.bean.User;

interface UserController {

    List<User> getUserList();

//    void addUserInOut(inout User user);
//
//    void addUserIn(in User user);
//
//    void addUserOut(out User user);

    User getUserIn(in User user);

    User getUserOut(out User user);

    User getUserInOut(inout User user);
}
