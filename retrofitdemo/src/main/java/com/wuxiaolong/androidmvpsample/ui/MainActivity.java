package com.wuxiaolong.androidmvpsample.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.common.Constants;
import com.wuxiaolong.androidmvpsample.common.Word;
import com.wuxiaolong.androidmvpsample.mvp.main.MainModel;
import com.wuxiaolong.androidmvpsample.mvp.main.MainPresenter;
import com.wuxiaolong.androidmvpsample.mvp.main.MainView;
import com.wuxiaolong.androidmvpsample.mvp.other.MvpActivity;
import com.wuxiaolong.androidmvpsample.retrofit.ApiCallback;
import com.wuxiaolong.androidmvpsample.retrofit.DefaultParser;
import com.wuxiaolong.androidmvpsample.retrofit.RetrofitCallback;
import com.wuxiaolong.androidmvpsample.retrofit.RxBus;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 *
 * @ClassName:MainActivity

 * @PackageName:com.wuxiaolong.androidmvpsample.ui

 * @Create On 2018/5/18   16:20

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    private TextView text;
    private TextView text_2;
    //项目所需权限(针对6.0)
    private String[] promissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        text_2 = (TextView) findViewById(R.id.text_2);
        initToolBarAsHome(getString(R.string.title));
        operateBus();
        operateBusString();
        operateBusArrayList();
        operateBusHashMap();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

//   使用rxBus获取Bean对象
    private void operateBus() {
        RxBus.get().toFlowable(MainModel.class)
                .map(new Function<Object, MainModel>() {
                    @Override
                    public MainModel apply(@NonNull Object o) throws Exception {
                        Log.i("test111","apply");
                        return (MainModel) o;
                    }

                })
                .subscribe(new Consumer<MainModel>() {
                    @Override
                    public void accept(@NonNull MainModel mainModel) throws Exception {
                        text.setText(mainModel.getWeatherinfo().getCity());
                        Log.i("test111","test:"+mainModel.getWeatherinfo().getCity());
                    }
                });

    }

    //使用rxbus获取字符串
    private void operateBusString() {
        RxBus.get().toFlowable(String.class)
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(@NonNull Object o) throws Exception {
                        Log.i("test111","string_apply");
                        return (String) o;
                    }

                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String string) throws Exception {
                        text_2.setText(string);
                        Log.i("test111","获取到的String："+string);
                    }
                });

    }
    //使用rxbus获取ArrayList
    private void operateBusArrayList() {
        RxBus.get().toFlowable(ArrayList.class)
                .map(new Function<Object, ArrayList>() {
                    @Override
                    public ArrayList apply(@NonNull Object o) throws Exception {
                        Log.i("test111","string_apply");
                        return (ArrayList) o;
                    }

                })
                .subscribe(new Consumer<ArrayList>() {
                    @Override
                    public void accept(@NonNull ArrayList arrayList) throws Exception {
                        Log.i("test111","获取到的arrayList："+arrayList.toString());
                    }
                });
    }

    //使用rxbus获取hashmap
    private void operateBusHashMap() {
        RxBus.get().toFlowable(HashMap.class)
                .map(new Function<Object, HashMap>() {
                    @Override
                    public HashMap apply(@NonNull Object o) throws Exception {
                        Log.i("test111","string_apply");
                        return (HashMap) o;
                    }

                })
                .subscribe(new Consumer<HashMap>() {
                    @Override
                    public void accept(@NonNull HashMap hashMap) throws Exception {
                        Log.i("test111","获取到的hashMap："+hashMap.toString());
                        String aKey = (String) hashMap.get("1");
                        Log.i("test111","aKey:"+aKey);
                    }
                });

    }


    @Override
    public void getDataSuccess(MainModel mainModelBean) {
        Log.i("test111","mvp回调成功");
        startActivity(new Intent(MainActivity.this,TestActivity.class));
    }

    @Override
    public void getDataFail(String msg) {
        Log.i("test111","getDataFail:"+msg);
        toastShow(getString(R.string.net_error));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                Log.i("test111","button0");
                loadDataByRetrofit();
                break;
            case R.id.button1:
                Log.i("test111","button1");
                loadDataByRetrofitRxJava();
                break;
            case R.id.button2:
                //请求接口
                mvpPresenter.loadDataByRetrofitRxjava("101310222");
                break;
            case R.id.btn_post: //通过post请求获取数据
                Log.i("test111","post请求访问网络");
                hasPromission();
                break;
        }
    }
    private void toLogin() {
        HashMap map = new HashMap();
        map.put("username", "gongchenghao");
        map.put("password", "888888");
        map.put("Landmarks", "868053033617499");
        map.put("sign", "1526628587259PXAIYA");
        map.put("loginToken", "63298990f6aee6a5e37b0e3fc0a821ca");
        //请求接口
        mvpPresenter.postLogin(Constants.LOGIN,map);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void hasPromission() {
        Log.i("test111","hasPromission");

        boolean hasPromission = verifyPermissions(promissions);
        Log.i("test111","hasPromission:"+hasPromission);
        if (hasPromission) {
            toLogin();
        } else {
            Log.i("test111","申请权限");
            requestPermissions(promissions, Word.REQUESTCODE_1); //申请权限
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Word.REQUESTCODE_1)
        {
            boolean hasPromission = verifyPermissions(promissions);
            if (hasPromission)
            {
                toLogin();
            }else {
                showMissingPermissionDialog();
            }
        }
    }

    private void loadDataByRetrofit() {
        Log.i("test111","loadDataByRetrofit");
        showProgressDialog();
        Call<ResponseBody> call = apiStores().loadDataByRetrofit("101190201");
        call.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody model) {
                try {
                    String netJson = new String(model.string());
                    MainModel mainModelBean = new DefaultParser<MainModel>().parser(netJson, MainModel.class);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
        addCalls(call);
    }


    //全国+国外主要城市代码http://mobile.weather.com.cn/js/citylist.xml
    private void loadDataByRetrofitRxJava() {
        Log.i("test111","loadDataByRetrofitRxJava");
        showProgressDialog();
        Observable<ResponseBody> responseBodyObservable = apiStores().loadDataByRetrofitRxJava("101220602");
        addSubscription(responseBodyObservable,
                new ApiCallback<ResponseBody>() {

                    @Override
                    public void onSuccess(ResponseBody model) {
                        try {
                            String netJson = new String(model.string());
                            MainModel mainModelBean = new DefaultParser<MainModel>().parser(netJson, MainModel.class);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onFinish() {
                        dismissProgressDialog();
                    }
                });
    }


//    ========================================= 以下为访问网络时需要传入Bean的方法 ================

//    需要在访问网络时传入bean的写法
//    private void loadDataByRetrofitRxJava1() {
//        showProgressDialog();
//        addSubscription(apiStores().loadDataByRetrofitRxJava1("101220602"),
//                new ApiCallback<MainModel>() {
//
//                    @Override
//                    public void onSuccess(MainModel model) {
//                        dataSuccess(model);
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        toastShow(msg);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        dismissProgressDialog();
//                    }
//                });
//    }


    //    需要传bean的写法
//    private void loadDataByRetrofit() {
//        Log.i("test111","loadDataByRetrofit");
//        showProgressDialog();
//        Call<MainModel> call = apiStores().loadDataByRetrofit1("101190201");
//        call.enqueue(new RetrofitCallback<MainModel>() {
//
//
//            @Override
//            public void onSuccess(MainModel model) {
//
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//
//            }
//
//            @Override
//            public void onThrowable(Throwable t) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//        addCalls(call);
//    }

//    private void dataSuccess(MainModel model) {
//        Log.i("test111","dataSuccess");
//        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
//        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
//                + getResources().getString(R.string.wd) + weatherinfo.getWD()
//                + getResources().getString(R.string.ws) + weatherinfo.getWS()
//                + getResources().getString(R.string.time) + weatherinfo.getTime();
//        text.setText(showData);
//    }
}
