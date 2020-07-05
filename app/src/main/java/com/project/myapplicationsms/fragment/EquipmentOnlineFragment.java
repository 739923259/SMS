package com.project.myapplicationsms.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.project.myapplicationsms.network.ServerResult;
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
    private static boolean flag = true;
    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //在这里执行定时需要的操作
            if (flag) {
                mHandler.postDelayed(this, 5000);
            }
        }
    };

    private void stopTimer(){
        flag = false;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view=inflater.inflate(R.layout.fragment_equipment_layout,null,false);
        }
        initView();
        setTimer();
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
        tvSubmit.setOnClickListener(this);
        tvSubmit1.setOnClickListener(this);
        LitePal.deleteAll(LogBean.class);
        for(int i=0;i<50;i++){
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
        List<LogBean> allMovies = LitePal.findAll(LogBean.class);
    }

    private void setTimer(){
        mHandler.postDelayed(runnable, 5000);
    }

    public  void  submitData(){
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
                                msg="认证成功";
                            }else if(code==4000){
                                msg="解密失败";
                            }else if(code==5001){
                                msg="待认证";
                            }else if(code==5002){
                                msg="认证失败";
                            }else if(code==5003){
                                msg="认证成功";
                            }
                            Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
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
            String input="您尾号7293的储蓄卡6月12日7时49分支付宝提现收入人民币500.09元,活期余额611.33元。[建设银行]";
            pasreSMS(input);
        }
    }

    public void pasreSMS(String body){
        String amount= StringUtils.parseInMoney(body);
        String cardNo=StringUtils.parseBankLastFour(body);
        String bankName=StringUtils.parseBankName(body);
        String createTime=new Date().getTime()+"";
        String macCode= SystemUtil.getMac(getActivity());
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                ServerResult<UserLoginBean> visitRecordDetail = NetApiUtil.postSMS(amount,cardNo,bankName,getActivity());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
