package com.wen.magi.androidbaseframe.base;

/**
 * Created by MVEN on 16/7/21.
 * <p/>
 * email: magiwen@126.com.
 */


public class BaseEventBusWebEvent {
    public boolean bResult;

    public int nFailedType;

    public static final int FAILED_TYPE_NO_NETWORK = 1;
    public static final int FAILED_TYPE_UNKOWN = 2;
    public static final int FAILED_TYPE_RESPONSE_CODE_WRONG = 3;// code != 0: wrong
    public static final int FAILED_TYPE_RESPONSE_DATA_FORMAT_WRONG = 4;

    public BaseEventBusWebEvent(boolean bResult) {
        this.bResult = bResult;
    }
}
