package com.wen.magi.baseframe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wen.magi.baseframe.rholders.RViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangzhaowen @ Zhihu Inc.
 * @since 10-10-2016
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RViewHolder> {

    public interface OnRecyclerItemClickListener<T> {
        void onClick(View view, RViewHolder<T> viewHolder);
    }

    private OnRecyclerItemClickListener mItemClickListener;

    private List<RecyclerItem> mItems = new ArrayList<>();

    public BaseRecyclerViewAdapter() {
        super();

    }

    public BaseRecyclerViewAdapter(OnRecyclerItemClickListener itemOnClicklistener) {
        this();

        mItemClickListener = itemOnClicklistener;
    }

    public void setItemOnClickListener(OnRecyclerItemClickListener itemOnClicklistener) {
        mItemClickListener = itemOnClicklistener;
    }

    /**
     * 替换position位置的targetItem
     *
     * @param position     pos
     * @param recyclerItem targetItem
     */
    public void setRecyclerItem(final int position, final RecyclerItem recyclerItem) {
        mItems.set(position, recyclerItem);
        notifyDataSetChanged();
    }

    /**
     * 在position位置添加targetItem
     *
     * @param position     pos
     * @param recyclerItem targetItem
     */
    public void addRecyclerItem(final int position, final RecyclerItem recyclerItem) {
        mItems.add(position, recyclerItem);
        notifyDataSetChanged();
    }

    /**
     * 批量增加item，范围刷新界面，百万级别以下适合使用ArrayList.addAll()|以上适合用Collections.addAll()  add一个单独的List适合使用List.addAll()
     *
     * @param recyclerItems item
     * @see <a href="http://blog.csdn.net/liyuming0000/article/details/49488607"></a>
     */
    public void addRecyclerItems(final RecyclerItem... recyclerItems) {
        final int oldItemCount = getItemCount();
        Collections.addAll(mItems, recyclerItems);
        notifyItemRangeChanged(oldItemCount, recyclerItems.length);
    }

    /**
     * 在已有Item的基础上，批量增加item
     *
     * @param recyclerItemList list
     */
    public void addRecyclerItemList(final List<RecyclerItem> recyclerItemList) {
        final int oldItemCount = getItemCount();
        mItems.addAll(recyclerItemList);
        notifyItemRangeChanged(oldItemCount, recyclerItemList.size());
    }

    /**
     * 在已有Item的基础上，批量增加item
     *
     * @param position         pos
     * @param recyclerItemList list
     */
    public void addRecyclerItemList(final int position, final List<RecyclerItem> recyclerItemList) {
        mItems.addAll(recyclerItemList);
        notifyItemRangeChanged(position, recyclerItemList.size());
    }

    /**
     * 获取position位置的Item
     *
     * @param position pos
     * @return item
     */
    public RecyclerItem getRecyclerItem(final int position) {
        return mItems.get(position);
    }

    /**
     * @return items
     */
    public List<RecyclerItem> getRecyclerItems() {
        return mItems;
    }

    /**
     * item位置刷新
     *
     * @param recyclerItem item
     * @param position     pos
     */
    public void changeRecyclerItem(final RecyclerItem recyclerItem, final int position) {
        mItems.set(position, recyclerItem);
        notifyItemChanged(position);
    }

    /**
     * 移除某一项
     *
     * @param position pos
     */
    public void removeRecyclerItem(final int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void removeRecyclerItem(final RecyclerItem recyclerItem) {
        final int index = mItems.indexOf(recyclerItem);
        if (index == -1)
            return;

        removeRecyclerItem(index);
    }

    public void removeData(Object data) {
        for (RecyclerItem item :
                mItems) {
            if (item.getData() == data) {
                removeRecyclerItem(item);
                break;
            }
        }
    }

    /**
     * 从某个位置，移除count个数的item
     *
     * @param position pos
     * @param count    个数
     */
    public void removeRecyclerItem(final int position, final int count) {
        mItems.subList(position, position + count).clear();
        notifyItemRangeChanged(position, count);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static class RecyclerItem<T> {

        private final int mViewType;

        private T mDdata;

        public RecyclerItem(final int viewType, final T data) {
            mDdata = data;
            mViewType = viewType;
        }

        public int getViewType() {
            return mViewType;
        }

        public T getData() {
            return mDdata;
        }

        public void setData(final T data) {
            mDdata = data;
        }
    }
}

