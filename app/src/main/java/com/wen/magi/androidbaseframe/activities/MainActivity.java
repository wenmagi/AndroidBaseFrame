package com.wen.magi.androidbaseframe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wen.magi.androidbaseframe.R;
import com.wen.magi.androidbaseframe.annotations.From;
import com.wen.magi.androidbaseframe.base.BaseActivity;
import com.wen.magi.androidbaseframe.utils.LogUtils;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    @From(R.id.main_tv)
    private TextView mainTv;

    private HashMap<String, Objects> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mainTv != null)
            mainTv.setText("wenwenwen");
        mainTv.setOnClickListener(this);
        int a = 2333;
        show2Or3(a);
        LogUtils.e("wwwwwwwwwwww %s %s", a,"abc".equals("abc"));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mainTv) {
            startActivity(DialogTestActivity.class);
        }
    }

    private int show2Or3(int index) {
        int i = 0;
        index = 2222;
        for (int j = 1; i < index; j++) {
            if (j % 2 == 0 || j % 3 == 0) {
                i++;
            }
            if (i == 2333) {
                return j;
            }
        }
        return -1;
    }
}
