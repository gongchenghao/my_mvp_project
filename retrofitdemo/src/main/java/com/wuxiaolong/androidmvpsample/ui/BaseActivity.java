package com.wuxiaolong.androidmvpsample.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.retrofit.ApiStores;
import com.wuxiaolong.androidmvpsample.retrofit.ApiClient;
import com.wuxiaolong.androidmvpsample.utils.SystemDialogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 *
 * @ClassName:BaseActivity

 * @PackageName:com.wuxiaolong.androidmvpsample.ui

 * @Create On 2018/5/18   16:20

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    private CompositeDisposable mCompositeDisposable;
    private List<Call> calls;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mActivity = this;
    }


    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        super.onDestroy();
    }

    public ApiStores apiStores() {
        return ApiClient.retrofit().create(ApiStores.class);
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }
//   参数一:Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件
//   参数二:Observer 即观察者，它决定事件触发的时候将有怎样的行为
    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void onUnsubscribe() {
        Log.i("test111","onUnSubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    public Toolbar initToolBar(String title) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBarAsHome(String title) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

    /**
     * 检测是否所有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     *
     */
    public boolean verifyPermissions(String[] grantResults) {
        for (String result : grantResults) {
            if (ContextCompat.checkSelfPermission(this, result) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    public void showMissingPermissionDialog() {
        new SystemDialogUtils().showMissingPermissionDialog(
                this,
                "提示",
                "当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。",
                "设置",
                "取消", new SystemDialogUtils.AfterClick() {
                    @Override
                    public void confirm() {
                        startAppSettings();
                    }

                    @Override
                    public void cancle() {
                    }
                });
    }
    /**
     *  启动应用的设置
     *
     * @since 2.5.0
     *
     */
    private void startAppSettings() {
        Log.i("test111","无权限，且用户点击了设置");

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + "eiyudensetsu.ginga.youxin.com.yinheyingxiong"));
        startActivity(intent);
    }

}
