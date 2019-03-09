package com.xxm.toolbase.base;

import android.os.Environment;

/**
 * Created by xxm on 2018/11/29 0029
 */
public class BaseConstants {

    public static String CHANNEL = "";
    public static String CLIENT_BUNDLE_ID = "";

    public static String BASE_FILE_NAME = Environment.getExternalStorageDirectory().getPath() + "/SNATCH/";// 所有软件数据在SD卡的存储路径
    public static String APP_FILE_NAME = "";// APP基础文件夹路径，在软件初始化的时候设置

    public static String APP_FILE = "";// BASE_FILE_NAME + "/" +
    // APP_FILE_NAME;// 存放图片的文件夹
    public static String IMGS_FILE = BASE_FILE_NAME;// APP_FILE + "/IMGS";// 存放图片的文件夹
    public static String CROP_FILE = "";// APP_FILE + "/CROP";// 存放裁剪图片的文件夹
    public static String TEMP_FILE = "";// APP_FILE + "/TEMP";// 存放临时文件的文件夹
    public static String MUSIC_FILE = "";// APP_FILE + "/MUSIC";// 存放临时文件的文件夹
    public static String RECORD_FILE = "";// APP_FILE + "/RECORD";// 存放录音文件的文件夹
    public static String CACHE_FILE = "";// APP_FILE + "/CACHE";// 缓存的文件夹
    public static String DOWNLOAD_FILE = "";// APP_FILE + "/DOWNLOAD";// 下载的文件夹
    public static String PATCH_FILE = "";// APP_FILE + "/PATCH";// 增量更新下载的文件夹
    public static String LOG_FILE = "";// APP_FILE + "/PATCH";// 增量更新下载的文件夹

    public static final String INTENT_TAG = "INTENT_TAG";


}
