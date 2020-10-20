package com.project.myapplicationsms.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Create by xuqunxing on  2019/3/25
 */
public class AuthServerBean extends BaseBean {



    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean  implements Serializable {
        private List<UnPayCardsBean> unPayCards;

        public List<UnPayCardsBean> getUnPayCards() {
            return unPayCards;
        }

        public void setUnPayCards(List<UnPayCardsBean> unPayCards) {
            this.unPayCards = unPayCards;
        }

        public static class UnPayCardsBean implements Serializable {
            /**
             * bankName : 招商
             * time : 14:02
             * userName : 小明
             * cardNo : 6689
             */

            private String bankName;
            private String time;
            private String userName;
            private String cardNo;

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }
        }
    }
}
