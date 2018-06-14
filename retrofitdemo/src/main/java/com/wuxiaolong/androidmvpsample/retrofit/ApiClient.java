package com.wuxiaolong.androidmvpsample.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @ClassName:ApiClient

 * @PackageName:com.wuxiaolong.androidmvpsample.retrofit

 * @Create On 2018/5/18   16:19

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public class ApiClient {
    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
//                  只用retrofit时，加上这两句代码则需要在访问网络时先把bean传进去
//                    .addConverterFactory(GsonConverterFactory.create())
//                    使用retrofit+rxjava时，加上这句代码
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
