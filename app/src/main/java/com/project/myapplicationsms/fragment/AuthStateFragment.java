package com.project.myapplicationsms.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.project.myapplicationsms.R;
import com.project.myapplicationsms.adapter.AuthLogAdapter;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.bean.AuthLogBean;
import com.project.myapplicationsms.bean.LogBean;

import org.litepal.LitePal;

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
    private  List<LogBean> logBeanList=new ArrayList<>();
    private  int rows=30;
    private  boolean hasNext=true;


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

        initData(0);
        return  view;
    }

    public void initView(){
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                swipeRefreshLayout.setRefreshing(true);
                initData(0);
            }
        });
        rcList=view.findViewById(R.id.rc_list);
        rcList.setLayoutManager(new LinearLayoutManager(getActivity()));
        authLogAdapter=new AuthLogAdapter(logBeanList);
        setEmptyView("暂无记录");
        setLoadMoreListner();
        rcList.setAdapter(authLogAdapter);
    }

    public  void setEmptyView(String str){
        View emptyView=null;
        if(emptyView==null){
            emptyView= LayoutInflater.from(getActivity()).inflate(R.layout.common_data_empty,null);
            emptyView.setBackgroundColor(Color.parseColor("#f7f7f7"));
            ((TextView)emptyView.findViewById(R.id.tv_empty)).setText(str);
        }
        if(emptyView.getParent()!=null){
            ((ViewGroup)emptyView.getParent()).removeAllViews();
        }
        authLogAdapter.setEmptyView(emptyView);
    }

    public void setLoadMoreListner(){
        authLogAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(hasNext){
                    page++;
                    initData(1);
                }else{//说明没有下一页数据了
                    authLogAdapter.loadMoreEnd();
                }
            }
        },rcList);
    }


    public void initData(int freshType){

        List<LogBean> logBeans;
        if(type==1){
            logBeans = LitePal
                    .where("authSate==?", "1")
                    .order("createTime desc").limit(rows).offset((page-1)*rows).find(LogBean.class);
        }else{
            logBeans = LitePal
                    .where("authSate==?", "2")
                    .order("createTime desc").limit(rows).offset((page-1)*rows).find(LogBean.class);
        }
        if(logBeans.size()<rows){
            hasNext=false;
        }else{
            hasNext=true;
        }
        if(freshType==0){
            logBeanList.clear();
            logBeanList.addAll(logBeans);
            authLogAdapter.setNewData(logBeanList);
        }else{
            logBeanList.addAll(logBeans);
            authLogAdapter.setNewData(logBeanList);
            authLogAdapter.loadMoreComplete();
        }
        swipeRefreshLayout.setRefreshing(false);
        authLogAdapter.notifyDataSetChanged();
    }


}
