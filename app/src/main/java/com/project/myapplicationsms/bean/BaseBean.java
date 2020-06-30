package com.project.myapplicationsms.bean;

import java.io.Serializable;

/**
 * Create by xuqunxing on  2019/5/9
 */
public class BaseBean implements Serializable {
    private  String respMessage;
    private  String respCode;

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
}
