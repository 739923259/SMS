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
        ArrayList list=new ArrayList();
//        list.add("【中信银行】您尾号5652的中信卡于08月20日11:20，支付宝存入人民币999.80元，当前余额为人民币1001.47元。");
//        list.add("尾号4033账户03:31存入299.82元，余额1500.56元，摘要:辛毅支付宝转账 辛毅支付宝转账。[光大银行]");
//        list.add("【邮储银行】20年08月15日03:02您尾号421账户提现金额499.83元，余额500.22元。");
//        list.add("您尾号*3455的卡于08月17日23:59在支付宝转入299.88元，交易后余额为300.81元。【交通银行】");
//        list.add("您存款账户7607于8月11日3:41付款业务转入人民币299.94元，活期余额人民币300.52元。【平安银行】");
//        list.add("您尾号4912的储蓄卡8月20日11时48分支付宝提现收入人民币299.84元,活期余额1105.24元。[建设银行]");
//        list.add("您的账户0163于08月20日12:14收入人民币2,001,600.00元，余额52,001.86元。【华夏银行】");
//        list.add("【中国农业银行】方孜行于08月17日18:12向您尾号2379账户完成转存交易人民币699.97，余额700.96。");
//        list.add("您尾号5960卡8月12日13:16网上银行收入(银联入账)299.92元，余额317.12元。【工商银行】");
//        list.add("29日12:53账户*4517*网联付款收入299.95元，余额1100.80元[兴业银行]");
//        list.add("【招商银行】手续费6折！即日起至8月31日办理e招贷指定期数可享，单期手续费率低至0.45%，具体以系统实时测评为准，转发无效。戳 cmbt.cn/arRyRI9Kh99 退订回#A");
//        list.add("贵账户*0977于2020年08月30日11:12在海南省分行转入资金0.10元，现余额3.17元，附言：支付宝转账9999.8元【交通银行】");
//        list.add("王飞8月30日11时9分向您尾号8600的储蓄卡支付宝转账9999.98元存入人民币0.10元，活期余额4.28.[建设银行]");
//        list.add("8月22日9时22分向您尾号6777的储蓄卡电子汇入存入人民币20000.00元,活期余额60399.06元。[建设银行]");
//        list.add("【招商银行】您账户0951于09月10日10:52收款人民币101.00，余额101.00，备注：支付宝-支付宝（中国）网络技术有限公，更多详情请查看招商银行APP动账通知。");
//
//
//        list.add("您尾号4963卡9月11日23:56快捷支付收入(张俊杰支付宝转账支付宝)2,000元，余额2,805.93元。【工商银行】");
//        list.add("尾号9186账户14:41存入500元，余额500.85元，摘要:刘道文支付宝转账 刘道文支付宝转账。[光大银行]");
//        list.add("您尾号2170的储蓄卡9月11日14时39分支付宝提现收入人民币1000.00元,活期余额1004.88元。[建设银行]");
//        list.add("杜聿球9月12日1时11分向您尾号8600的储蓄卡支付宝提现收入人民币5000.00元，存入人民币0.10元,活期余额1.04元。[建设银行]");
//        list.add("您尾号*1914的卡于09月11日22:32在支付宝转入300.00元，交易后余额为1367.12元。【交通银行】");
//        list.add("贵账户*0977于2020年08月30日11:12在海南省分行转入资金0.10元，现余额3.17元，附言：支付宝转账9999.8元【交通银行】");
//        list.add("账户*1895于09月11日14:26存入￥300.00元，可用余额304.83元。田静支付宝转账-田静支付宝转账。【民生银行】");
//        list.add("12日00:19账户*4621*网联付款收入300.00元，余额6849.69元[兴业银行]");
//        list.add("【邮储银行】20年09月11日23:12您尾号988账户提现金额300.00元，余额1302.18元。");
//        list.add("【招商银行】您账户0951于09月12日00:10收款人民币1300.00，余额4705.67，备注：支付宝-支付宝（中国）网络技术有限公，更多详情请查看招商银行APP动账通知。");
//        list.add("【中信银行】您尾号3068的中信卡于09月12日0130，支付宝存入人民币500.00元，当前余额为人民币505.39元。");
//
//
//            list.add("贵账户*0977于2020年08月30日11:12在海南省分行转入资金0.10元，现余额3.17元，附言：支付宝转入资金9999.8元【交通银行】");
//            list.add("何龙荣9月12日11时23分向您尾号6777的储蓄卡转入资金999.8【交通银行】存入人民币0.10元,活期余额53.47元。[建设银行]");
//            list.add("何龙荣9月12日12时51分向您尾号6777的储蓄卡附言:转入5000存入人民币0.10元,活期余额53.57元。[建设银行]");
//        list.add("您尾号4963卡9月12日14:30工商银行收入(支付宝转账支付宝）500.00)0.10元，余额6.02元，对方户名：王俊锋，对方账户尾号：0718。【工商银行】");
//
//
//
//        list.add("【邮储银行】20年09月12日01:02您00:58尾号447账户提现金额500.00元，余额1504.97元。");
//        list.add("您尾号4326卡9月12日01:23快捷支付收入(薛晓辉支付宝转账支付宝)500元，余额1,604.77元。【工商银行】");
//        list.add("尾号9186账户16:12存入300元，余额800.85元，摘要:李柳亮支付宝转账 李柳亮支付宝转账。[光大银行]");
//        list.add("您尾号0546的储蓄卡9月12日0时53分支付宝提现收入人民币300.00元,活期余额604.05元。[建设银行]");
//        list.add("李军9月12日1时0分向您尾号0546的储蓄卡电子汇入存入人民币300.00元,活期余额904.05元。[建设银行]");
//        list.add("您尾号*1914的卡于09月11日20:06网银转入5000.00元,交易后余额为5005.13元。【交通银行】");
//        list.add("账户*1895于09月11日14:26存入￥300.00元，可用余额304.83元。田静支付宝转账-田静支付宝转账。【民生银行】");
//        list.add("账户*1895于09月10日11:00存入￥10.00元，可用余额111.26元。银联入账-商户资金结算-张桂林支付宝转账。【民生银行】");
//        list.add("12日01:26账户*4777*网联付款收入2000.00元，余额5305.84元[兴业银行]");
//        list.add("11日23:48账户*4621*汇款汇入收入300.00元，余额2404.69元。对方户名:徐梁博超[兴业银行]");
//        list.add("【招商银行】您账户0951于09月12日00:36收款人民币1000.00，余额1005.67，备注：支付宝-支付宝（中国）网络技术有限公，更多详情请查看招商银行APP动账通知。");
//        list.add("【中信银行】您尾号7028的中信卡于09月12日14:14，跨行转入存入人民币501.00元，当前余额为人民币504.91元。");
//        list.add("【邮储银行】20年09月12日01:02王俊锋账户0718向您尾号813账户他行来账金额1.00元，余额6.87元。");
//        list.add("您尾号4963卡9月12日14:30工商银行收入(支付宝转账支付宝）50)0.10元，余额6.02元，对方户名：王俊锋，对方账户尾号：0718。【工商银行】");
//        list.add("12日14:06账户*4621*汇款汇入收入102.00元，余额107.68元。对方户名:王俊锋（存入5000）[兴业银行]");
//        list.add("王俊锋向尾号9186账户15:13转入300元，余额为304.85元，摘要:网银跨行汇款存入600.00元。[光大银行]");
//        list.add("贵账户*0977于2020年08月30日11:12在海南省分行转入资金0.10元，现余额3.17元，附言：支付宝转账9999.8元【交通银行】");
//        list.add("【招商银行】您账户0951于09月12日00:36收到本行转入人民币2.00，余额7.67，付方王俊锋，账号尾号0718，备注：收款人民币500.00元");
//        list.add("杜聿球9月12日1时11分向您尾号8600的储蓄卡支付宝提现收入人民币5000.00元，存入人民币0.10元,活期余额1.04元。[建设银行]");
//        list.add("账户*1895于09月12日14:41存入￥1.00元，可用余额5.83元。存入￥500.00元。【民生银行】");
//
//        list.add("杜聿球9月12日1时11分向您尾号8600的储蓄卡支付宝提现收入人民币5000.00元【民生银行】附言：支付宝转账9999.8元，存入人民币0.10元,活期余额1.04元。[建设银行]");
//
//
//        list.add("您尾号4963卡9月15日00:58工商银行收入((转账)5000.00元，余00:58)))0.10元，余额2,905.11元，对方户名：陈德榆，对方账户尾号：6901。【工商银行】");
//        list.add("陈德榆9月15日1时48分向您尾号4720的储蓄卡支付宝提现收入人民币3000.00元，存入人民币0.10元,活期余额5.11元。[建设银 ");


        list.add("您的借记卡账户2572，于09月23日收入(网银跨行)人民币500.00元,交易后余额20958.35【中国银行】");

       for(int i=0;i<list.size();i++){
           Log.i("===", StringUtils.parseBankLastFour(list.get(i).toString())+"==="+StringUtils.parseBankName(list.get(i).toString())+"==="+StringUtils.parseInMoney(list.get(i).toString()));
       }




    }


    public static String change_str(String str){
        String newStr = "";
        int level = 0;
        for (int i = 0; i < str.length(); ++i){
            if (str.charAt(i) == '('){
                if (level == 0) //check before incrementing
                    newStr += "(";
                level++;
            } else if (str.charAt(i) == ')'){
                level--;
                if (level == 0) //check after incrementing
                    newStr += ")";
            } else {
                newStr += str.charAt(i);
            }
        }

        return newStr;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(smsContent);
    }


}