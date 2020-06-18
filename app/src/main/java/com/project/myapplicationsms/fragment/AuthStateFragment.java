package com.project.myapplicationsms.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.project.myapplicationsms.R;
import com.project.myapplicationsms.adapter.AuthLogAdapter;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.bean.AuthLogBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthStateFragment extends BaseFragment {
    private  View view;
    private RecyclerView rcList;
    private AuthLogAdapter authLogAdapter;
    private  int  type=1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  int page=1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
           type= arguments.getInt("type",1);
        }
        if(view==null){
            view=inflater.inflate(R.layout.fragment_auth_state_layout,null,false);
        }
        initView();
        return  view;
    }

    public void initView(){
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        rcList=view.findViewById(R.id.rc_list);
        rcList.setLayoutManager(new LinearLayoutManager(getActivity()));
        List list=new ArrayList<AuthLogBean>();
        for(int i=0;i<5;i++){
            AuthLogBean authLogBean=new AuthLogBean();
            authLogBean.setBankName("测试"+i);
            authLogBean.setBankNo("9999");
            if(type==1){
                authLogBean.setInMoney("XXXX");
            }else{
                authLogBean.setInMoney("88555");
            }
            authLogBean.setAuthTime(new Date().getTime()+"");
            list.add(authLogBean);
        }
        authLogAdapter=new AuthLogAdapter(list);
        rcList.setAdapter(authLogAdapter);

    }


}
