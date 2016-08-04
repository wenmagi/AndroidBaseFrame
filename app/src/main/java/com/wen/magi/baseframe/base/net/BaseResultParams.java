package com.wen.magi.baseframe.base.net;

import java.io.Serializable;

/**
 * Created by MVEN on 16/6/20.
 * <p/>
 * email: magiwen@126.com.
 */


public class BaseResultParams implements Serializable {

    private static final long serialVersionUID = 1265758314834003579L;
    /**
     * 后端返回的code
     */
    public int code;
    /**
     * 后端返回的msg
     */
    public String msg;
}
