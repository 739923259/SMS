package com.project.myapplicationsms.observe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
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
    private static int initialPos;
    private static final Uri uriSMS = Uri.parse("content://sms/inbox");

    public SmsContent(Handler handler, Context activity) {
        super(handler);
        this.mActivity = activity;
        initialPos = getLastMsgId();
    }

    public int getLastMsgId() {
        Cursor cur = mActivity.getContentResolver().query(uriSMS, null, null, null, null);
        if(cur!=null){
           boolean ok= cur.moveToFirst();
           if(ok){
               int lastMsgId = cur.getInt(cur.getColumnIndex("_id"));
               return lastMsgId;
           }
            return  0;
        }
        return  0;

    }




    // 只要 “content://sms” 里面的数据发生了变化就会调用该方法
    // selfChange = false, Uri = content://sms/2750 收到短信后调用的
    // selfChange = false, Uri = content://sms/inbox 打开了短信 App 后调用的

    @Override
    public void onChange(boolean selfChange, Uri uri) {



        if (uri == null) {
            uri = Uri.parse("content://sms/inbox");
        } else {
            uri = uri;
        }

        if (uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms")) {
            return;
        }

        cursor = this.mActivity.getContentResolver().query(uri, null, null, null, "date desc");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                if (initialPos != getLastMsgId()) {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    //Log.i("===",uri.toString());
                    initialPos = getLastMsgId();
                    pasreSMS(body,uri);
                    cursor.close();
                }
            }
        }
    }


    public void pasreSMS(String body,Uri uri){
        String amount= StringUtils.parseInMoney(body);
        String cardNo=StringUtils.parseBankLastFour(body);
        String bankName=StringUtils.parseBankName(body);
        String createTime=new Date().getTime()+"";
        String macCode= SystemUtil.recupAdresseMAC(this.mActivity);
        if(SystemUtil.isEmulator(this.mActivity)){
            return ;
        }

        if(TextUtils.isEmpty(amount)||(TextUtils.isEmpty(cardNo))||TextUtils.isEmpty(bankName)||body.indexOf("银行")<0||body.indexOf("余额")<0){
            return;
        }

        if(!StringUtils.isNumber(cardNo)){
            return;
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
                       // logBean.setUrl(uri.toString());
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
