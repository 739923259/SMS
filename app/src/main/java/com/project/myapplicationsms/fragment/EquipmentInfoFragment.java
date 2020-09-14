package com.project.myapplicationsms.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.network.ApiUrlManager;
import com.project.myapplicationsms.utils.BaseConfigPreferences;
import com.project.myapplicationsms.utils.SystemUtil;


public class EquipmentInfoFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private RelativeLayout rlBack;
    private TextView tvTitle;
    private TextView tvName;
    private TextView tvMacCode;
    private TextView tvIp;
    private TextView tvVersion;
    private EditText etAlia;
    private  TextView tvEdit;

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
        tvVersion=view.findViewById(R.id.tv_version);
        tvIp = view.findViewById(R.id.tv_ip);
        etAlia=view.findViewById(R.id.et_alia);
        tvEdit=view.findViewById(R.id.tv_edit_alia);
        tvEdit.setOnClickListener(this);
        String name = SystemUtil.getSystemModel();
        String mac = SystemUtil.recupAdresseMAC(getActivity());
        String ip = SystemUtil.getIpAddress(getActivity());
        String version=SystemUtil.getVersionName(getActivity());
        if (!TextUtils.isDigitsOnly(name)) {
            tvName.setText(name);
        }
        if (!TextUtils.isEmpty(mac)) {
            tvMacCode.setText(mac);
        }
        if (!TextUtils.isEmpty(ip)) {
            tvIp.setText(ip);
        }

        if (!TextUtils.isEmpty(version)) {
            tvVersion.setText(version);
        }

        String alia= BaseConfigPreferences.getInstance(getActivity()).getAlia();
        if(!TextUtils.isEmpty(alia)){
            etAlia.setText(alia);
            setCanEdit(false);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_edit_alia){
            if(tvEdit.getText().equals("编辑")){
                tvEdit.setText("完成");
                setCanEdit(true);
                etAlia.setFocusable(true);
                etAlia.setFocusableInTouchMode(true);
                etAlia.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) etAlia.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etAlia, 0);
            }else{
                tvEdit.setText("编辑");
                setCanEdit(false);
                BaseConfigPreferences.getInstance(getActivity()).setAlia(etAlia.getText().toString());
            }
        }
    }

    public  void setCanEdit(boolean isEdit){
        etAlia.setEnabled(isEdit);
    }
}
