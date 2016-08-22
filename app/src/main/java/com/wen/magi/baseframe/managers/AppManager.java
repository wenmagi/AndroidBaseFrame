package com.wen.magi.baseframe.managers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.LongSparseArray;

import com.wen.magi.baseframe.algorithms;
import com.wen.magi.baseframe.models.AppUser;
import com.wen.magi.baseframe.utils.Constants;
import com.wen.magi.baseframe.utils.IOUtils;
import com.wen.magi.baseframe.utils.LogUtils;
import com.wen.magi.baseframe.utils.SysUtils;

import java.lang.reflect.Field;
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
        MultiDex.install(applicationContext);
//        clearPreRes();
        SysUtils.initialize(context);
        RequestQueueManager.initialize(context);
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

    /**
     * 退出当前用户
     */
    public static void logout() {
        clearPreferenceButKeep();
    }

    private static void clearPreferenceButKeep() {
        ArrayList<String> keepKeys = new ArrayList<>();
        keepKeys.add(Constants.SETTINGS_IS_PUSHMESSAGE_ON);
        keepKeys.add(Constants.SETTINGS_PIC_QUALITY);
        IOUtils.clearPreferencesKeep(keepKeys);
    }

    /**
     * 应用退出调用该方法
     */
    public static void destroy() {
        RequestQueueManager.destroy();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void clearPreRes() {
        Resources resources = applicationContext.getResources();
        try {
            Field field = Resources.class.getDeclaredField("sPreloadedDrawables");
            field.setAccessible(true);
            LongSparseArray<Drawable.ConstantState>[] sPreLoadedDrawables = (LongSparseArray<Drawable.ConstantState>[]) field.get(resources);
            LogUtils.e("wwwwwwww %s", sPreLoadedDrawables.length);
            for (LongSparseArray<Drawable.ConstantState> preLoaded : sPreLoadedDrawables) {

                preLoaded.clear();
            }
        } catch (NoSuchFieldException e) {
            LogUtils.e("清理系统资源  %s", e);
        } catch (IllegalAccessException e) {
            LogUtils.e("清理系统资源sPreLoadedDrawables  %s", e);
        }
    }
}
