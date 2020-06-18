package com.project.myapplicationsms.bean;

import java.io.Serializable;

/**
 * Create by xuqunxing on  2019/8/20
 */
public class QiniuSettingBean extends BaseBean {


    /**
     * data : {"domain":"http://test.qiniu.dpzaixian.com/","token":"iseQipJlqKJ2-TuVALYcA-r7qq-tEwhSQB5s7Dpt:Z2lsz-FAt_76XNDpKv4L-K2d-Kc=:eyJzY29wZSI6ImRwengiLCJmc2l6ZUxpbWl0Ijo3MzExMzYwLCJkZWFkbGluZSI6MjI4NjI5MjE1Nn0="}
     */

    private DataBean datas;

    public DataBean getData() {
        return datas;
    }

    public void setData(DataBean data) {
        this.datas = data;
    }

    public static class DataBean implements Serializable {
        /**
         * domain : http://test.qiniu.dpzaixian.com/
         * token : iseQipJlqKJ2-TuVALYcA-r7qq-tEwhSQB5s7Dpt:Z2lsz-FAt_76XNDpKv4L-K2d-Kc=:eyJzY29wZSI6ImRwengiLCJmc2l6ZUxpbWl0Ijo3MzExMzYwLCJkZWFkbGluZSI6MjI4NjI5MjE1Nn0=
         */

        private String domain;
        private String token;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
