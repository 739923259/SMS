package com.project.myapplicationsms.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.bean.LogBean;

import org.litepal.LitePal;

public class MineFragment extends BaseFragment {
    private  View view;
    private TabLayout tableLayout;
    AuthStateFragment authStateFragmentSuccess;
    AuthStateFragment authStateFragmentFail;
    private RelativeLayout rlBack;
    private  TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_mine_layout,null,false);
        }
        authStateFragmentSuccess=new AuthStateFragment();
        Bundle bundleS=new Bundle();
        bundleS.putInt("type",1);
        authStateFragmentSuccess.setArguments(bundleS);
        authStateFragmentFail=new AuthStateFragment();
        Bundle bundleF=new Bundle();
        bundleF.putInt("type",2);
        authStateFragmentFail.setArguments(bundleF);
        initView();
        return view;
    }

    public  void initView(){
        rlBack=view.findViewById(R.id.common_back_rl);
        rlBack.setVisibility(View.GONE);
        tvTitle=view.findViewById(R.id.common_title_tv);
        tvTitle.setText("日志");
        tableLayout=view.findViewById(R.id.tab_layout);
        tableLayout.addTab(tableLayout.newTab().setText("成功"));
        tableLayout.addTab(tableLayout.newTab().setText("失败"));

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_monetary_replace, authStateFragmentSuccess).commit();
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                if(tab.getPosition()==0){
                    transaction.replace(R.id.fl_monetary_replace, authStateFragmentSuccess).commit();
                }else if(tab.getPosition()==1){
                    transaction.replace(R.id.fl_monetary_replace, authStateFragmentFail).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
