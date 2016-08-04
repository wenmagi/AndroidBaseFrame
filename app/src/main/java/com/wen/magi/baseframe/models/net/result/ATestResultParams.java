package com.wen.magi.baseframe.models.net.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.wen.magi.baseframe.base.net.BaseResultParams;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MVEN on 16/8/4.
 * <p/>
 * email: magiwen@126.com.
 */


public class ATestResultParams extends BaseResultParams {
    private static final long serialVersionUID = -5894744583863771986L;

    @JSONField(name = "time_left")
    public long timeLeft;

    @JSONField(name = "has_more")
    public boolean hasMore;

    @JSONField(name = "next_offset")
    public int nextPage;

    @JSONField(name = "free_shower_time")
    public long freeTime;

    @JSONField(name = "unit_price")
    public long unitPrice;

    @JSONField(name = "history")
    public ArrayList<ShowerHistory> historyArrayList;

    /**
     * 内部类需要实现Serializable
     */
    public class ShowerHistory implements Serializable {

        private static final long serialVersionUID = -6679703831346839313L;

        @JSONField(name = "desc")
        public String desc;

        @JSONField(name = "time")
        public String historyTime;
    }

    @Override
    public String toString() {
        return "timeLeft" + timeLeft + "hasMore" + hasMore + nextPage + "freetime" + freeTime + "unitPrice" + unitPrice + "size" + historyArrayList.size();
    }
}
