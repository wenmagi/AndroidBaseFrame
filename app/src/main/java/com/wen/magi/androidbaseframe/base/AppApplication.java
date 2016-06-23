package com.wen.magi.androidbaseframe.base;

import android.app.Application;
import android.content.res.Configuration;

import com.wen.magi.androidbaseframe.managers.AppManager;

/**
 * Created by MVEN on 16/4/28.
 */
public class AppApplication extends Application {
    private static AppApplication appApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        AppManager.initInMainThread(appApplication);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
