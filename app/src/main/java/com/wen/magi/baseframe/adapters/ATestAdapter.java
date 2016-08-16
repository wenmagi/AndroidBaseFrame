package com.wen.magi.baseframe.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wen.magi.baseframe.R;
import com.wen.magi.baseframe.base.BaseListAdapter;

import java.util.List;

/**
 * Created by MVEN on 16/6/30.
 * <p/>
 * email: magiwen@126.com.
 */


public class ATestAdapter extends BaseListAdapter<String> {

    public ATestAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected void initItemView(int position, BaseListAdapter<String>.ViewHolder holder) {
//        TextView v = holder.getView(R.id.stub_id);
    }

    @Override
    public int getItemResourse() {
        return 0;
    }

}
