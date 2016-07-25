package com.wen.magi.androidbaseframe.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wen.magi.androidbaseframe.managers.AppManager;
import com.wen.magi.androidbaseframe.managers.AppSessionManager;
import com.wen.magi.androidbaseframe.utils.SysUtils;
import com.wen.magi.androidbaseframe.utils.WebUtils;

/**
 * Created by MVEN on 16/7/22.
 * <p/>
 * email: magiwen@126.com.
 */

/**
 * BroadcastReceiver：
 * <p/>
 * 网络状况的实时监听（onLine：offLine），网络类型的实时监听（NetworkType）
 */
public class AppNetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppSessionManager sessionManager = AppSessionManager.getSessionManager();
        if (sessionManager == null)
            return;

        boolean isNetWorkConnected = WebUtils.isNetworkConnected(AppManager.getApplicationContext());
        AppSessionManager.NetWorkType netWorkType = SysUtils.getNetWorkType();

        if (netWorkType != sessionManager.getNetWorkType()) {
            sessionManager.setNetWorkType(netWorkType);
        }

        if (isNetWorkConnected != sessionManager.isOnline()) {
            sessionManager.setSessionMode(isNetWorkConnected ? AppSessionManager.SessionMode.OnLine : AppSessionManager.SessionMode.OffLine);
        }
    }
}
