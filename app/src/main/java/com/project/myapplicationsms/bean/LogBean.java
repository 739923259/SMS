package com.project.myapplicationsms.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public  class LogBean extends LitePalSupport implements Serializable {

    private  String amount;
    private  String createTime;
    private  String cardNo;
    private  String bankName;
    private  int authSate;
    private  String signKey;
    private  String url;
    private  String smsSender;
    private  String userName;
    private String fomartime;

    public String getFomartime() {
        return fomartime;
    }

    public void setFomartime(String fomartime) {
        this.fomartime = fomartime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSmsSender() {
        return smsSender;
    }

    public void setSmsSender(String smsSender) {
        this.smsSender = smsSender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public int getAuthSate() {
        return authSate;
    }

    public void setAuthSate(int authSate) {
        this.authSate = authSate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "LogBean{" +
                "amount='" + amount + '\'' +
                ", createTime='" + createTime + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", authSate=" + authSate +
                ", signKey='" + signKey + '\'' +
                '}';
    }
}
