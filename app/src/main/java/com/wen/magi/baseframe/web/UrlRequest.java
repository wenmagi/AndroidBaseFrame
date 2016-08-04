package com.wen.magi.baseframe.web;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wen.magi.baseframe.R;
import com.wen.magi.baseframe.base.net.NetworkParams;
import com.wen.magi.baseframe.managers.AppManager;
import com.wen.magi.baseframe.managers.AppSessionManager;
import com.wen.magi.baseframe.managers.RequestQueueManager;
import com.wen.magi.baseframe.utils.LangUtils;
import com.wen.magi.baseframe.utils.LogUtils;

import java.net.URL;
import java.util.HashMap;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Priority.IMMEDIATE;
import static com.android.volley.Request.Priority.NORMAL;
import static com.wen.magi.baseframe.utils.WebUtils.compositeUrl;
import static com.wen.magi.baseframe.utils.WebUtils.createURL;

/**
 * Created by MVEN on 16/6/19.
 * <p/>
 * email: magiwen@126.com.
 */

public class UrlRequest {
    public interface RequestDelegate {
        void requestFailed(UrlRequest request, int statusCode, String errorString);

        void requestFinished(UrlRequest request);
    }

    /**
     * 统一格式，网络访问返回数据格式一律为:
     * <p/>
     * {
     * "code": 0,
     * "data": {}
     * }
     * <p/>
     * data对应的数据才是我们需要的数据
     * <p/>
     * 如果请求错误，返回
     * *{
     * "code": 102,
     * "msg": "无该用户"
     * }
     */
    private static final String CODE_KEY = "code";
    private static final String DATA_KEY = "data";
    private static final String ERROR_KEY = "msg";
    private static final int MY_SOCKET_TIMEOUT_MS = 45 * 1000;
    /**
     * 访问成功时返回的code值
     */
    public static final int REQUEST_SUCCESS_CODE = 0;


    private static String mURL;
    protected RequestDelegate mDelegate;
    protected NetworkParams networkParams;

    private HashMap<String, String> mPostParams;
    private int mMethod = GET;
    private String mStringData;


    public UrlRequest(String url) {
        autoCompileUrl(url, null);
    }

    public UrlRequest(String url, HashMap<String, Object> params) {
        autoCompileUrl(url, params);
    }

    private void autoCompileUrl(String url, HashMap<String, Object> params) {
        URL u = createURL(params == null ? url : compositeUrl(url, params));
        if (u != null)
            mURL = u.toString();
    }


    public String getStringData() {
        return mStringData;
    }

    public void setDelegate(RequestDelegate delegate) {
        mDelegate = delegate;
    }

    public void setNetWorkParams(NetworkParams params) {
        networkParams = params;
    }

    public NetworkParams getNetworkParams() {
        return networkParams;
    }

    public void addPostParams(String key, String value) {
        if (mPostParams == null) {
            mPostParams = new HashMap<>();
        }
        mPostParams.put(key, String.valueOf(value));
    }

    /**
     * 网络访问开启,将request加入网络访问队列
     */
    public void start() {
        start(true);
    }

    public void start(boolean immediate) {
        if (mPostParams != null && mPostParams.size() > 0) {
            mMethod = POST;
        }

        StringRequestPriority requestPriority = new StringRequestPriority(mMethod, mURL, succListener, errorListener);
        if (mMethod == POST) {
            requestPriority.setPostParams(mPostParams);
        }

        requestPriority.setShouldCache(false);
        requestPriority.setPriority(immediate ? IMMEDIATE : NORMAL);
        requestPriority.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (networkParams != null)
            RequestQueueManager.addRequest(requestPriority, networkParams.requestTag);
        else
            RequestQueueManager.addRequest(requestPriority);
    }

    /**
     * 网络请求失败回调类
     */
    private Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            int code = error.networkResponse != null ? error.networkResponse.statusCode : -1;
            LogUtils.w("onErrorResponse %s", error);
            if (error instanceof NoConnectionError) {
                AppSessionManager.getSessionManager().setSessionMode(AppSessionManager.SessionMode.OffLine);
            }
            fireDelegate(false, code, null);
        }
    };

    /**
     * 网络请求成功回调类
     */
    private Response.Listener<String> succListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            String errorString = AppManager.getApplicationContext().getString(R.string.no_network_warn);
            JSONObject json = null;
            try {
                json = JSON.parseObject(response);
            } catch (Exception e) {

            }
            if (json != null) {
                int code = json.getIntValue(CODE_KEY);
                String errorMsg = json.getString(ERROR_KEY);
                mStringData = json.getString(DATA_KEY);
                if (LangUtils.isNotEmpty(errorMsg)) {
                    errorString = errorMsg;
                }
                if (code == REQUEST_SUCCESS_CODE) {
                    fireDelegate(true, REQUEST_SUCCESS_CODE, null);
                } else {
                    if (code == 102) {//LOGIN_REQUIRED = (102, '需要登录')
                        Toast.makeText(AppManager.getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        AppManager.logout();
                    } else {
                        fireDelegate(false, code, errorString);
                    }
                }
            } else {
                if (LangUtils.isNotEmpty(response)) {
                    mStringData = response;
                    fireDelegate(true, REQUEST_SUCCESS_CODE, null);
                } else {
                    fireDelegate(false, -1, errorString);
                }
            }
        }
    };


    private void fireDelegate(boolean result, int code, String errorString) {
        RequestDelegate d = mDelegate;
        if (!result) {
            LogUtils.w("requst failed, url = %s code = %s", mURL, code);
        }
        if (d != null) {
            if (result) {
                d.requestFinished(this);
            } else {
                d.requestFailed(this, code, errorString);
            }
        }
    }
}
