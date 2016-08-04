package com.wen.magi.baseframe.base.net;

import android.app.Activity;

/**
 * Created by MVEN on 16/8/3.
 * <p/>
 * email: magiwen@126.com.
 */


public class NetworkParams {
    /**
     * 后端服务
     */
    public EService service;

    /**
     * UrlRequest tag,本应用使用{@link Activity#getLocalClassName()}作为网络访问的tag
     */
    public String requestTag;

}
