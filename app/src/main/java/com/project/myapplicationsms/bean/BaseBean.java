package com.project.myapplicationsms.bean;

import java.io.Serializable;

/**
 * Create by xuqunxing on  2019/5/9
 */
public class BaseBean implements Serializable {
    private  String respMessage;
    private  int respCode;

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }
}
