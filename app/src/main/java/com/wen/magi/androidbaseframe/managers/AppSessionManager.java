package com.wen.magi.androidbaseframe.managers;

/**
 * Created by MVEN on 16/6/19.
 * <p/>
 * email: magiwen@126.com.
 */


public class AppSessionManager {
    public enum SessionMode {
        OnLine, OffLine
    }

    public enum SessionStatus {
        Login, Logout
    }

    private static AppSessionManager mManager;
    private SessionMode sessionMode;
    private SessionStatus sessionStatus;

    public static AppSessionManager getSessionManager() {

        if (mManager == null) {
            mManager = new AppSessionManager();
        }

        return mManager;
    }

    public void setSessionMode(SessionMode sessionMode) {
        this.sessionMode = sessionMode;
    }

    public SessionMode getSessionMode() {
        return sessionMode;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public boolean isLogin() {
        return sessionStatus == SessionStatus.Login;
    }

    public boolean isOnline() {
        return sessionMode == SessionMode.OnLine;
    }
}
