package com.wen.magi.baseframe.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wen.magi.baseframe.base.BaseActivity;
import com.wen.magi.baseframe.utils.Constants;

/**
 * Created by MVEN on 16/8/3.
 * <p/>
 * email: magiwen@126.com.
 */

/**
 * 仅仅用来展示的页面，无任何逻辑
 * <p/>
 * 防止多个无用activity的创建
 * <p/>
 * 传入{@link com.wen.magi.baseframe.utils.Constants} BUNDLE_JUMP_TO_BASE_JUST_SHOW_ACTIVITY_INFLATE_ID
 */

public class JustShowActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            finish();

        int inflateID = bundle.getInt(Constants.BUNDLE_JUMP_TO_BASE_JUST_SHOW_ACTIVITY_INFLATE_ID);
        setContentView(inflateID);
    }

    @Override
    protected void OnClickView(View v) {

    }
}
