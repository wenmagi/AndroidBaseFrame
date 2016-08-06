package com.wen.magi.baseframe.base.net;

import com.wen.magi.baseframe.utils.LangUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public abstract class BaseRequestParams implements Serializable {
    private static final long serialVersionUID = -2245239887458887343L;
    /**
     * 请求参数对象转换为HashMap
     */
    public HashMap<String, Object> params;

    /**
     * 存储拼接url参数的key值
     *
     * @like "client/uid=%s/count=%s/follow"
     */
    public static final String URL_PARAMS = "url_params";

    public HashMap<String, Object> getRequestParams() {
        addRequestParams();

        return params;
    }

    protected abstract void addRequestParams();


    /**
     * 添加url拼接参数，注意顺序
     *
     * @param args
     */
    public void addUrlParams(Object... args) {
        if (args == null)
            return;

        params.put(URL_PARAMS, args);
    }

    /**
     * 添加请求参数
     *
     * @param key   请求参数key值
     * @param value 请求参数value值
     */
    public void addParam(String key, Object value) {
        if (LangUtils.isEmpty(key))
            return;
        if (params == null)
            params = new HashMap<>();
        params.put(key, value);
    }
}
