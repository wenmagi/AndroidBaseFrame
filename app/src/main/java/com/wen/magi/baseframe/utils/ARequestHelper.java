package com.wen.magi.baseframe.utils;

import com.android.volley.Network;
import com.wen.magi.baseframe.base.net.BaseRequestParams;
import com.wen.magi.baseframe.base.net.EService;
import com.wen.magi.baseframe.base.net.NetworkParams;
import com.wen.magi.baseframe.utils.LangUtils;
import com.wen.magi.baseframe.utils.StringUtils;
import com.wen.magi.baseframe.web.UrlRequest;

import static com.wen.magi.baseframe.base.net.BaseRequestParams.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public class ARequestHelper {

    public static void start(String clazzName, UrlRequest.RequestDelegate delegate, EService service, BaseRequestParams params) {

        if (delegate == null || service == null)
            return;

        String url = service.getUrl();
        if (LangUtils.isEmpty(url))
            return;

        UrlRequest r = initUrlRequest(service, params, url);
        r.setNetWorkParams(initNetworkParams(clazzName, service));
        r.setDelegate(delegate);
        r.start();
    }

    private static NetworkParams initNetworkParams(String clazz, EService service) {
        NetworkParams networkParams = new NetworkParams();
        networkParams.service = service;
        networkParams.requestTag = clazz;
        return networkParams;
    }

    private static UrlRequest initUrlRequest(EService service, BaseRequestParams params, String url) {
        if (params == null)
            return new UrlRequest(url);

        HashMap<String, Object> param = params.getRequestParams();

        if (param != null && param.containsKey(URL_PARAMS)) {
            Object[] args = (Object[]) param.get(URL_PARAMS);
            if (args != null)
                url = StringUtils.format(url, args);
            param.remove(URL_PARAMS);
        }

        UrlRequest r;
        if (service.getUrlType() == EService.METHOD_OF_GET) {
            r = new UrlRequest(url, param);
        } else {
            r = new UrlRequest(url);
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                r.addPostParams(entry.getKey(), (String) entry.getValue());
            }
        }
        return r;
    }
}
