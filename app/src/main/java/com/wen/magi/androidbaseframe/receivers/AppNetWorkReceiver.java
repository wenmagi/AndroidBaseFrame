package com.wen.magi.androidbaseframe.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wen.magi.androidbaseframe.managers.AppManager;
import com.wen.magi.androidbaseframe.managers.AppSessionManager;
import com.wen.magi.androidbaseframe.utils.LogUtils;
import com.wen.magi.androidbaseframe.utils.SysUtils;
import com.wen.magi.androidbaseframe.utils.WebUtils;

/**
 * Created by MVEN on 16/7/22.
 * <p/>
 * email: magiwen@126.com.
 */


public class AppNetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (AppSessionManager.getSessionManager() == null)
            return;
        boolean isNetWorkConnected = WebUtils.isNetworkConnected(AppManager.getApplicationContext());
        LogUtils.e("wwwwwwww test git for cancel change before(after) added");
        int netWorkType = SysUtils.getNetWorkType();
    }
}
