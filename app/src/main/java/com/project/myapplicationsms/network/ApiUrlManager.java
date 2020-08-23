package com.project.myapplicationsms.network;
public class ApiUrlManager {
    public static String BaseUrl = "";//测试地址
  //  public static String BaseUrl = "http://111.230.200.83:8081/paygateway";//测试地址
    public static String preUrl = "/paygateway";//测试地址
    public static String API_LOGIN_URL =preUrl+"/api/v5/customer/login";
    public static String API_CRM_QINIU_SETTING =preUrl+"/api/v1/system/qiniu";//获取七牛云配置
    public static String API_INNER_SORSI =preUrl+"/inner/sorsi";//心跳接口
    public static String API_BANK2CARD =preUrl+"/pay/callback/bank2card";//短信上传



}
