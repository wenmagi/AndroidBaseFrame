package com.wen.magi.baseframe.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.wen.magi.baseframe.R;
import com.wen.magi.baseframe.annotations.From;
import com.wen.magi.baseframe.base.BaseActivity;
<<<<<<< HEAD
import com.wen.magi.baseframe.base.net.BaseResultParams;
import com.wen.magi.baseframe.base.net.EService;
import com.wen.magi.baseframe.models.net.request.ATestRequestParams;
import com.wen.magi.baseframe.models.net.result.ATestResultParams;
<<<<<<< HEAD
=======
import com.wen.magi.baseframe.utils.LogUtils;
>>>>>>> f68154aa475bb1d3c917048732c4d3418cb9ff8e
=======
import com.wen.magi.baseframe.utils.LogUtils;
>>>>>>> dev
import com.wen.magi.baseframe.utils.SysUtils;
import com.wen.magi.baseframe.web.UrlRequest;
import com.wen.magi.baseframe.web.WebActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;
import static com.wen.magi.baseframe.utils.Constants.ACTIVITY_WEB_KEY_INTENT_URL;

public class MainActivity extends BaseActivity {

    @From(R.id.main_tv)
    private TextView mainTv;

    @From(R.id.main_tv1)
    private TextView mainTv1;

    @From(R.id.stub_id1)
    private ViewStub viewStub;

    private View linearLayout;

    int i = 0;
    private HashMap<String, Objects> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTv.setOnClickListener(this);
        mainTv1.setOnClickListener(this);
<<<<<<< HEAD
<<<<<<< HEAD

        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();

        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();

        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        startRequest();
        finish();
=======
        double a = 0.03;
>>>>>>> dev
    }

    private void startRequest() {
        ATestRequestParams request = new ATestRequestParams();
        request.gymID = 12;
        request.limit = 10;
        request.nextOffset = i++;
        startRequest(EService.TestRequest, request);
=======
        LogUtils.e("wwwwwwwwweeeee");
>>>>>>> f68154aa475bb1d3c917048732c4d3418cb9ff8e
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void OnClickView(View v) {
        if (v == mainTv) {
            Intent intent = new Intent(this, DialogTestActivity.class);
            if (SysUtils.nowSDKINTBigger(21)) {
                startActivity(intent,
                        makeSceneTransitionAnimation(MainActivity.this, mainTv, "shareNames").toBundle());
            } else
                startActivity(intent);

        } else if (v == mainTv1) {
            if (linearLayout != null)
                return;
            linearLayout = viewStub.inflate();
            linearLayout.setBackgroundResource(R.color.red_btn_bg_color);
            linearLayout.setOnClickListener(this);
            viewStub.setBackgroundResource(R.color.blue_btn_bg_color);
        } else if (v == linearLayout) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(ACTIVITY_WEB_KEY_INTENT_URL, "https://www.baidu.com");
            startActivity(intent);
        }
    }

    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }

    @Override
    protected void onResponseError(UrlRequest request, int code, String message) {
        super.onResponseError(request, code, message);
    }

    @Override
    protected void onNetError(UrlRequest request, int statusCode) {
        super.onNetError(request, statusCode);
    }

    @Override
    protected void onResponseSuccess(UrlRequest request, BaseResultParams data) {
        super.onResponseSuccess(request, data);
        ATestResultParams resultParams = (ATestResultParams) data;
    }

}
