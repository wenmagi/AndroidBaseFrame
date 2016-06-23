package com.wen.magi.androidbaseframe.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.magi.androidbaseframe.bundles.BaseBundleParams;
import com.wen.magi.androidbaseframe.utils.InjectUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MVEN on 16/6/14.
 * <p/>
 * email: magiwen@126.com.
 */

public class BaseFragment extends Fragment {
    protected Activity activity;

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
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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


    public boolean isValidActivity() {
        if (isRemoving())
            return false;

        Activity activity = getActivity();
        return activity != null && !activity.isFinishing();
    }
}
