package com.wuxiaolong.androidmvpsample.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @ClassName:RetrofitCallback

 * @PackageName:com.wuxiaolong.androidmvpsample.retrofit

 * @Create On 2018/5/18   16:19

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


public abstract class RetrofitCallback<M> implements Callback<M> {

//    public abstract void onSuccess(M model);
    public abstract void onSuccess(M model);

    public abstract void onFailure(int code, String msg);

    public abstract void onThrowable(Throwable t);

    public abstract void onFinish();

    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onFailure(response.code(), response.errorBody().toString());
        }
        onFinish();
    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        onThrowable(t);
        onFinish();
    }
}
