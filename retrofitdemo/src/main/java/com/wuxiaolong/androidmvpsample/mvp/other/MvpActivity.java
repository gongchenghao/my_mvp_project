package com.wuxiaolong.androidmvpsample.mvp.other;

import android.os.Bundle;

import com.wuxiaolong.androidmvpsample.ui.BaseActivity;


/**
 *
 * @ClassName:MvpActivity

 * @PackageName:com.wuxiaolong.androidmvpsample.mvp.other

 * @Create On 2018/5/18   16:18

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
