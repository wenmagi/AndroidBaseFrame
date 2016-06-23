package com.wen.magi.androidbaseframe.base.net;

import com.wen.magi.androidbaseframe.utils.LangUtils;
import com.wen.magi.androidbaseframe.web.UrlRequest;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public class AResponse {

    public BaseResultParams parseResultParams(UrlRequest request, int statusCode) {
        BaseResultParams result = null;
        if (request == null || LangUtils.isEmpty(request.getStringData()))
            return null;
        return null;
    }
}
