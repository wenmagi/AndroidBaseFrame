package com.wen.magi.androidbaseframe.managers;

import android.content.Context;

import com.wen.magi.androidbaseframe.algorithms;
import com.wen.magi.androidbaseframe.base.AppApplication;
import com.wen.magi.androidbaseframe.models.AppUser;
import com.wen.magi.androidbaseframe.utils.Constants;
import com.wen.magi.androidbaseframe.utils.IOUtils;
import com.wen.magi.androidbaseframe.utils.SysUtils;

import java.util.ArrayList;

/**
 * Created by MVEN on 16/4/28.
 */
public class AppManager {

    private static Context applicationContext;

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void initInMainThread(Context context) {
        applicationContext = context;
        SysUtils.initialize(context);
        algorithms.getInstance();
    }

    /**
     * 用户登录后从后端返回的AppUser
     *
     * @return
     */
    public static AppUser getAppUser() {
        return new AppUser();
    }

    public static void logout() {
        clearPreferenceButKeep();
    }

    private static void clearPreferenceButKeep() {
        ArrayList<String> keepKeys = new ArrayList<>();
        keepKeys.add(Constants.SETTINGS_IS_PUSHMESSAGE_ON);
        keepKeys.add(Constants.SETTINGS_PIC_QUALITY);
        IOUtils.clearPreferencesKeep(keepKeys);
    }
}
