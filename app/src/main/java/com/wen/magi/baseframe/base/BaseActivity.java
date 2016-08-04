package com.wen.magi.baseframe.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wen.magi.baseframe.base.net.BaseRequestParams;
import com.wen.magi.baseframe.base.net.BaseResultParams;
import com.wen.magi.baseframe.base.net.EService;
import com.wen.magi.baseframe.bundles.BaseBundleParams;
import com.wen.magi.baseframe.managers.RequestQueueManager;
import com.wen.magi.baseframe.utils.ARequestHelper;
import com.wen.magi.baseframe.utils.AResponseHelper;
import com.wen.magi.baseframe.utils.InjectUtils;
import com.wen.magi.baseframe.utils.ViewUtils;
import com.wen.magi.baseframe.web.UrlRequest;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MVEN on 16/5/3.
 * <p/>
 * 子类可以调用的方法{@link #startActivity(Intent)} {@link #startRequest(EService)}
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, UrlRequest.RequestDelegate {

    public enum TRANSITION {
        LEFT_IN,
        TOP_IN,
        RIGHT_IN,
        BOTTOM_IN
    }

    protected TRANSITION transition = TRANSITION.LEFT_IN;
    private boolean hasRequest = false;
    //用作request的tag
    private String className;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProperties();
        initView();
        className = getLocalClassName();
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

    /**
     * 点击home键，跳转其他页面，横竖屏切换,按下电源键
     * <p/>
     * 均可触发
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 不一定与{@link #onSaveInstanceState(Bundle)}成对出现
     * <p/>
     * #BaseActivity 确定被销毁后，才可触发
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 页面关闭后，
     * 1.unregister EventBus
     * 2.取消该页面在队列中存放的网络请求
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (hasRequest)
            RequestQueueManager.cancelTag(className);
    }

    @Override
    public void onClick(View v) {
        if (isValidActivity())
            OnClickView(v);
    }

    private void initView() {
        RelativeLayout contentView = new RelativeLayout(this);
        setContentView(contentView);
    }

    protected void setTransition(TRANSITION transition) {
        this.transition = transition;
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
    private boolean isValidActivity() {
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

        hasRequest = true;
        ARequestHelper.start(className, this, service, param);
    }


    @Override
    public void requestFailed(final UrlRequest request, final int statusCode, final String errorString) {
        if (!isValidActivity())
            return;

        ViewUtils.runInHandlerThread(new Runnable() {
            @Override
            public void run() {
                if (errorString == null) {
                    onNetError(request, statusCode);
                } else {
                    onResponseError(request, statusCode, errorString);
                }
            }
        });
    }

    @Override
    public void requestFinished(final UrlRequest request) {
        if (!isValidActivity() || request == null)
            return;

        final BaseResultParams resultParams = AResponseHelper.parseResultParams(request);

        if (resultParams == null)
            return;

        if (!ViewUtils.isInMainThread())
            ViewUtils.runInHandlerThread(new Runnable() {
                @Override
                public void run() {
                    onResponseSuccess(request, resultParams);
                }
            });
        else
            onResponseSuccess(request, resultParams);
    }

    /**
     * 子类可复写的方法
     *
     * @param request
     * @param resultParams
     */

    /**
     * 网络访问成功 返回数据
     *
     * @param request      网络请求
     * @param resultParams 返回数据对象
     */
    protected void onResponseSuccess(UrlRequest request, BaseResultParams resultParams) {
    }

    /**
     * 网络访问成功，但服务器未返回正确数据，返回errorString
     *
     * @param request     网络请求
     * @param statusCode  返回码
     * @param errorString 错误信息
     */
    protected void onResponseError(UrlRequest request, int statusCode, String errorString) {
    }

    /**
     * 网络访问失败
     *
     * @param request    网络请求
     * @param statusCode 返回码
     */
    protected void onNetError(UrlRequest request, int statusCode) {
    }

    /**
     * 点击事件
     *
     * @param v
     */
    protected abstract void OnClickView(View v);
}
