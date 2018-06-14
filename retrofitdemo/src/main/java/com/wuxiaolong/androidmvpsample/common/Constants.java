/**
 * ClassName: Constants.java
 * created on 2013-1-24
 * Copyrights 2013-1-24 hjgang All rights reserved.
 * site: http://t.qq.com/hjgang2012
 * email: hjgang@yahoo.cn
 */
package com.wuxiaolong.androidmvpsample.common;

import android.content.Context;

import java.io.File;

/**
 *
 * @ClassName:Constants

 * @PackageName:com.wuxiaolong.androidmvpsample.common

 * @Create On 2018/5/18   16:18

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


public final class Constants {
    /**
     * 系统初始化配置文件名
     */
    public static final String SYSTEM_INIT_FILE_NAME = "gch.android.sysini";
    public static final String FLAG = "com.gch.android";


    /**
     * 图片类型
     */
    public static final String IMAGE_UNSPECIFIED = "image/*";
    /**
     * 本地缓存目录
     */
    public static String CACHE_DIR;
    /**
     * 图片缓存目录
     */
    public static String CACHE_DIR_IMAGE;
    /**
     * 待上传图片缓存目录
     */
    public static String CACHE_DIR_UPLOADING_IMG;
    /**
     * 图片目录
     */
    public static String CACHE_IMAGE;
    /**
     * 图片名称
     */
    public static final String PHOTO_PATH = "handongkeji_android_photo";
    /**
     * 语音缓存目录
     */
    public static String CACHE_VOICE;

    public static void init(Context context) {
        CACHE_DIR = context.getCacheDir().getAbsolutePath();
        File file = new File(CACHE_DIR, "image");
        file.mkdirs();
        CACHE_IMAGE = file.getAbsolutePath();
        CACHE_DIR_IMAGE = CACHE_IMAGE;
        file = new File(CACHE_DIR, "temp");
        file.mkdirs();
        CACHE_DIR_UPLOADING_IMG = file.getAbsolutePath();
        file = new File(CACHE_DIR, "voice");
        file.mkdirs();
        CACHE_VOICE = file.getAbsolutePath();

        file = new File(CACHE_DIR, "html");
        file.mkdirs();
        ENVIROMENT_DIR_CACHE = file.getAbsolutePath();
    }

    public static String ENVIROMENT_DIR_CACHE;

    private Constants() {

    }

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION = 1;
    /**
     * 数据库名
     */
    public static final String DB_NAME = "android.db";


	//测试接口的Base url
	public static final String URL_CONTEXTPATH = "此处写自己公司的域名";

	//接口数据缓存时间
	public static final long guanLiCatchTime = 30*60*1000; //管理缓存有效时间半小时，其他接口为五分钟

	public static final long catchTime = 5*60*1000; //管理缓存有效时间半小时，其他接口为五分钟


//    ==============================================================================================================

    //登录
    public static String LOGIN = URL_CONTEXTPATH +
            "此处写自己公司的接口地址";

}
