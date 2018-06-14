package com.wuxiaolong.androidmvpsample.mvp.main;

import android.text.TextUtils;
import android.util.Log;

import com.wuxiaolong.androidmvpsample.common.MyApp;
import com.wuxiaolong.androidmvpsample.common.Word;
import com.wuxiaolong.androidmvpsample.mvp.other.BasePresenter;
import com.wuxiaolong.androidmvpsample.retrofit.ACache;
import com.wuxiaolong.androidmvpsample.retrofit.ApiCallback;
import com.wuxiaolong.androidmvpsample.retrofit.DefaultParser;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;

/**
 *
 * @ClassName:MainPresenter

 * @PackageName:com.wuxiaolong.androidmvpsample.mvp.main

 * @Create On 2018/5/18   16:17

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private ACache mCache ;

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String cityId) {
        mvpView.showLoading();
        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId),
                new ApiCallback<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String json = new String(responseBody.string());
                            MainModel mainModelBean = new DefaultParser<MainModel>().parser(json, MainModel.class);
                            if (mainModelBean ==null)
                            {
                                Log.i("test111","服务器数据异常");
                                return;
                            }
                            mvpView.getDataSuccess(mainModelBean);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("test111","ResponseBody转化成Json字符串异常");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });


//        在访问网络时需要传入bean的写法
//        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId),
//                new ApiCallback<MainModel>() {
//                    @Override
//                    public void onSuccess(MainModel model) {
//                        mvpView.getDataSuccess(model);
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        mvpView.getDataFail(msg);
//                    }
//
//
//                    @Override
//                    public void onFinish() {
//                        mvpView.hideLoading();
//                    }
//                });
    }

    public void postLogin(final String url, final HashMap map) {
        if (mCache == null)
        {
            mCache = ACache.get(MyApp.getInstance().getApplicationContext());
        }
        //判断是否可以用缓存数据
        String cacheJson = mCache.getAsString(url + map.toString());
        if (!TextUtils.isEmpty(cacheJson)) {
            Log.i("test111","拿缓存的Json："+cacheJson);
            MainModel mainModelBean = new DefaultParser<MainModel>().parser(cacheJson, MainModel.class);
            mvpView.getDataSuccess(mainModelBean);
            return;
        }

        mvpView.showLoading();
        addSubscription(apiStores.postLogin(map),
                new ApiCallback<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String netJson = new String(responseBody.string());
                            MainModel mainModelBean = new DefaultParser<MainModel>().parser(netJson, MainModel.class);
                            if (mainModelBean == null)
                            {
                                Log.i("test111","服务器数据异常");
                                return;
                            }
                            mCache.put(url+ map.toString(), netJson, Word.CACHE_TIME_5); //设置缓存时间5分钟
                            mvpView.getDataSuccess(mainModelBean);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("test111","ResponseBody转化成Json字符串异常");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

}
