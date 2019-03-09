package com.xxm.toolbase.utils;

/**
 * Created by xxm on 2019/1/8 0008
 */
public class CosUrlUtil {

    public static String getCosUrl(String url, int w, int h) {
        if (StringUtils.isEmpty(url)) return "";
        return url + "?imageMogr2/thumbnail/" + w + "x" + h;
    }

    public static String getCosUrl(String url, int w) {
        if (StringUtils.isEmpty(url)) return "";
        return url + "?imageMogr2/thumbnail/" + w + "x";
    }

    public static String getCosUrlH(String url, int h) {
        if (StringUtils.isEmpty(url)) return "";
        return url + "?imageMogr2/thumbnail/x" + h;
    }
}
