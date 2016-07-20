package com.wen.magi.androidbaseframe.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.wen.magi.androidbaseframe.annotations.OnClick;
import com.wen.magi.androidbaseframe.bundles.BaseBundleParams;
import com.wen.magi.androidbaseframe.utils.InjectUtils;
import com.wen.magi.androidbaseframe.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MVEN on 16/6/14.
 * <p/>
 * email: magiwen@126.com.
 */

public abstract class BaseFragment extends Fragment implements OnClickListener {

    /**
     * 该fragment attach的activity
     */
    protected Activity activity;

    /**
     * fragment当前状态是否可见
     */
    protected boolean isVisible;

    /**
     * 是否该fragment需要延迟加载，默认true
     * </p>
     * true:支持延迟加载  false:不允许延迟加载
     */
    private boolean needLazyLoad = true;

    private boolean isPrepared = false;

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
//        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e("wwwwwwww oncreateView");
        View rootView = createView(inflater, container, savedInstanceState);
        isPrepared = true;
        onVisible();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.e("wwwwwwww %s  %s", isVisibleToUser, getUserVisibleHint());
        super.setUserVisibleHint(isVisibleToUser);

        if (!needLazyLoad)
            return;

        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            onInVisible();
        }
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
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void onInVisible() {

    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void onVisible() {
        if (!isPrepared || !isVisible)
            return;

        lazyLoad();
    }

    /**
     * 延迟加载
     * </p>
     * 子类必须复写
     */
    protected abstract void lazyLoad();

    protected void setNeedLazyLoad(boolean needLazyLoad) {
        this.needLazyLoad = needLazyLoad;
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
        if(!isValidActivity()) {
            return;
        }

        OnClickView(v);
    }

    protected abstract void OnClickView(View v);
}
