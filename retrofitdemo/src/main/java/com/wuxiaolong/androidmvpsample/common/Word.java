package com.wuxiaolong.androidmvpsample.common;


import android.os.Environment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 *
 * @ClassName:Word

 * @PackageName:com.wuxiaolong.androidmvpsample.common

 * @Create On 2018/5/18   16:18

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


public class Word {

	public static String cacheDir = Environment.getExternalStorageDirectory().toString() + File.separator + "Test" + File.separator;

	public static int REQUESTCODE_1 = 123 ; //6.0申请权限

	public static int CACHE_TIME_5 = 300; //五分钟的缓存时间
}
