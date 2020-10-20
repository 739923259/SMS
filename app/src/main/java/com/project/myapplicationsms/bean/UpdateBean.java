package com.project.myapplicationsms.bean;

import java.io.Serializable;

public class UpdateBean extends BaseBean{
    private AndroidBean android;
    public AndroidBean getAndroid() {
        return android;
    }
    public void setAndroid(AndroidBean android) {
        this.android = android;
    }
    public static class AndroidBean implements Serializable {
        private boolean isShow;
        public boolean isIsShow() {
            return isShow;
        }
        public void setIsShow(boolean isShow) {
            this.isShow = isShow;
        }
    }
}
