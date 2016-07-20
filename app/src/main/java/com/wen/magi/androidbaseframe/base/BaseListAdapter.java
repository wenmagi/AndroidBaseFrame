package com.wen.magi.androidbaseframe.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MVEN on 16/6/17.
 * <p/>
 * email: magiwen@126.com.
 */


public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> datas;

    public BaseListAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = new ArrayList<>();
        datas.addAll(datas);
    }

    public void setDatas(List<T> datas) {
        this.datas.clear();
        if (datas != null) {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (datas == null)
            return 0;
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        if (datas == null)
            return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    /**
     * 局部刷新可见区域的数据
     *
     * @param view 需要刷新的View
     * @param itemIndex 该view的位置
     */
    protected void updateViews(View view, int itemIndex) {
        if (view == null)
            return;

        ViewHolder holder = (ViewHolder) view.getTag();
        findHolderViewAndRefresh(holder, itemIndex);
    }

    /**
     * 寻找到holder持有view的内容，并进行刷新
     *
     * @param holder 目标holder
     * @param itemIndex 数据datas的位置
     */
    protected void findHolderViewAndRefresh(ViewHolder holder, int itemIndex) {
    }

    class ViewHolder {
    }
}
