package com.wen.magi.baseframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by MVEN on 16/7/20.
 * <p/>
 * email: magiwen@126.com.
 * <p/>
 * 支持延迟加载的Fragment
 */

/**
 * ViewPager 中的Fragment生命周期
 * setUserVisibleHint: isVisibleToUser = false
 * onAttach
 * onCreate
 * setUserVisibleHint: isVisibleToUser = true
 * onCreateView
 * onActivityCreated
 * onStart
 * onResume
 * onPause
 * onStop
 * onDestroyView
 * onDestroy
 * onDetach
 */
public abstract class BaseLazyLoadFragment extends BaseFragment {

    /**
     * fragment当前状态是否加载过
     */
    private boolean isLoaded;

    /**
     * fragment是否已经CreateView
     */
    private boolean isCreatedView = false;

    protected View inVisibleView, visibleView;

    private RelativeLayout rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        visibleView = createView(inflater, container, savedInstanceState);

        rootView = createRootView(visibleView);
        isCreatedView = true;
        onInVisible();
        return rootView;
    }

    private RelativeLayout createRootView(View visibleView) {
        RelativeLayout rootView = new RelativeLayout(activity);
        inVisibleView = createInvisibleView();
        RelativeLayout.LayoutParams visibleViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams inVisibleViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (visibleView != null)
            rootView.addView(visibleView, visibleViewParams);
        if (inVisibleView != null)
            rootView.addView(inVisibleView, inVisibleViewParams);
        return rootView;
    }


    /**
     * 子类必须继承的方法，代替{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isLoaded && isCreatedView) {
            onVisible();
        }
    }

    /**
     * setUserVisible再onCreateView之前调用
     * 如果处于ViewPager首页
     * isCreatedView = true;并没有执行
     * 所以不会加载数据，此处需要做判断
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getUserVisibleHint())
            onVisible();
    }

    /**
     * Fragment不可见时展示的界面
     */
    private void onInVisible() {
        if (inVisibleView != null)
            inVisibleView.setVisibility(View.VISIBLE);
        if (visibleView != null)
            visibleView.setVisibility(View.GONE);
    }

    /**
     * Fragment可见时执行的操作
     */
    private void onVisible() {
        isLoaded = true;

        if (inVisibleView != null) {
            rootView.removeView(inVisibleView);
        }
        if (visibleView != null) {
            visibleView.setVisibility(View.VISIBLE);
        }
        lazyLoad();
    }

    /**
     * 延迟加载
     * </p>
     * 子类必须复写
     */
    protected abstract void lazyLoad();

    /**
     * 统一延迟加载默认页面样式
     *
     * @return
     */
    protected View createInvisibleView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (!isValidActivity()) {
            return;
        }

        OnClickView(v);
    }

    /**
     * 子类必须复写，代替onClick事件
     *
     * @param v 目标View
     */
    protected abstract void OnClickView(View v);
}
