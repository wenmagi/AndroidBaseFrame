package com.wen.magi.baseframe.models.net.request;

import com.wen.magi.baseframe.base.net.BaseRequestParams;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public class ATestRequestParams extends BaseRequestParams {

    private static final long serialVersionUID = -8563053649014066283L;
    public int limit;
    public int nextOffset;
    public long gymID;

    @Override
    public void addRequestParams() {
        addParam("limit", limit);
        addParam("offset", nextOffset);
        addParam("gym_id", gymID);
    }
}