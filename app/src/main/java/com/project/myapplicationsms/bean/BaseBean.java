package com.project.myapplicationsms.bean;

import java.io.Serializable;

/**
 * Create by xuqunxing on  2019/5/9
 */
public class BaseBean implements Serializable {

    private String code;
    private String message;

    private String serverTimeDate;
    private long serverTimeMillisecond;
    private String costTime;

    private int resource;
    private long shopId;

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServerTimeDate() {
        return serverTimeDate;
    }

    public void setServerTimeDate(String serverTimeDate) {
        this.serverTimeDate = serverTimeDate;
    }

    public long getServerTimeMillisecond() {
        return serverTimeMillisecond;
    }

    public void setServerTimeMillisecond(long serverTimeMillisecond) {
        this.serverTimeMillisecond = serverTimeMillisecond;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }
}
