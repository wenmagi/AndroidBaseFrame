package com.wen.magi.baseframe.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.wen.magi.baseframe.base.net.BaseRequestParams;
import com.wen.magi.baseframe.base.net.BaseResultParams;
import com.wen.magi.baseframe.base.net.EService;
import com.wen.magi.baseframe.bundles.BaseBundleParams;
import com.wen.magi.baseframe.eventbus.DetachAllFragmentEvent;
import com.wen.magi.baseframe.managers.RequestQueueManager;
import com.wen.magi.baseframe.utils.ARequestHelper;
import com.wen.magi.baseframe.utils.AResponseHelper;
import com.wen.magi.baseframe.utils.InjectUtils;
import com.wen.magi.baseframe.utils.ViewUtils;
import com.wen.magi.baseframe.web.UrlRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by MVEN on 16/6/14.
 * <p/>
 * email: magiwen@126.com.
 */

public abstract class BaseFragment extends Fragment implements OnClickListener, UrlRequest.RequestDelegate {

    /**
     * 该fragment attach的activity
     */
    protected Activity activity;
    private String className;
    private boolean hasRequest = false;

    /**
     * 初始化activity，子Fragment代替{@link #getActivity()}方法
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    /**
     * 注册EventBus
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getClass() != null)
            className = getClass().getSimpleName();

        EventBus.getDefault().register(this);
    }

    /**
     * 得到Fragment填充View后，添加注解
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InjectUtils.autoInjectR(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (hasRequest)
            RequestQueueManager.cancelTag(className);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        Intent intent = new Intent(getActivity(), clazz);
        if (params != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseBundleParams.PARAM_SKEY, params);
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * {@link #activity} 是否可用
     *
     * @return
     */
    public boolean isValidActivity() {
        if (isRemoving())
            return false;

        return activity != null && !activity.isFinishing();
    }

    @Override
    public void onClick(View v) {
        if (!isValidActivity()) {
            return;
        }

        OnClickView(v);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishAllFragmentEvent(DetachAllFragmentEvent event) {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
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
        if (service == null || !isValidActivity()) {
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
     * @param request
     * @param statusCode
     * @param errorString
     */
    protected void onResponseError(UrlRequest request, int statusCode, String errorString) {
    }

    /**
     * 网络访问失败
     *
     * @param request
     * @param statusCode
     */
    protected void onNetError(UrlRequest request, int statusCode) {
    }


    /**
     * 子类必须复写，代替onClick事件
     *
     * @param v 目标View
     */
    protected abstract void OnClickView(View v);
}
