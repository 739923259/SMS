package com.project.myapplicationsms.intercepter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.network.ServerResultInterceptor;


/**
 * 网络请求返回码统一监听类
 * Create by xuqunxing on  2019/4/2
 */
public class NetConnectionIntercepter extends ServerResultInterceptor {
    @Override
    public void onIntercept(String message, int code) {
        Log.e("======", "======NetIntercepter-code:" + code);
        if (code == 10002) {//需要重新登陆
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
