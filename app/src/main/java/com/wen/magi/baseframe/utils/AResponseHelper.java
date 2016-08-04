package com.wen.magi.baseframe.utils;

import com.wen.magi.baseframe.base.net.BaseResultParams;
import com.wen.magi.baseframe.utils.LangUtils;
import com.wen.magi.baseframe.web.UrlRequest;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public class AResponseHelper {

    public static BaseResultParams parseResultParams(UrlRequest request, int statusCode) {
        BaseResultParams result = null;
        if (request == null || LangUtils.isEmpty(request.getStringData()))
            return null;
        return null;
    }
}
