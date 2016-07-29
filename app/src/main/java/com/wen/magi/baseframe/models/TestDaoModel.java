package com.wen.magi.baseframe.models;

/**
 * Created by MVEN on 16/6/18.
 * <p/>
 * email: magiwen@126.com.
 */

import com.wen.magi.baseframe.utils.LangUtils;
import com.wen.magi.baseframe.utils.LogUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table CARD_ORDER.
 */
public class TestDaoModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2610116364656540881L;
    // "id": 1,
    // "status": 0, // 状态: 0-未使用或者过期, 看时间 1-已使用 2-已取消
    // "order_code": "8937337192", // 预约码: 如果是null, 则提示运营人员正在处理
    // "course": {
    // "id": "5710458dc4ec4a78b1162420006d872d_1431615600",
    // "name": "提前约"
    // "type": 1,
    // "start": "2015-05-14T23:00:00",
    // "end": "2015-05-14T23:30:00",
    // },
    // "gym": {
    // "id": 160,
    // "name": "林业大学团购"
    // "location": "北京市海淀区北京林业大学",
    // }
    private static final String KEY_ORDER_ID = "id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_ORDER_CODE = "order_code";

    /******
     * class info
     *****/
    private static final String KEY_CLASS_ID = "id";
    private static final String KEY_CLASS_TYPE = "type";
    private static final String KEY_CLASS_NAME = "name";
    private static final String KEY_CLASS_NOTICE = "notice";
    private static final String KEY_START_TIME = "start";
    private static final String KEY_END_TIME = "end";
    private static final String KEY_MERCHANT_GRUOP_NAME = "co_site";
    /******
     * merchant info
     *****/
    private static final String KEY_MERCHANT_ID = "id";
    private static final String KEY_MERCHANT_NAME = "name";
    private static final String KEY_MERCHANT_LOCATION = "location";
    private static final String KEY_MERCHANT_CO_TYPE = "co_type";

    private static final String KEY_UPDATE_TIME = "update_time";

    private static final String KEY_XIAO_XIONG_QUAN = "xxquan";
    private static final String KEY_XIAO_XIONG_QUAN_INVITE = "invite";
    private static final String KEY_XIAO_XIONG_QUAN_CHECKIN = "checkin";

    private Long id;
    private long orderID;
    private Long merchantID;
    private Integer classType;
    private Integer coType;//"co_type": 0 or 1 or 2, 0-团购, 1-直接, 2-小泥人

    public static final int CO_TYPE_GROUP = 0;
    public static final int CO_TYPE_DIRECT = 1;
    public static final int CO_TYPE_XIAONIRENG = 2;

    // private Integer orderStatus;
    private String orderCode;
    private String classID;
    private String className;
    private String merchantName;
    private String groupPurchaseName;
    private String merchantLocation;
    private java.util.Date updateTime;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private String notice;

    private int inviteMsgID = 0;// >0 表示已邀约过
    private int punchMsgID = 0;// >0 表示已打卡过


    /**
     * orderType :  0-xxCard  1-MerchantCard  2-specialCard
     */
    private Integer orderType = 0;

    public enum OrderStatus {
        OrderStatusUnused, OrderStatusFinished, OrderStatusCancelled
    }

    private OrderStatus orderStatus = OrderStatus.OrderStatusUnused; // 状态: 0-未使用或者过期, 看时间 1-已使用 2-已取消


    public TestDaoModel(Long orderId) {
        this.orderID = orderId;
        this.updateTime = new Date();
    }

    public TestDaoModel(Long id, long orderID, Long merchantID, Integer classType, Integer coType,
                        Integer orderStatus, String orderCode, String classID, String className, String merchantName,
                        String groupPurchaseName, String merchantLocation, java.util.Date updateTime,
                        java.util.Date startTime, java.util.Date endTime, String notice, int inviteMsgID, int punchMsgID, int orderType) {
        this.id = id;
        this.orderID = orderID;
        this.merchantID = merchantID;
        this.classType = classType;
        this.coType = coType;
        this.orderCode = orderCode;
        this.classID = classID;
        this.className = className;
        this.merchantName = merchantName;
        this.groupPurchaseName = groupPurchaseName;
        this.merchantLocation = merchantLocation;
        this.updateTime = updateTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setNotice(notice);
        this.inviteMsgID = inviteMsgID;
        this.punchMsgID = punchMsgID;
        this.orderType = Integer.valueOf(orderType);
    }

    public String toString() {
        JSONObject j = toJson();
        return j == null ? super.toString() : j.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof TestDaoModel) {
            if (this.classID.equals(((TestDaoModel) o).getClassID())
                    && this.orderID == ((TestDaoModel) o).getOrderID()) {
                return true;
            }
        }
        return false;
    }
    public JSONObject toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put(KEY_ORDER_ID, this.orderID);
            json.put(KEY_MERCHANT_GRUOP_NAME, this.groupPurchaseName);
            json.put("class_" + KEY_CLASS_ID, this.classID);
            json.put("class_" + KEY_CLASS_NAME, this.className);
            json.put("class_" + KEY_CLASS_TYPE, this.classType);
            json.put(KEY_ORDER_CODE, this.orderCode);
            json.put("class_" + KEY_START_TIME, this.startTime);
            json.put("class_" + KEY_END_TIME, this.endTime);
            json.put("class_" + KEY_CLASS_NOTICE, this.notice);
            json.put(KEY_STATUS, this.orderStatus.ordinal());

            json.put("merchant_" + KEY_MERCHANT_ID, this.merchantID);
            json.put("merchant_" + KEY_MERCHANT_NAME, this.merchantName);
            json.put("merchant_" + KEY_MERCHANT_LOCATION, this.merchantLocation);
            json.put("merchant_" + KEY_MERCHANT_CO_TYPE, this.coType);

            json.put("xxq_" + KEY_XIAO_XIONG_QUAN_INVITE, this.inviteMsgID);
            json.put("xxq_" + KEY_XIAO_XIONG_QUAN_CHECKIN, this.punchMsgID);


            json.put(KEY_UPDATE_TIME, this.updateTime);

            return json;
        } catch (Exception e) {
            LogUtils.w(e, " parse order serial error");

        }
        return null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public Long getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public Integer getClassType() {
        return classType;
    }

    public void setClassType(Integer classType) {
        this.classType = classType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public Integer getCoType() {
        return coType;
    }

    public void setCoType(Integer coType) {
        this.coType = coType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public int getOrderStatusIndex() {
        return orderStatus.ordinal();
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getShowOrderCode() {
        if (coType == CO_TYPE_GROUP) {
            if (LangUtils.isNotEmpty(groupPurchaseName)) {
                return orderCode + "(" + groupPurchaseName + ")";
            } else {
                return "";
            }
        } else {
            return orderCode;
        }
    }

    public boolean isExpired() {
        return endTime == null ? true : endTime.getTime() < System.currentTimeMillis();
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGroupPurchaseName() {
        return groupPurchaseName;
    }

    public void setGroupPurchaseName(String groupPurchaseName) {
        this.groupPurchaseName = groupPurchaseName;
    }

    public String getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public long getEndTimeStamp() {
        return endTime == null ? 0 : endTime.getTime();
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isAlreadyInvite() {
        return inviteMsgID > 0 ? true : false;
    }

    public int getInviteMsgID() {
        return inviteMsgID;
    }

    public void setInviteMsgID(int inviteMsgID) {
        this.inviteMsgID = inviteMsgID;
    }


    public boolean isAlreadyPunch() {
        return punchMsgID > 0 ? true : false;
    }

    public int getPunchMsgID() {
        return punchMsgID;
    }

    public void setPunchMsgID(int punchMsgID) {
        this.punchMsgID = punchMsgID;
    }

}
