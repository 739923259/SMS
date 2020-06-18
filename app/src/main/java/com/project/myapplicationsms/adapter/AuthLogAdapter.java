package com.project.myapplicationsms.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.myapplicationsms.R;
import com.project.myapplicationsms.bean.AuthLogBean;


import java.util.List;

public  class AuthLogAdapter  extends BaseQuickAdapter<AuthLogBean, BaseViewHolder> {

    public AuthLogAdapter( @Nullable List<AuthLogBean> data) {
        super(R.layout.item_rc_list_ayout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthLogBean item) {
        TextView tvName=helper.getView(R.id.tv_name);
        TextView tvNo=helper.getView(R.id.tv_no);
        TextView tvMoney=helper.getView(R.id.tv_money);
        TextView tvTime=helper.getView(R.id.tv_time);
        if(!TextUtils.isEmpty(item.getBankName())){
            tvName.setText(item.getBankName());
        }else{
            tvName.setText("");
        }

        if(!TextUtils.isEmpty(item.getBankNo())){
             tvNo.setText(item.getBankNo());
        }else{
            tvNo.setText("");
        }


        if(!TextUtils.isEmpty(item.getInMoney())){
            tvMoney.setText(item.getInMoney());
        }else{
            tvMoney.setText("");
        }


        if(!TextUtils.isEmpty(item.getAuthTime())){
            tvTime.setText(item.getAuthTime());
        }else{
            tvTime.setText("");
        }
    }


}
