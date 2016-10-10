package com.wen.magi.baseframe.rholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author zhangzhaowen @ Zhihu Inc.
 * @since 10-10-2016
 */

public class RViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected T data;

    public RViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {

    }
}
