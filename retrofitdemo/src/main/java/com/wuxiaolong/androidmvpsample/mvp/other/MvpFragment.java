package com.wuxiaolong.androidmvpsample.mvp.other;


import android.os.Bundle;
import android.view.View;

import com.wuxiaolong.androidmvpsample.ui.BaseFragment;

/**
 *
 * @ClassName:MvpFragment

 * @PackageName:com.wuxiaolong.androidmvpsample.mvp.other

 * @Create On 2018/5/18   16:18

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
