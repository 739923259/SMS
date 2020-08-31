package com.project.myapplicationsms;

import android.content.Context;
import android.os.Bundle;

import com.project.myapplicationsms.observe.SmsContent;
import com.project.myapplicationsms.utils.StringUtils;
import com.project.myapplicationsms.utils.SystemUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
        ArrayList list=new ArrayList();
        list.add("【中信银行】您尾号5652的中信卡于08月20日11:20，支付宝存入人民币999.80元，当前余额为人民币1001.47元。");
        list.add("尾号4033账户03:31存入299.82元，余额1500.56元，摘要:辛毅支付宝转账 辛毅支付宝转账。[光大银行]");
        list.add("【邮储银行】20年08月15日03:02您尾号421账户提现金额499.83元，余额500.22元。");
        list.add("您尾号*3455的卡于08月17日23:59在支付宝转入299.88元，交易后余额为300.81元。【交通银行】");
        list.add("您存款账户7607于8月11日3:41付款业务转入人民币299.94元，活期余额人民币300.52元。【平安银行】");
        list.add("您尾号4912的储蓄卡8月20日11时48分支付宝提现收入人民币299.84元,活期余额1105.24元。[建设银行]");
        list.add("您的账户0163于08月20日12:14收入人民币2,001,600.00元，余额52,001.86元。【华夏银行】");
        list.add("【中国农业银行】方孜行于08月17日18:12向您尾号2379账户完成转存交易人民币699.97，余额700.96。");
        list.add("您尾号5960卡8月12日13:16网上银行收入(银联入账)299.92元，余额317.12元。【工商银行】");
        list.add("29日12:53账户*4517*网联付款收入299.95元，余额1100.80元[兴业银行]");
        list.add("【招商银行】手续费6折！即日起至8月31日办理e招贷指定期数可享，单期手续费率低至0.45%，具体以系统实时测评为准，转发无效。戳 cmbt.cn/arRyRI9Kh99 退订回#A");
        list.add("贵账户*0977于2020年08月30日11:12在海南省分行转入资金0.10元，现余额3.17元，附言：支付宝转账9999.8元【交通银行】");
        list.add("王飞8月30日11时9分向您尾号8600的储蓄卡支付宝转账9999.98元存入人民币0.10元，活期余额4.28.[建设银行]");
        list.add("8月22日9时22分向您尾号6777的储蓄卡电子汇入存入人民币20000.00元,活期余额60399.06元。[建设银行]");


       for(int i=0;i<list.size();i++){
           Log.i("===", StringUtils.parseBankLastFour(list.get(i).toString())+"==="+StringUtils.parseBankName(list.get(i).toString())+"==="+StringUtils.parseInMoney(list.get(i).toString()));
           // Log.i("===",StringUtils.isNumber(StringUtils.parseBankLastFour(list.get(i).toString()))+"");
       }




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(smsContent);
    }


}