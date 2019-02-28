/*
 * lwxkey.130725
 * 对SharePreference 操作的工具类
 */

package com.xxm.toolbase.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xxm.toolbase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpHelper {
    private SharedPreferences sp;
    private volatile static SpHelper instance;

    // 密钥
    private final static String secretKey = "snatch.state";
    // 向量
    private final static String iv = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /*********************************************/

    public static SpHelper getInstance(Context context, String app_name) {
        if (instance == null) {
            synchronized (SpHelper.class) {
                if (instance == null) {
                    instance = new SpHelper(context, app_name);
                }
            }
        }
        return instance;
    }

    public static SpHelper getInstance(Context context) {
        if (instance == null) {
            getInstance(context, context.getResources().getString(R.string.app_name));
        }
        return instance;
    }

    private SpHelper(Context context, String app_name) {
        super();
        sp = context.getSharedPreferences(app_name, Context.MODE_PRIVATE);
    }

    /*********************************************/
    public void setString(String keyStr, String valueStr) {
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putString(keyStr, valueStr);
        editor.apply();
    }

    public void setInt(String keyStr, int valueInt) {
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putInt(keyStr, valueInt);
        editor.apply();
    }

    public void setFloat(String keyStr, float valueFloat) {
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putFloat(keyStr, valueFloat);
        editor.apply();
    }

    public void setBoolean(String keyStr, Boolean valueBoolean) {
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putBoolean(keyStr, valueBoolean);
        editor.apply();
    }

    public void setLong(String keyStr, long valueFloat) {
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putLong(keyStr, valueFloat);
        editor.apply();
    }

    /*********************************************/
    public String getString(String keyStr, String defaultValue) {
        return sp.getString(keyStr, defaultValue);
    }

    public int getInt(String keyStr, int defaultValue) {
        return sp.getInt(keyStr, defaultValue);
    }

    public boolean getBoolean(String keyStr, Boolean defaultValue) {
        return sp.getBoolean(keyStr, defaultValue);
    }

    public float getFloat(String keyStr, float defaultValue) {
        return sp.getFloat(keyStr, defaultValue);
    }

    public long getLong(String keyStr, long defaultValue) {
        return sp.getLong(keyStr, defaultValue);
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public <T> boolean putListData(String key, List<T> list) {
        boolean result;
        String type = list.get(0).getClass().getSimpleName();
        SharedPreferences.Editor editor = sp.edit();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = sp.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public <K, V> boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @return HashMap
     */
    public <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = sp.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        KLog.e("SharedPreferencesUtil", obj.toString());
        return map;
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /*********************************************/
    public void cleanData() {
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.clear();
        editor.apply();
    }

}
