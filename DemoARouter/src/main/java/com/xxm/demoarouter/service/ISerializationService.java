package com.xxm.demoarouter.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.fastjson.JSON;
import com.xxm.demoarouter.base.Constance;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2019/3/1 0001
 * 如果需要传递自定义对象，新建一个类（并非自定义对象类），然后实现 SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)，例如：BeanTest类
 */
@Route(path = Constance.PATH_SERVICE_JSON)
public class ISerializationService implements SerializationService {

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return JSON.toJSONString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return JSON.parseObject(input, clazz);
    }

    @Override
    public void init(Context context) {

    }
}
