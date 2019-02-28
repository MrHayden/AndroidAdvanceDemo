package com.xxm.demolivedatabus;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Administrator on 2019/2/26 0026
 */
public class NameViewModel extends ViewModel {

    private  MutableLiveData mutableLiveData;

    public <T> MutableLiveData<T> getInstance() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return (MutableLiveData<T>) mutableLiveData;
    }
}
