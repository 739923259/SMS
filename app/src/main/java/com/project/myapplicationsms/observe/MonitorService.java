package com.project.myapplicationsms.observe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.project.myapplicationsms.R;
import com.project.myapplicationsms.utils.LogUtils;

import java.util.Set;




public class MonitorService extends Service {

    private SmsContent smsContent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化操作
     */
    public void init() {
        startForeground(this);
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("====","service");
        smsContent = new SmsContent(new Handler(), MonitorService.this);
        getContentResolver().registerContentObserver(
                Uri.parse("content://sms/"), true, smsContent);
        return super.onStartCommand(intent, flags, startId);
    }

    public static void startForeground(Service context) {
        //设置常驻通知栏
        //保持为前台应用状态
        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("手机监控器监听通知栏中...")
                .setContentText("")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MIN)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true);
        Notification notification = builder.build();
        context.startForeground(8888, notification);
    }

    @Override
    public void onDestroy() {
        try {
            if(smsContent!=null){
                getContentResolver().unregisterContentObserver(smsContent);
            }
            //进行自动重启
            Intent intent = new Intent(MonitorService.this, MonitorService.class);
            //重新开启服务
            startService(intent);
            stopForeground(true);
            super.onDestroy();
        }catch (Exception e){

        }
    }


}

