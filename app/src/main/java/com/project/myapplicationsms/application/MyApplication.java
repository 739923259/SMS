package com.project.myapplicationsms.application;

import android.app.Application;
import android.os.Handler;

import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.network.AppOkHttpClientImpl;
import com.project.myapplicationsms.network.HttpCommon;
import com.project.myapplicationsms.observe.TraceServiceImpl;
import com.xdandroid.hellodaemon.DaemonEnv;

public  class MyApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Global.setContext(this);
        Global.setHandler(new Handler());
        HttpCommon.initClient(new AppOkHttpClientImpl());
        DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
    }
}
