package com.project.myapplicationsms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Create by xuqunxing on  2019/3/14
 */
public class BaseConfigPreferences {
    public static final String NAME = "smssp";
    private static SharedPreferences baseSP;
    private static BaseConfigPreferences baseConfig;

    protected BaseConfigPreferences(Context context) {
        baseSP = context.getSharedPreferences(NAME, 4);
    }

    public static BaseConfigPreferences getInstance(Context context) {
        if (null == baseConfig) {
            baseConfig = new BaseConfigPreferences(context);
        }

        return baseConfig;
    }

    public SharedPreferences getBaseSP() {
        return baseSP;
    }

    //------------------------------------------------------
    public static final String LOGIN_SIGN = "login_account";
    public static final String cloud_baseurl = "cloud_baseurl";


    //------------------------------------------------------



    public void setLoginSigin(String count) {
        baseSP.edit().putString(LOGIN_SIGN, count).commit();
    }

    public void setBaseUrl(String url) {
        baseSP.edit().putString(cloud_baseurl, url).commit();
    }



    public String getLoginSigin() {
        return baseSP.getString(LOGIN_SIGN, (String) null);
    }
    public String getCloud_baseurl() {
        return baseSP.getString(cloud_baseurl, (String) null);
    }


}
