package com.wen.magi.androidbaseframe.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.wen.magi.androidbaseframe.base.BaseActivity;

/**
 * Created by MVEN on 16/7/5.
 * <p/>
 * email: magiwen@126.com.
 */


public class WebActivity extends BaseActivity {
    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void OnClickView(View v) {

    }
}
