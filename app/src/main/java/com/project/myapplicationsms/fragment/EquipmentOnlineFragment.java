package com.project.myapplicationsms.fragment;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.bean.LogBean;
import com.project.myapplicationsms.bean.QiniuSettingBean;
import com.project.myapplicationsms.bean.UserLoginBean;
import com.project.myapplicationsms.http.NetApiUtil;
import com.project.myapplicationsms.network.ApiUrlManager;
import com.project.myapplicationsms.network.ServerResult;
import com.project.myapplicationsms.ui.MainActivity;
import com.project.myapplicationsms.utils.BaseConfigPreferences;
import com.project.myapplicationsms.utils.MessageUtils;
import com.project.myapplicationsms.utils.StringUtils;
import com.project.myapplicationsms.utils.SystemUtil;
import com.project.myapplicationsms.utils.ThreadUtil;

import org.litepal.LitePal;

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
    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //在这里执行定时需要的操作
            if (flag) {
                submitData();
                mHandler.postDelayed(this, 5000*10);

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
           submitData();
       }
       // LitePal.deleteAll(LogBean.class);
       /* for(int i=0;i<50;i++){
            LogBean logBean=new LogBean();
            logBean.setAmount("100");
            logBean.setCreateTime(new Date().getTime()+"");
            logBean.setBankName("中国银行"+i);
            logBean.setCardNo("7777");
            if(i%2==0){
                logBean.setAuthSate(1);
            }else{
                logBean.setAuthSate(2);
            }
            logBean.save();
        }
        List<LogBean> allMovies = LitePal.findAll(LogBean.class);*/
    }

    private void setTimer(){
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 5000*10);
    }

    public  void  submitData(){
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
                ServerResult<UserLoginBean> visitRecordDetail = NetApiUtil.postUserAuth(etUrl.getText().toString(),etSign.getText().toString(),getActivity());
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(visitRecordDetail!=null){
                            int code=visitRecordDetail.getResultCode();
                            String msg="";
                            if(code==200){
                                setCanEdit(false);
                                msg="认证成功";
                                BaseConfigPreferences.getInstance(getActivity()).setLoginSigin(etSign.getText().toString());
                                BaseConfigPreferences.getInstance(getActivity()).setBaseUrl(etUrl.getText().toString());
                                setTimer();
                                MainActivity parentActivity = (MainActivity) getActivity();
                                parentActivity.setSelectPositon(3);
                            }else if(code==4000){
                                msg="解密失败";
                            }else if(code==5001){
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
                                tvSubmit.setText("已上线");
                            }else{
                                tvSubmit.setText("上线");
                                MessageUtils.show(getActivity(),msg+"code:"+code);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_submit){
            submitData();
        }else if(v.getId()==R.id.tv_submit1){
           // String input="您尾号7293的储蓄卡6月12日7时49分支付宝提现收入人民币500.09元,活期余额611.33元。[建设银行]";
           // pasreSMS(input);
        }else if(v.getId()==R.id.tv_edit){
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
