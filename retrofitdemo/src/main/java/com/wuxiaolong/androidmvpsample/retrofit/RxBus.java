package com.wuxiaolong.androidmvpsample.retrofit;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 *
 * @ClassName:RxBus

 * @PackageName:com.wuxiaolong.androidmvpsample.rxbus

 * @Create On 2018/5/18   16:20

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


//有背压处理（Backpressure）的 Rxbus
public class RxBus {
    private final FlowableProcessor<Object> mBus;
    private static ArrayList<String> mArrayList = new ArrayList<>();

    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        mArrayList.add(tClass.toString());
        return mBus.ofType(tClass);
    }

    //用来判断rxbus是否重复注册，比如已经注册过DeleteGongGaoBean.class了，
    // 就不能再注册了，否则会出现发送一个消息，接收到好几个消息的情况
    public static boolean isAdded(Class tClass)
    {
        boolean isAdd = false;
        for (int i = 0; i <mArrayList.size() ; i++) {
            if (tClass.toString().equals(mArrayList.get(i)))
            {
                isAdd = true;
            }
        }
        return isAdd;
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }
}
