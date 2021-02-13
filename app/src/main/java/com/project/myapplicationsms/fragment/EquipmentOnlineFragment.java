package com.project.myapplicationsms.fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.bean.AuthServerBean;
import com.project.myapplicationsms.bean.LogBean;
import com.project.myapplicationsms.bean.UserLoginBean;
import com.project.myapplicationsms.http.NetApiUtil;
import com.project.myapplicationsms.network.ApiUrlManager;
import com.project.myapplicationsms.network.ServerResult;
import com.project.myapplicationsms.ui.MainActivity;
import com.project.myapplicationsms.utils.BaseConfigPreferences;
import com.project.myapplicationsms.utils.MessageUtils;
import com.project.myapplicationsms.utils.SystemUtil;
import com.project.myapplicationsms.utils.ThreadUtil;

import java.util.Date;
import java.util.List;


public class EquipmentOnlineFragment  extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rlBack;
    private  TextView tvTitle;
    private  View view;
    private EditText etUrl;
    private  EditText etSign;
    private  TextView tvSubmit;
    private  TextView tvSubmit1;
    private  TextView tvEdit;
    private static boolean flag = true;
    private SoundPool soundPool;
    private  static  final  int DELAY_TIME=5000*10;
    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //在这里执行定时需要的操作
          //  Log.i("====",flag+"");
            if (flag) {
                if(SystemUtil.isnetwork(getActivity())){
                    InnerSorsiData();
                }else {
                    Global.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            MessageUtils.show(getActivity(),"网络不可用");
                        }
                    });
                }
                mHandler.postDelayed(this, DELAY_TIME);
            }
        }
    };

    private void stopTimer(){
        flag = false;
        mHandler.removeCallbacks(runnable);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view=inflater.inflate(R.layout.fragment_equipment_layout,null,false);
        }
        initView();
        return view;
    }

    public  void initView(){
        rlBack=view.findViewById(R.id.common_back_rl);
        rlBack.setVisibility(View.GONE);
        tvTitle=view.findViewById(R.id.common_title_tv);
        tvTitle.setText("设备上线");
        etUrl=view.findViewById(R.id.et_url);
        etSign=view.findViewById(R.id.et_sign);
        tvSubmit=view.findViewById(R.id.tv_submit);
        tvSubmit1=view.findViewById(R.id.tv_submit1);
        tvEdit=view.findViewById(R.id.tv_edit);
        tvSubmit.setOnClickListener(this);
        tvSubmit1.setOnClickListener(this);
        tvSubmit1.setVisibility(View.GONE);
        tvEdit.setOnClickListener(this);
        tvEdit.setVisibility(View.VISIBLE);
       String sigin= BaseConfigPreferences.getInstance(getActivity()).getLoginSigin();
       String url= BaseConfigPreferences.getInstance(getActivity()).getCloud_baseurl();

       if(!TextUtils.isEmpty(url)){
           ApiUrlManager.BaseUrl=url;
           etUrl.setText(url);
           setCanEdit(false);
       }
       if(!TextUtils.isEmpty(sigin)){
           etSign.setText(sigin);
           setCanEdit(false);
       }

       if(!TextUtils.isEmpty(url)&&!TextUtils.isEmpty(sigin)){
           if(SystemUtil.isnetwork(getActivity())){
               InnerHandlesData();
           }else {
               MessageUtils.show(getActivity(),"网络不可用");
           }

       }
    }

    private void setTimer(){
        mHandler.removeCallbacks(runnable);
        flag=true;
        mHandler.postDelayed(runnable, DELAY_TIME);
    }


    private void processCustomMessage() {
        try {

            soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            int id = 1;
            id = soundPool.load(getActivity(), R.raw.error, 1);
            int finalId = id;
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(finalId, 1, 1, 0, 0, 1);
                    Intent intent = new Intent();
                    intent.setAction("com.pateo.mybroadcast.bank.error");
                    intent.putExtra("refresh",3);
                    getActivity().sendBroadcast(intent);
                    MainActivity parentActivity = (MainActivity) getActivity();
                    parentActivity.setSelectPositon(3);

                    Intent intentTab = new Intent();
                    intentTab.setAction("com.pateo.mybroadcast.tab");
                    intentTab.putExtra("selectTab",3);
                    getActivity().sendBroadcast(intentTab);

                }
            });
        } catch (Exception e) {
           // Log.i("====",e.getMessage());
        }
    }


    private void errorIpTip() {
        try {

            soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            int id = 2;
            id = soundPool.load(getActivity(), R.raw.error_ip, 1);
            int finalId = id;
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(finalId, 1, 1, 0, 0, 1);
                }
            });
        } catch (Exception e) {
            // Log.i("====",e.getMessage());
        }
    }

    /*woshou*/
    public  void  InnerHandlesData(){
        if(SystemUtil.isEmulator(getContext())){
            return ;
        }
        if(TextUtils.isEmpty(etUrl.getText().toString())){
            MessageUtils.show(getActivity(),"请输入云端地址");
            return;
        }
        if(TextUtils.isEmpty(etSign.getText().toString())){
            MessageUtils.show(getActivity(),"请输入设备key");
            return;
        }
        ApiUrlManager.BaseUrl=etUrl.getText().toString().trim();
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                ServerResult<AuthServerBean> visitRecordDetail = NetApiUtil.postUserAuth(1,etUrl.getText().toString(),etSign.getText().toString(),getActivity());
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(visitRecordDetail!=null){
                            int code=visitRecordDetail.getResultCode();
                            String msg="";
                            if(code==5001){
                                msg="待认证";
                            }else if(code==5002){
                                msg="认证失败";
                            }else if(code==5003){
                                setCanEdit(false);
                                msg="认证成功";
                                BaseConfigPreferences.getInstance(getActivity()).setBaseUrl(etUrl.getText().toString());
                                BaseConfigPreferences.getInstance(getActivity()).setLoginSigin(etSign.getText().toString());
                                setTimer();
                                MainActivity parentActivity = (MainActivity) getActivity();
                                parentActivity.setSelectPositon(3);
                            }
                            if(code==5003){
                                MessageUtils.show(getActivity(),msg);
                                setOnlineBtn(true,1);
                            }else{
                                setOnlineBtn(false,0);
                                MessageUtils.show(getActivity(),"握手接口"+msg+"code:"+code);
                            }
                        }
                    }
                });
            }
        });
    }

    /*xintiao*/
    public  void  InnerSorsiData(){
        if(SystemUtil.isEmulator(getContext())){
            return ;
        }
        if(TextUtils.isEmpty(etUrl.getText().toString())){
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    MessageUtils.show(getActivity(),"请输入云端地址");
                }
            });
            return;
        }
        if(TextUtils.isEmpty(etSign.getText().toString())){
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    MessageUtils.show(getActivity(),"请输入设备key");
                }
            });
            return;
        }
        ApiUrlManager.BaseUrl=etUrl.getText().toString().trim();
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                ServerResult<AuthServerBean> visitRecordDetail = NetApiUtil.postUserAuth(2,etUrl.getText().toString(),etSign.getText().toString(),getActivity());
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(visitRecordDetail!=null){
                            int code=visitRecordDetail.getResultCode();
                            String msg="";
                            if(code==5001){
                                msg="待认证";
                                InnerHandlesData();
                            }else if(code==5002){
                                msg="认证失败";
                                InnerHandlesData();
                            }else if(code==5003){
                                setCanEdit(false);
                                msg="认证成功";
                                BaseConfigPreferences.getInstance(getActivity()).setBaseUrl(etUrl.getText().toString());
                                BaseConfigPreferences.getInstance(getActivity()).setLoginSigin(etSign.getText().toString());
                                setTimer();
                                MainActivity parentActivity = (MainActivity) getActivity();
                                parentActivity.setSelectPositon(3);
                            }else if(code==7001){//卡号异常
                                if(visitRecordDetail.itemList!=null&&visitRecordDetail.itemList.size()>0&&visitRecordDetail.itemList.get(0).getData()!=null){
                                    List<AuthServerBean.DataBean.UnPayCardsBean> list=visitRecordDetail.itemList.get(0).getData().getUnPayCards();
                                    for (int i=0;i<list.size();i++){
                                        LogBean logBean=new LogBean();
                                        logBean.setAuthSate(3);
                                        logBean.setBankName(list.get(i).getBankName());
                                        logBean.setCreateTime(list.get(i).getSysTime());
                                        logBean.setFomartime(list.get(i).getTime());
                                        logBean.setCardNo(list.get(i).getCardNo());
                                        logBean.setUserName(list.get(i).getUserName());
                                        logBean.save();
                                    }
                                    processCustomMessage();
                                }

                            }else if(code==7002){//非法ip
                                errorIpTip();
                            }

                            if(code==5003){
                                MessageUtils.show(getActivity(),msg);
                                setOnlineBtn(true,1);
                            }else{
                               // setOnlineBtn(false,0);
                                MessageUtils.show(getActivity(),"心跳接口"+msg+"code:"+code);
                            }
                        }
                    }
                });
            }
        });
    }


    /*state 0 未上线  1已上线*/
    public void setOnlineBtn(boolean disalbe,int state){
        if(state==0){
            tvSubmit.setText("上线");
        }else{
            tvSubmit.setText("已上线");
        }
        if(disalbe){
            tvSubmit.setClickable(false);
            tvSubmit.setBackgroundResource(R.drawable.btn_10_conner_disable);
        }else{
            tvSubmit.setClickable(true);
            tvSubmit.setBackgroundResource(R.drawable.btn_10_conner);
        }
    }



    public void pasreSMS(String body, Uri uri, String  smsSender, int smsId ){
        String createTime=new Date().getTime()+"";
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {

                ServerResult<UserLoginBean> visitRecordDetail = NetApiUtil.postSMS(getActivity(),smsSender,body,smsId);
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
                        logBean.setSignKey(BaseConfigPreferences.getInstance(getActivity()).getLoginSigin());
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
                            }
                            logBean.save();
                            if(code==200){
                                MessageUtils.show(getActivity(),msg);
                            }else{
                                MessageUtils.show(getActivity(),msg+"code:"+code);
                            }
                            Intent intent = new Intent();
                            intent.setAction("com.pateo.mybroadcast");
                            intent.putExtra("refresh",1);
                            getActivity().sendBroadcast(intent);
                        }else{
                            logBean.setAuthSate(2);
                            logBean.save();
                            Intent intent = new Intent();
                            intent.setAction("com.pateo.mybroadcast");
                            intent.putExtra("refresh",1);
                            getActivity().sendBroadcast(intent);
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_submit){
            if(SystemUtil.isnetwork(getActivity())){
                stopTimer();
                InnerHandlesData();
            }else {
                MessageUtils.show(getActivity(),"网络不可用");
            }
        }else if(v.getId()==R.id.tv_submit1){
          //  String str="【招商银行】您账户0111于02月13日他行实时转入人民币299.85，付方余立源。推荐享好礼 cmbt.cn/yY7 。";
           // String str="序号05的验证码491567，您向杨海琼尾号4766账户转账8900.0元。任何索要验证码的都是骗子，千万别给！[建设银行], smsId=8735)";
        if(SystemUtil.isnetwork(getActivity())){
            for(int i=0;i<10;i++){
                //  String str="【招商银行】您账户0111于02月13日他行实时转入人民币299.92，付方郭志航。推荐享好礼 cmbt.cn/yY7 。";
                String str="序号05的验证码491567，您向杨海琼尾号4766账户转账8900.0元。任何索要验证码的都是骗子，千万别给！[建设银行], smsId=8735)";
                pasreSMS(str,null,"95533",967);
                //pasreSMS(str,null,"95555",967);
            }
        }else {
            LogBean logBean=null;
            logBean=new LogBean();
            logBean.setCardNo("当前网络不可用");
            logBean.setCreateTime(new Date().getTime()+"");
            logBean.setAuthSate(2);
            logBean.setSmsSender("address");
            logBean.setSignKey(BaseConfigPreferences.getInstance(getActivity()).getLoginSigin());
            logBean.save();
            Intent intent = new Intent();
            intent.setAction("com.pateo.mybroadcast");
            intent.putExtra("refresh",1);
            getActivity().sendBroadcast(intent);
        }
        }else if(v.getId()==R.id.tv_edit){
            tvSubmit.setText("上线");
            tvSubmit.setClickable(true);
            tvSubmit.setBackgroundResource(R.drawable.btn_10_conner);
            if(tvEdit.getText().equals("编辑")){
                tvEdit.setText("完成");
                setCanEdit(true);
            }else{
                tvEdit.setText("编辑");
                setCanEdit(false);
            }
        }
    }

    public  void setCanEdit(boolean isEdit){
        etUrl.setEnabled(isEdit);
        etSign.setEnabled(isEdit);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
