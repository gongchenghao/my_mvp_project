package com.wuxiaolong.androidmvpsample.retrofit;

import com.wuxiaolong.androidmvpsample.mvp.main.MainModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
/**
 *
 * @ClassName:ApiStores

 * @PackageName:com.wuxiaolong.androidmvpsample.retrofit

 * @Create On 2018/5/18   16:19

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


//retrofit请求网络的方法: https://www.jianshu.com/p/7687365aa946
public interface ApiStores {
//    path是对应于url里直接写value的，如：http://www.test/news/1;
//    query是对应于url里面有key-value对的，如：http://www.test/news/id=1;

//    Retrofit 除了提供了传统的 Callback 形式的 API,还有 RxJava 版本的 Observable 形式 API。
//    当 RxJava 形式的时候，Retrofit 把请求封装进 Observable ，在请求结束后调用 onNext() 或在请求失败后调用 onError()。

//    使用传统callback形式时，retrofit的json在RetrofitCallback类中
//

    //baseUrl；初始化的时候，就已经将baseUrl写入Retrofit中了
    String API_SERVER_URL = "http://www.weather.com.cn/";
//    String API_SERVER_URL = "此处写自己公司的域名";


// =======================   Get请求直接返回Json的写法 =======================

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Call<ResponseBody> loadDataByRetrofit(@Path("cityId") String cityId);


    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<ResponseBody> loadDataByRetrofitRxJava(@Path("cityId") String cityId);



// =======================   Get请求需要传入Bean的写法 =======================
    //加载天气
    @GET("adat/sk/{cityId}.html")
    Call<MainModel> loadDataByRetrofit1(@Path("cityId") String cityId);


    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxJava1(@Path("cityId") String cityId);




//  ============================ Post请求，直接返回Json的方法 ==================

    @FormUrlEncoded
    @POST("此处写自己公司的接口地址")
    Observable<ResponseBody> postLogin(@FieldMap Map<String, String> map);
}
