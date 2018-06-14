package com.wuxiaolong.androidmvpsample.retrofit;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wuxiaolong.androidmvpsample.common.MyApp;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 *
 * @ClassName:DefaultParser

 * @PackageName:com.wuxiaolong.androidmvpsample.retrofit

 * @Create On 2018/5/18   16:19

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public class DefaultParser<T> implements Parser<T> {

    @Override
    public T parser(String json, Class<T> tClass) {

        if (TextUtils.isEmpty(json) || tClass == null) {
            return null;
        }

        T bean = null;

        try {
            Gson gson = new Gson();
            bean = gson.fromJson(json, tClass);
        } catch (Exception exp) {
            exp.printStackTrace();
			Toast.makeText(MyApp.getInstance().getApplicationContext(),"服务器数据异常",Toast.LENGTH_SHORT).show();
			Log.i("test111","Json解析异常："+exp.getMessage().toString());
        }
        return bean;
    }
}
