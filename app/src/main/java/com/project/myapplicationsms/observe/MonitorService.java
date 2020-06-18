package com.project.myapplicationsms.observe;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.project.myapplicationsms.R;
import com.project.myapplicationsms.utils.LogUtils;






public class MonitorService extends Service {
    private SmsContent smsContent;
    String CHANNEL_ID = "com.example.test";
    String CHANNEL_NAME = "TEST";
    NotificationChannel notificationChannel = null;
    NotificationCompat.Builder builder = null;



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

    public  void startForeground(Service context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        builder = new NotificationCompat.Builder(this, CHANNEL_ID).
                setContentTitle(getResources().getString(R.string.app_name)).
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.mipmap.ic_launcher).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setPriority(Notification.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK版本>=21才能设置悬挂式通知栏
            builder.setCategory(String.valueOf(Notification.FLAG_ONGOING_EVENT))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setColor(getResources().getColor(R.color.common_gray));
            Intent intent2 = new Intent();
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
            builder.setFullScreenIntent(pi, true);
        }
        Notification notification = builder.build();
        startForeground(1, notification);
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
            stopForeground(true);
            super.onDestroy();
        }catch (Exception e){

        }
    }


}

