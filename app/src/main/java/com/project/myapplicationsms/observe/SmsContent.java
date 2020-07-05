package com.project.myapplicationsms.observe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.bean.LogBean;
import com.project.myapplicationsms.bean.UserLoginBean;
import com.project.myapplicationsms.http.NetApiUtil;
import com.project.myapplicationsms.network.ServerResult;
import com.project.myapplicationsms.utils.BaseConfigPreferences;
import com.project.myapplicationsms.utils.MessageUtils;
import com.project.myapplicationsms.utils.StringUtils;
import com.project.myapplicationsms.utils.SystemUtil;
import com.project.myapplicationsms.utils.ThreadUtil;

import java.util.Date;

public  class SmsContent extends ContentObserver {
    private static final String TAG = SmsContent.class.getSimpleName();
    private static final String MARKER = "YOUR_KEYWORD";
    private Cursor cursor = null;
    private Context mActivity;


    public SmsContent(Handler handler, Context activity) {
        super(handler);
        this.mActivity = activity;
    }
    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        if (uri == null) {
            uri = Uri.parse("content://sms/inbox");
        }
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        cursor = this.mActivity.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                //String input="您尾号7293的储蓄卡6月12日7时49分支付宝提现收入人民币500.09元,活期余额611.33元。[建设银行]";
                pasreSMS(body);
                cursor.close();

            }
        }
    }


    public void pasreSMS(String body){
        String amount= StringUtils.parseInMoney(body);
        String cardNo=StringUtils.parseBankLastFour(body);
        String bankName=StringUtils.parseBankName(body);
        String createTime=new Date().getTime()+"";
        String macCode= SystemUtil.getMac(this.mActivity);
        if(SystemUtil.isEmulator(this.mActivity)){
            return ;
        }
        Activity activity= (Activity) this.mActivity;
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {

                ServerResult<UserLoginBean> visitRecordDetail = NetApiUtil.postSMS(amount,cardNo,bankName,activity);
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LogBean logBean=new LogBean();
                        logBean.setAmount(amount);
                        logBean.setCreateTime(createTime);
                        logBean.setBankName(bankName);
                        logBean.setCardNo(cardNo);
                        logBean.setSignKey(BaseConfigPreferences.getInstance(activity).getLoginSigin());
                        if(visitRecordDetail!=null){
                            int code=visitRecordDetail.getResultCode();
                            String msg="";
                            if(code==200){
                                msg="认证成功";
                                logBean.setAuthSate(1);
                            }else if(code==4000){
                                msg="解密失败";
                                logBean.setAuthSate(2);
                            }else if(code==5001){
                                msg="待认证";
                                logBean.setAuthSate(2);
                            }else if(code==6001){
                                msg="回调失败";
                                logBean.setAuthSate(2);
                            }
                            logBean.save();
                            if(code==200){
                                MessageUtils.show(activity,msg);
                            }else{
                                MessageUtils.show(activity,msg+"code:"+code);
                            }
                            Intent intent = new Intent();
                            intent.setAction("com.pateo.mybroadcast");
                            intent.putExtra("refresh",1);
                            activity.sendBroadcast(intent);
                        }else{
                            logBean.setAuthSate(2);
                            logBean.save();
                            Intent intent = new Intent();
                            intent.setAction("com.pateo.mybroadcast");
                            intent.putExtra("refresh",1);
                            activity.sendBroadcast(intent);
                        }
                    }
                });
            }
        });
    }
}
