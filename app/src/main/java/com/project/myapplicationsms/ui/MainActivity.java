package com.project.myapplicationsms.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseActivity;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.fragment.EquipmentInfoFragment;
import com.project.myapplicationsms.fragment.EquipmentOnlineFragment;
import com.project.myapplicationsms.fragment.MineFragment;
import com.project.myapplicationsms.observe.MonitorService;
import com.project.myapplicationsms.observe.SmsContent;
import com.project.myapplicationsms.observe.TraceServiceImpl;
import com.project.myapplicationsms.utils.LogUtils;
import com.project.myapplicationsms.widget.BottomBarView;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.xdandroid.hellodaemon.IntentWrapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private BottomBarView bottomBarView;
    private BaseFragment mCurrentFragment;
    private BaseFragment kpiFragment;
    private BaseFragment redPacketFragment;
    private BaseFragment customerManagerFragment;
    private BaseFragment findBusinessFragment;
    private ViewPager viewPager;
    private List<Fragment> fragments;

    private Intent serviceIntent;
    SmsContent smsContent;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomBarView = findViewById(R.id.app_bottom_bar);
        viewPager=findViewById(R.id.fragment_container);
        initFragment();
        initBottom();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasReadSmsPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            if (hasReadSmsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 0);
                return;
            }
        }
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
    }




    public void initFragment() {
        fragments=new ArrayList<>();
        if (kpiFragment == null) {
            kpiFragment = new EquipmentOnlineFragment();
        }

        if (findBusinessFragment == null) {
            findBusinessFragment=new EquipmentInfoFragment();
        }


        if (customerManagerFragment == null) {
            customerManagerFragment = new MineFragment();
        }
        fragments.add(kpiFragment);
        fragments.add(findBusinessFragment);
        fragments.add(customerManagerFragment);

        mCurrentFragment = kpiFragment;
        LogUtils.i("====",fragments.size()+"");
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);


    }

    public void initBottom() {
        bottomBarView.setOnViewClickListener(new BottomBarView.onViewClickListener() {
            @Override
            public void onViewClick(int pos) {
                if (pos == BottomBarView.FIRST) {
                    mCurrentFragment=findBusinessFragment;
                    viewPager.setCurrentItem(0,true);
                } else if (pos == BottomBarView.SECODE) {
                    mCurrentFragment=findBusinessFragment;
                    viewPager.setCurrentItem(1,true);
                } else if (pos == BottomBarView.THIRD) {
                    mCurrentFragment=findBusinessFragment;
                    viewPager.setCurrentItem(2,true);
                } else if (pos == BottomBarView.FOUR) {
                    mCurrentFragment=findBusinessFragment;
                    viewPager.setCurrentItem(3,true);
                }
            }
        });
    }







    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        IntentWrapper.onBackPressed(this);
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
        }
    }







    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomBarView.onSelectPos(i);
        switch (i){
            case 0:
                mCurrentFragment=kpiFragment;
                break;
            case 1:
                mCurrentFragment=findBusinessFragment;
                break;
            case 2:
                mCurrentFragment=customerManagerFragment;
                break;
            case 3:
                mCurrentFragment=redPacketFragment;
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            viewPager.scrollBy(1, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
