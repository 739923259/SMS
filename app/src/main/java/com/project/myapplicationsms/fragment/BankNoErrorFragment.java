package com.project.myapplicationsms.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.myapplicationsms.R;
import com.project.myapplicationsms.adapter.AuthLogAdapter;
import com.project.myapplicationsms.adapter.BankErrorAdapter;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.bean.LogBean;
import com.project.myapplicationsms.utils.BaseConfigPreferences;
import com.project.myapplicationsms.utils.SystemUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class BankNoErrorFragment extends BaseFragment {
    private  View view;
    private RecyclerView rcList;
    private BankErrorAdapter bankErrorAdapter;
    private  int  type=3;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  int page=1;
    private List<LogBean> logBeanList=new ArrayList<>();
    private  int rows=30;
    private  boolean hasNext=true;
    private MyBroadCastReceiver myBroadCastReceiver;


    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                int type=intent.getIntExtra("refresh",-1);
                if(type==3){
                    initData(0);
                }
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type= arguments.getInt("type",3);
        }
        if(view==null){
            view=inflater.inflate(R.layout.fragment_bankno_error_layout,null,false);
            myBroadCastReceiver = new MyBroadCastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.pateo.mybroadcast.bank.error");
        getActivity().registerReceiver(myBroadCastReceiver,intentFilter);
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
        bankErrorAdapter=new BankErrorAdapter(logBeanList);
        setEmptyView("暂无记录");
        setLoadMoreListner();
        rcList.setAdapter(bankErrorAdapter);
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
        bankErrorAdapter.setEmptyView(emptyView);
    }

    public void setLoadMoreListner(){
        bankErrorAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(hasNext){
                    page++;
                    initData(1);
                }else{//说明没有下一页数据了
                    bankErrorAdapter.loadMoreEnd();
                }
            }
        },rcList);
    }


    public void initData(int freshType){
        if(TextUtils.isEmpty(BaseConfigPreferences.getInstance(getActivity()).getLoginSigin())){
            return;
        }
        List<LogBean> logBeans=new ArrayList<>();
        if(type==3){
            logBeans = LitePal
                    .where("authSate==?", "3")
                    .order("createTime desc").limit(rows).offset((page-1)*rows).find(LogBean.class);
        }

        if(logBeans!=null&&logBeans.size()<rows){
            hasNext=false;
        }else{
            hasNext=true;
        }
        if(logBeans!=null){
            if(freshType==0){
                logBeanList.clear();
                logBeanList.addAll(logBeans);
                bankErrorAdapter.setNewData(logBeanList);
            }else{
                logBeanList.addAll(logBeans);
                bankErrorAdapter.setNewData(logBeanList);
                bankErrorAdapter.loadMoreComplete();
            }
            swipeRefreshLayout.setRefreshing(false);
            bankErrorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(myBroadCastReceiver!=null){
            getActivity().unregisterReceiver(myBroadCastReceiver);
        }
    }


}
