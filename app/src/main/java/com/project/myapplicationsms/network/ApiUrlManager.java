package com.project.myapplicationsms.network;
public class ApiUrlManager {
    public static String BaseUrl = "http://118.25.45.185:8080/paygateway";//测试地址
    public static String API_LOGIN_URL = BaseUrl + "/api/v5/customer/login";
    public static String API_CRM_QINIU_SETTING = BaseUrl + "/api/v1/system/qiniu";//获取七牛云配置
    public static String API_INNER_SORSI = BaseUrl + "/inner/sorsi";//心跳接口



}
