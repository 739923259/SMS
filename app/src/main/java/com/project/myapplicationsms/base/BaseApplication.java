package com.project.myapplicationsms.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;


public class BaseApplication extends Application {

    private static BaseApplication mAppContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //因为引用的包过多，实现多包问题

    }

    @Nullable
    public static Application getAppContext() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

}
