package com.project.myapplicationsms;

import android.content.Context;
import android.os.Bundle;

import com.project.myapplicationsms.observe.SmsContent;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static final String CLASS_SMS_MANAGER = "com.android.internal.telephony.SmsApplication";
    public static final String METHOD_SET_DEFAULT = "setDefaultApplication";
    private void setDefaultSms(Boolean isMyApp) {
        try {
            Class<?> smsClass = Class.forName(CLASS_SMS_MANAGER);
            Method method = smsClass.getMethod(METHOD_SET_DEFAULT, String.class, Context.class);
            method.invoke(null, "要设置的包名", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SmsContent smsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*String defaultSmsApp = null;
        String currentPn = getPackageName();//获取当前程序包名
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);//获取手机当前设置的默认短信应用的包名
        }
        if (!defaultSmsApp.equals(currentPn)) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, currentPn);
            startActivity(intent);
        }*/
      /*  smsContent = new SmsContent(new Handler(), this);
        getContentResolver().registerContentObserver(
                Uri.parse("content://sms/"), true, smsContent);*/
        // 银行名称#卡号尾号#金额

       // Pattern pattern = Pattern.compile("【(.*?)】|\\[(.*?)\\]");//银行名称
        //Pattern pattern = Pattern.compile("[\\*0-9\\.]+");//金额
       // String input="【邮储银行】20年06月12日16:15您尾号019账户提现金额250.46元，余额2458.07元。";
        String input="尾号6965账户14:46存入300.64元，余额2127.53元，摘要:王祺禛支付宝转账 王祺禛支付宝转账。[光大银行]";
        String zhanghao=input.substring(input.indexOf("尾号")+2,input.indexOf("尾号")+6);
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher m=pat.matcher(zhanghao);
       zhanghao=m.replaceAll("");
       Log.i("===",zhanghao);



        Pattern pattern = Pattern.compile("[\\*0-9\\.]+");
       // Matcher matcher = pattern.matcher("您尾号7293的储蓄卡6月12日7时49分支付宝提现收入人民币500.09元,活期余额611.33元。[建设银行]");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String group = matcher.group().replace("【","").replace("】","").replace("[","").replace("]","");
           Log.i("===",group);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(smsContent);
    }
}