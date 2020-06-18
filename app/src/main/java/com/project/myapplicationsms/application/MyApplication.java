package com.project.myapplicationsms.application;

import android.app.Application;
import android.os.Handler;

import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.network.AppOkHttpClientImpl;
import com.project.myapplicationsms.network.HttpCommon;

public  class MyApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Global.setContext(this);
        Global.setHandler(new Handler());
        HttpCommon.initClient(new AppOkHttpClientImpl());
    }
}
