package com.wuxiaolong.androidmvpsample.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxiaolong.androidmvpsample.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 *
 * @ClassName:BaseFragment

 * @PackageName:com.wuxiaolong.androidmvpsample.ui

 * @Create On 2018/5/18   16:20

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */


public class BaseFragment extends Fragment {
    public Activity mActivity;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
    }

    public Toolbar initToolBar(View view, String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
        return toolbar;
    }


    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }

    private CompositeDisposable mCompositeDisposable;

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void addSubscription(DisposableObserver observer) {
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(observer);
    }
}
