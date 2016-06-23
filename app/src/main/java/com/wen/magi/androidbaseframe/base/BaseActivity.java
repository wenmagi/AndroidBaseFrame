package com.wen.magi.androidbaseframe.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wen.magi.androidbaseframe.base.net.BaseRequestParams;
import com.wen.magi.androidbaseframe.base.net.EService;
import com.wen.magi.androidbaseframe.base.net.ARequest;
import com.wen.magi.androidbaseframe.bundles.BaseBundleParams;
import com.wen.magi.androidbaseframe.utils.InjectUtils;
import com.wen.magi.androidbaseframe.web.UrlRequest;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MVEN on 16/5/3.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, UrlRequest.RequestDelegate {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initProperties();
        initView();
    }

    private void initProperties() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        InjectUtils.autoInjectR(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        InjectUtils.autoInjectR(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

    }

    private void initView() {
        RelativeLayout contentView = new RelativeLayout(this);
        setContentView(contentView);
    }

    /**
     * 跳转到其他页面
     *
     * @param clazz 目标activity
     */
    protected void startActivity(Class<? extends BaseActivity> clazz) {
        startActivity(clazz, null, 0);
    }

    /**
     * 跳转到其他页面
     *
     * @param clazz     目标activity
     * @param pageParam 目标页面参数
     */
    protected void startActivity(Class<? extends BaseActivity> clazz, BaseBundleParams pageParam) {
        startActivity(clazz, pageParam, 0);
    }

    /**
     * 跳转到其他页面
     *
     * @param clazz       目标activity
     * @param requestCode 目标activity参数
     */
    protected void startActivity(Class<? extends BaseActivity> clazz, int requestCode) {
        startActivity(clazz, null, requestCode);
    }

    /**
     * 跳转到其他页面
     *
     * @param clazz       目标activity
     * @param params      每个activity之间的参数需封装成{@link BaseBundleParams}
     * @param requestCode 请求码
     */
    protected void startActivity(Class<? extends BaseActivity> clazz, BaseBundleParams params, int requestCode) {
        if (!isValidActivity()) {
            return;
        }
        Intent intent = new Intent(this, clazz);
        if (params != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseBundleParams.PARAM_SKEY, params);
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 判断当前Activity是否有效，如果当前Activity已经被回收，返回false
     *
     * @return 当前Activity是否有效
     */
    protected boolean isValidActivity() {
        boolean flag;
        try {
            flag = !isFinishing();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                flag = flag && !isDestroyed();
            }
        } catch (RuntimeException e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 发送HTTP请求，返回结果在requestFinished、requestFailed中处理
     *
     * @param service 后端服务
     */
    protected void startRequest(EService service) {
        startRequest(service, null);
    }

    /**
     * 发送HTTP请求，返回结果在requestFinished、requestFailed中处理
     *
     * @param service 后端服务
     * @param param   HTTP请求参数
     */
    protected void startRequest(EService service, BaseRequestParams param) {
        startRequest(service, param, 0);
    }

    /**
     * 发送HTTP请求，返回结果在requestFinished、requestFailed中处理
     *
     * @param service     后端服务
     * @param param       HTTP请求参数
     * @param requestCode 该请求的编号
     */
    protected void startRequest(EService service, BaseRequestParams param, int requestCode) {
        String tag = null;
        if (getClass() != null) {
            tag = getClass().getSimpleName();
        }
        startRequest(service, param, requestCode, tag);
    }

    /**
     * 发送HTTP请求，返回结果在requestFinished、requestFailed中处理
     *
     * @param service     后端服务
     * @param param       HTTP请求参数
     * @param requestCode 该请求的编号
     * @param requestTag  该请求的tag
     */
    protected void startRequest(EService service, BaseRequestParams param, int requestCode, String requestTag) {
        if (service == null || isFinishing()) {
            return;
        }

        ARequest.start(this, service, param);
    }


    @Override
    public void requestFailed(UrlRequest request, int statusCode, String errorString) {

    }

    @Override
    public void requestFinished(UrlRequest request) {
        if (!isValidActivity() || request == null)
            return;

    }


}