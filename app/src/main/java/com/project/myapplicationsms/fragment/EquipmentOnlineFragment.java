package com.project.myapplicationsms.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.bean.QiniuSettingBean;
import com.project.myapplicationsms.bean.UserLoginBean;
import com.project.myapplicationsms.http.NetApiUtil;
import com.project.myapplicationsms.network.ServerResult;
import com.project.myapplicationsms.utils.ThreadUtil;


public class EquipmentOnlineFragment  extends BaseFragment {
    private RelativeLayout rlBack;
    private  TextView tvTitle;
    private  View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view=inflater.inflate(R.layout.fragment_equipment_layout,null,false);
        }
        initView();
        initData();
        return view;
    }

    public  void initView(){
        rlBack=view.findViewById(R.id.common_back_rl);
        rlBack.setVisibility(View.GONE);
        tvTitle=view.findViewById(R.id.common_title_tv);
        tvTitle.setText("设备上线");
    }

    public  void  initData(){
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                ServerResult<QiniuSettingBean> visitRecordDetail = NetApiUtil.getQiniuSetting();
            }
        });
    }
}
