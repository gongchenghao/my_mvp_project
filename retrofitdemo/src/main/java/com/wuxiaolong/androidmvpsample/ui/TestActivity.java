package com.wuxiaolong.androidmvpsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.mvp.main.MainModel;
import com.wuxiaolong.androidmvpsample.retrofit.RxBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 *
 * @ClassName:TestActivity

 * @PackageName:com.wuxiaolong.androidmvpsample.ui

 * @Create On 2018/5/18   16:20

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvRxbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mTvRxbus = (TextView) findViewById(R.id.tv_rxbus);
        mTvRxbus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_rxbus:

//                //使用rxbus传递Bean对象
//                sendBean();
//
//                //使用rxBus传递string
//                sendString();

//                使用rxbus传递list
//                sendList();

//                使用rxbus传递HashMap
                sendHashMap();
                break;
        }
    }

    private void sendList() {
        Log.i("test111","sendList");
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        RxBus.get().post(list);
    }

    private void sendHashMap() {
        Log.i("test111","sendHashMap");
        HashMap<String,String> map = new HashMap<>();
        map.put("1","aaa");
        map.put("2","bbb");
        map.put("3","ccc");
        RxBus.get().post(map);
    }

    private void sendString() {
        String s = "我是测试String";
        RxBus.get().post(s);
    }

    private void sendBean() {
        MainModel mainModel = new MainModel();
        MainModel.WeatherinfoBean weatherinfoBean = new MainModel.WeatherinfoBean();
        weatherinfoBean.setCity("北京111");
        mainModel.setWeatherinfo(weatherinfoBean);
        RxBus.get().post(mainModel);
    }
}
