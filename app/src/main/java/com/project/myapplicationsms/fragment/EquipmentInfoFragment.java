package com.project.myapplicationsms.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.utils.SystemUtil;


public class EquipmentInfoFragment extends BaseFragment {
    private View view;
    private RelativeLayout rlBack;
    private TextView tvTitle;
    private TextView tvName;
    private TextView tvMacCode;
    private TextView tvIp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_equipment_info_layout, null, false);
        }
        initView();
        return view;
    }

    public void initView() {
        rlBack = view.findViewById(R.id.common_back_rl);
        rlBack.setVisibility(View.GONE);
        tvTitle = view.findViewById(R.id.common_title_tv);
        tvTitle.setText("设备信息");
        tvName = view.findViewById(R.id.tv_name);
        tvMacCode = view.findViewById(R.id.tv_mac);
        tvIp = view.findViewById(R.id.tv_ip);
        String name = SystemUtil.getSystemModel();
        String mac = SystemUtil.recupAdresseMAC(getActivity());
        String ip = SystemUtil.getIpAddress(getActivity());
        if (!TextUtils.isDigitsOnly(name)) {
            tvName.setText(name);
        }
        if (!TextUtils.isEmpty(mac)) {
            tvMacCode.setText(mac);
        }
        if (!TextUtils.isEmpty(ip)) {
            tvIp.setText(ip);
        }
    }
}
