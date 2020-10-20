package com.project.myapplicationsms.adapter;

import android.text.TextUtils;
import android.view.TextureView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.myapplicationsms.R;
import com.project.myapplicationsms.bean.LogBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public  class BankErrorAdapter extends BaseQuickAdapter<LogBean, BaseViewHolder> {
    private List<LogBean> list;
    public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat simpleDateFormat;

    public void setList(List<LogBean> list) {
        this.list = list;
    }

    public BankErrorAdapter(@Nullable List<LogBean> data) {
        super(R.layout.item_rc_error_list_ayout, data);
        list=data;
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogBean item) {
        TextView tvName=helper.getView(R.id.tv_name);
        TextView tvNo=helper.getView(R.id.tv_no);
        TextView tvTime=helper.getView(R.id.tv_time);
        TextView tvUsername=helper.getView(R.id.tv_username);


        if(!TextUtils.isEmpty(item.getBankName())){
            tvName.setText(item.getBankName());
        }else{
            tvName.setText("");
        }

        if(!TextUtils.isEmpty(item.getCardNo())){
             tvNo.setText(item.getCardNo());
        }else{
            tvNo.setText("");
        }


        if(!TextUtils.isEmpty(item.getCreateTime())){
            tvTime.setText(item.getCreateTime());
        }else{
            tvTime.setText("");
        }

        if(!TextUtils.isEmpty(item.getUserName())){
            tvUsername.setText(item.getUserName());
        }else {
            tvUsername.setText("");
        }
    }


}
