package com.wen.magi.baseframe.utils;

import com.alibaba.fastjson.JSON;
import com.wen.magi.baseframe.base.net.BaseResultParams;
import com.wen.magi.baseframe.web.UrlRequest;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public class AResponseHelper {

    public static BaseResultParams parseResultParams(UrlRequest request) {
        return parseResultParams(request, UrlRequest.REQUEST_SUCCESS_CODE);
    }

    public static BaseResultParams parseResultParams(UrlRequest request, int statusCode) {
        BaseResultParams result = new BaseResultParams();
        result.code = statusCode;


        if (request == null || LangUtils.isEmpty(request.getStringData()))
            return result;

        String jsonStr = request.getStringData();
        if (jsonStr == null)
            return result;

        result = JSON.parseObject(jsonStr, request.getNetworkParams().service.getClazz());

        return result;
    }
}
