package com.wuxiaolong.androidmvpsample.common;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
/**
 *
 * @ClassName:MyApp

 * @PackageName:com.wuxiaolong.androidmvpsample.common

 * @Create On 2018/5/18   16:18

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


public class MyApp extends Application {

    public static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //适配Android7.0 URI 拍照，应用安装问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        instance = this;
    }

    //单例模式中获取唯一的MyApp实例
    public static MyApp getInstance() {
        if (null == instance) {
            instance = new MyApp();
        }
        return instance;
    }
}
