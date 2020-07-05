package com.project.myapplicationsms.bean;

import org.litepal.crud.LitePalSupport;

public  class LogBean extends LitePalSupport {

    private  String amount;
    private  String createTime;
    private  String cardNo;
    private  String bankName;
    private  int authSate;
    private  String signKey;

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
}
