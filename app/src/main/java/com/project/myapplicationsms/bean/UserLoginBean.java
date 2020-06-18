package com.project.myapplicationsms.bean;


/**
 * Create by xuqunxing on  2019/3/25
 */
public class UserLoginBean extends BaseBean {

    /**
     * code : 200
     * message : OK
     * authState : 1
     * datas : {"token":"a428ab948bb54add8178cd9aede44843","account":"13900000002","name":"烧烤店","addr":"西洪路110号","contactName":"卢君","mobile":"13900000002","storePic":"http://test.qiniu.dpzaixian.com/app/dpzxzyapp/e029d71e-c818-472b-beb4-a00f44358153","idCardBack":"http://test.qiniu.dpzaixian.com/app/dpzxzyapp/c8481950-269b-48eb-8352-782d3cc46f08","idCardFront":"http://test.qiniu.dpzaixian.com/app/dpzxzyapp/2d00a3c4-28d8-4024-abfe-eb78587285ec","provinceArea":{"id":246651,"name":"福建省"},"cityArea":{"id":246652,"name":"福州市"},"districtArea":{"id":246654,"name":"鼓楼区"},"streetArea":{"id":246655,"name":"鼓东街道"},"customerType":{"id":6,"name":"农贸菜市场"},"customerLevel":{"id":1,"name":"普通会员","logo":"http://qiniu.dpzaixian.com/customer_level/v1.png"}}
     * serverTimeDate : 2019-03-25 19:16:56
     * serverTimeMillisecond : 1553512616687
     * costTime : 0:0:0.22
     * stockThreshold : 20
     */
    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * token : a428ab948bb54add8178cd9aede44843
         * account : 13900000002
         * name : 烧烤店
         * addr : 西洪路110号
         * contactName : 卢君
         * mobile : 13900000002
         * storePic : http://test.qiniu.dpzaixian.com/app/dpzxzyapp/e029d71e-c818-472b-beb4-a00f44358153
         * idCardBack : http://test.qiniu.dpzaixian.com/app/dpzxzyapp/c8481950-269b-48eb-8352-782d3cc46f08
         * idCardFront : http://test.qiniu.dpzaixian.com/app/dpzxzyapp/2d00a3c4-28d8-4024-abfe-eb78587285ec
         * provinceArea : {"id":246651,"name":"福建省"}
         * cityArea : {"id":246652,"name":"福州市"}
         * districtArea : {"id":246654,"name":"鼓楼区"}
         * streetArea : {"id":246655,"name":"鼓东街道"}
         * customerType : {"id":6,"name":"农贸菜市场"}
         * customerLevel : {"id":1,"name":"普通会员","logo":"http://qiniu.dpzaixian.com/customer_level/v1.png"}
         */

        private String token;
        private String account;
        private String name;
        private String addr;
        private String contactName;
        private String mobile;
        private String storePic;
        private String idCardBack;
        private String idCardFront;
        private ProvinceAreaBean provinceArea;
        private CityAreaBean cityArea;
        private DistrictAreaBean districtArea;
        private StreetAreaBean streetArea;
        private CustomerTypeBean customerType;
        private CustomerLevelBean customerLevel;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStorePic() {
            return storePic;
        }

        public void setStorePic(String storePic) {
            this.storePic = storePic;
        }

        public String getIdCardBack() {
            return idCardBack;
        }

        public void setIdCardBack(String idCardBack) {
            this.idCardBack = idCardBack;
        }

        public String getIdCardFront() {
            return idCardFront;
        }

        public void setIdCardFront(String idCardFront) {
            this.idCardFront = idCardFront;
        }

        public ProvinceAreaBean getProvinceArea() {
            return provinceArea;
        }

        public void setProvinceArea(ProvinceAreaBean provinceArea) {
            this.provinceArea = provinceArea;
        }

        public CityAreaBean getCityArea() {
            return cityArea;
        }

        public void setCityArea(CityAreaBean cityArea) {
            this.cityArea = cityArea;
        }

        public DistrictAreaBean getDistrictArea() {
            return districtArea;
        }

        public void setDistrictArea(DistrictAreaBean districtArea) {
            this.districtArea = districtArea;
        }

        public StreetAreaBean getStreetArea() {
            return streetArea;
        }

        public void setStreetArea(StreetAreaBean streetArea) {
            this.streetArea = streetArea;
        }

        public CustomerTypeBean getCustomerType() {
            return customerType;
        }

        public void setCustomerType(CustomerTypeBean customerType) {
            this.customerType = customerType;
        }

        public CustomerLevelBean getCustomerLevel() {
            return customerLevel;
        }

        public void setCustomerLevel(CustomerLevelBean customerLevel) {
            this.customerLevel = customerLevel;
        }

        public static class ProvinceAreaBean {
            /**
             * id : 246651
             * name : 福建省
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CityAreaBean {
            /**
             * id : 246652
             * name : 福州市
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class DistrictAreaBean {
            /**
             * id : 246654
             * name : 鼓楼区
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class StreetAreaBean {
            /**
             * id : 246655
             * name : 鼓东街道
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CustomerTypeBean {
            /**
             * id : 6
             * name : 农贸菜市场
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CustomerLevelBean {
            /**
             * id : 1
             * name : 普通会员
             * logo : http://qiniu.dpzaixian.com/customer_level/v1.png
             */

            private int id;
            private String name;
            private String logo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }
}
