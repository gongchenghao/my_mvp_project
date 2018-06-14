package com.wuxiaolong.androidmvpsample.mvp.main;

import okhttp3.ResponseBody;

/**
 *
 * @ClassName:MainView

 * @PackageName:com.wuxiaolong.androidmvpsample.mvp.main

 * @Create On 2018/5/18   16:18

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public interface MainView extends BaseView {

//    void getDataSuccess(MainModel model);
//    void getDataSuccess(ResponseBody responseBody);
    void getDataSuccess(MainModel model);

    void getDataFail(String msg);

}
