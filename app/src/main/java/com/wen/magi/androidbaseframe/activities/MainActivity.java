package com.wen.magi.androidbaseframe.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.wen.magi.androidbaseframe.R;
import com.wen.magi.androidbaseframe.algorithms;
import com.wen.magi.androidbaseframe.annotations.From;
import com.wen.magi.androidbaseframe.base.BaseActivity;
import com.wen.magi.androidbaseframe.interfaces.home.OneFragment;
import com.wen.magi.androidbaseframe.utils.LogUtils;
import com.wen.magi.androidbaseframe.views.BounceListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.wen.magi.androidbaseframe.algorithms.NODE;
import static com.wen.magi.androidbaseframe.algorithms.binarySearch;
import static com.wen.magi.androidbaseframe.algorithms.create;
import static com.wen.magi.androidbaseframe.algorithms.printNODE;
import static com.wen.magi.androidbaseframe.algorithms.quickSort;
import static com.wen.magi.androidbaseframe.algorithms.reverse;
import static com.wen.magi.androidbaseframe.algorithms.sum;

public class MainActivity extends BaseActivity {

    @From(R.id.main_tv)
    private TextView mainTv;

    @From(R.id.list_view)
    private BounceListView listView;
    private HashMap<String, Objects> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mainTv != null)
            mainTv.setText("wenwenwen");
        mainTv.setOnClickListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new OneFragment()).commit();
//        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getDummyData(30)));
    }

    @Override
    protected void OnClickView(View v) {
        if (v == mainTv) {
            startActivity(DialogTestActivity.class);
        }
    }

    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }

}
