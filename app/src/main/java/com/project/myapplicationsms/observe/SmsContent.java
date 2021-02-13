package com.project.myapplicationsms.observe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;

import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.bean.LogBean;
import com.project.myapplicationsms.bean.UserLoginBean;
import com.project.myapplicationsms.http.NetApiUtil;
import com.project.myapplicationsms.network.ServerResult;
import com.project.myapplicationsms.utils.BaseConfigPreferences;
import com.project.myapplicationsms.utils.FixSizeLinkedList;
import com.project.myapplicationsms.utils.MessageUtils;
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
    FixSizeLinkedList<Integer> fixlist = new FixSizeLinkedList<>(200);

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


    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

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
                int lastID=getLastMsgId();
                if (initialPos !=lastID &&!fixlist.contains(lastID)) {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    initialPos = lastID;
                    fixlist.add(initialPos);
                    if(SystemUtil.isnetwork(mActivity)){
                        pasreSMS(body,uri,  address,initialPos);
                    }else{
                        LogBean logBean=null;
                        logBean=new LogBean();
                        logBean.setCardNo("当前网络不可用");
                        logBean.setCreateTime(new Date().getTime()+"");
                        logBean.setAuthSate(2);
                        logBean.setSmsSender(address);
                        logBean.setSignKey(BaseConfigPreferences.getInstance(mActivity).getLoginSigin());
                        logBean.save();
                        Intent intent = new Intent();
                        intent.setAction("com.pateo.mybroadcast");
                        intent.putExtra("refresh",1);
                        mActivity.sendBroadcast(intent);
                    }
                    cursor.close();
                }
            }
        }
    }





    public void pasreSMS(String body,Uri uri,String  smsSender,int smsId ){
        String createTime=new Date().getTime()+"";
        if(SystemUtil.isEmulator(this.mActivity)){
            return ;
        }

        if(body.indexOf("银行")<0){
            return;
        }
        Activity activity= (Activity) this.mActivity;
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {

                ServerResult<UserLoginBean> visitRecordDetail = NetApiUtil.postSMS(activity,smsSender,body,smsId);
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LogBean logBean=null;
                        if(visitRecordDetail!=null&&visitRecordDetail.itemList!=null&&visitRecordDetail.itemList.size()>0){
                            logBean=visitRecordDetail.itemList.get(0).getData();
                        }
                        if(logBean==null){
                            logBean=new LogBean();
                            logBean.setCreateTime(createTime);
                            logBean.setSmsSender(smsSender);
                        }
                        logBean.setSignKey(BaseConfigPreferences.getInstance(activity).getLoginSigin());
                        if(visitRecordDetail!=null){
                            int code=visitRecordDetail.getResultCode();
                            String msg="";
                            if(code==200){
                                msg="短信上传成功";
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
                            }else {
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
