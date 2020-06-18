package com.project.myapplicationsms.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.project.myapplicationsms.base.BaseConfigPreferences;
import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.bean.QiniuSettingBean;
import com.project.myapplicationsms.bean.UserLoginBean;
import com.project.myapplicationsms.intercepter.NetConnectionIntercepter;
import com.project.myapplicationsms.network.ApiUrlManager;
import com.project.myapplicationsms.network.HttpCommon;
import com.project.myapplicationsms.network.HttpRequestParam;
import com.project.myapplicationsms.network.ServerResult;
import com.project.myapplicationsms.network.ServerResultHeader;
import com.project.myapplicationsms.utils.NetworkUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

//网络数据请求工具类
public class NetApiUtil {

    //--------------------------------GET 方法部分↓-----------------------------------------------------------
    //回访登记 详情
    public static final ServerResult<Object> getVisitRecordDetail(int id) {
        if (!NetworkUtils.isNetworkConnected(Global.getApplicationContext())) {
            return null;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        String userToken = BaseConfigPreferences.getInstance(Global.getContext()).getUserToken();
        paramsMap.put("Authorization", userToken);
        HttpRequestParam.addCommmonGetRequestValue(Global.getApplicationContext(), paramsMap);
        String url = ApiUrlManager.API_LOGIN_URL + "?id=" + id;
        HttpCommon httpCommon = new HttpCommon(url, new NetConnectionIntercepter());
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultGet(paramsMap, null);
        ServerResult<Object> resTagList = new ServerResult<>();
        if (csResult != null) {
            String responseStr = csResult.getResponseJson();
            resTagList.setCsResult(csResult);
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                  //  VisitRecordDetailBean messageUnReadBean = new Gson().fromJson(responseStr, VisitRecordDetailBean.class);
                  //  resTagList.setResultBean(messageUnReadBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }
//--------------------------------GET 方法部分↑-----------------------------------------------------------



    public static final ServerResult<UserLoginBean> postLoginAccount(String account, String password, String imei, String code1) {
        if (!NetworkUtils.isNetworkConnected(Global.getApplicationContext())) {
            // MessageUtils.show(Global.getApplicationContext(), "网络异常，请稍后重试");
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("account", account);
        linkedHashMap.put("password", password);
        linkedHashMap.put("imei", imei);
        if (!TextUtils.isEmpty(code1)) {
            linkedHashMap.put("code", code1);
        }
        String httpStr = HttpRequestParam.parsePostData(linkedHashMap);
        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonPostRequestValue(Global.getApplicationContext(), paramsMap);
        HttpCommon httpCommon = new HttpCommon(ApiUrlManager.API_LOGIN_URL, null);
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, httpStr);
        ServerResult<UserLoginBean> resTagList = new ServerResult<UserLoginBean>();
        if (csResult != null) {
            String responseStr = csResult.getResponseJson();
            resTagList.setCsResult(csResult);
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    UserLoginBean userLogin = new Gson().fromJson(responseStr, UserLoginBean.class);
                    resTagList.itemList.add(userLogin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }


    //获取七牛配置
    public static final ServerResult<QiniuSettingBean> getQiniuSetting() {
        if (!NetworkUtils.isNetworkConnected(Global.getApplicationContext())) {
            return null;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonGetRequestValue(Global.getApplicationContext(), paramsMap);
        String url = ApiUrlManager.API_CRM_QINIU_SETTING;
        HttpCommon httpCommon = new HttpCommon(url, new NetConnectionIntercepter());
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultGet(paramsMap, null);
        ServerResult<QiniuSettingBean> resTagList = new ServerResult<>();
        if (csResult != null) {
            String responseStr = csResult.getResponseJson();
            resTagList.setCsResult(csResult);
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    QiniuSettingBean messageUnReadBean = new Gson().fromJson(responseStr, QiniuSettingBean.class);
                    resTagList.setResultBean(messageUnReadBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }


   /* public static final ServerResult<ShopListBean> postShopDataList(String latitue, String longitude, int crawler) {
        if (!NetworkUtils.isNetworkConnected(Global.getApplicationContext())) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("location", longitude + "," + latitue);
        linkedHashMap.put("crawler", crawler + "");
        String httpStr = HttpRequestParam.parsePostData(linkedHashMap);
        HashMap<String, String> paramsMap = new HashMap<>();
        String userToken = BaseConfigPreferences.getInstance(Global.getContext()).getUserToken();
        paramsMap.put("Authorization", userToken);
        HttpRequestParam.addCommmonPostRequestValue(Global.getApplicationContext(), paramsMap);
        HttpCommon httpCommon = new HttpCommon(ApiUrlManager.API_CRM_FILTER_SHOP_LIST, new NetConnectionIntercepter());
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, httpStr);
        ServerResult<ShopListBean> resTagList = new ServerResult<>();
        if (csResult != null) {
            String responseStr = csResult.getResponseJson();
            resTagList.setCsResult(csResult);
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    ShopListBean userLogin = new Gson().fromJson(responseStr, ShopListBean.class);
                    resTagList.setResultBean(userLogin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }*/



}
