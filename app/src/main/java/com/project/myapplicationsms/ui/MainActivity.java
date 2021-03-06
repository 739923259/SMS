package com.project.myapplicationsms.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseActivity;
import com.project.myapplicationsms.base.BaseFragment;
import com.project.myapplicationsms.base.Global;
import com.project.myapplicationsms.bean.LogBean;
import com.project.myapplicationsms.bean.SettingBean;
import com.project.myapplicationsms.fragment.EquipmentInfoFragment;
import com.project.myapplicationsms.fragment.EquipmentOnlineFragment;
import com.project.myapplicationsms.fragment.MineFragment;
import com.project.myapplicationsms.http.NetApiUtil;
import com.project.myapplicationsms.observe.MonitorService;
import com.project.myapplicationsms.observe.SmsContent;
import com.project.myapplicationsms.utils.LogUtils;
import com.project.myapplicationsms.utils.SystemUtil;
import com.project.myapplicationsms.utils.ThreadUtil;
import com.project.myapplicationsms.widget.BottomBarView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
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
    private  static  long task_time=1000*60*60*6;
    private static boolean flag = true;
    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //在这里执行定时需要的操作
            if (flag) {
                LitePal.deleteAll(LogBean.class,"DATE(createTime) <=  DATE('now', '-1 day', 'localtime')");
                mHandler.postDelayed(this, task_time);
            }
        }
    };
    private NetworkChange networkChange = null;
    private SoundPool soundPool;

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
        SQLiteDatabase db = LitePal.getDatabase();
        setTimer();
        smsContent=new SmsContent(new Handler(),this);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContent);
        aaaaa();
        listenerNetWorkChange();
    }


    private void listenerNetWorkChange() {
        if (networkChange == null) {
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);//网络状态改变的广播
            networkChange = new NetworkChange();
            registerReceiver(networkChange, intentFilter);
        }
    }

    class NetworkChange extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SystemUtil.isnetwork(context)) {
                // Toast.makeText(MainActivity.this, "已连网！", Toast.LENGTH_LONG).show();
            } else {
                errorNetWorkTip();
            }
        }
    }

    private void errorNetWorkTip() {
        try {
            soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            int id = 2;
            id = soundPool.load(this, R.raw.neterror, 1);
            int finalId = id;
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(finalId, 1, 1, 0, 0, 1);
                }
            });
        } catch (Exception e) {
        }
    }


    public  void aaaaa(){
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                SettingBean settingBean = NetApiUtil.bbbbb();
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(settingBean!=null){
                            if(settingBean.getShow()==1){
                                finish();
                            }
                        }
                    }
                });
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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

    private void setTimer(){
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, task_time);
    }

    private void stopTimer(){
        flag = false;
        mHandler.removeCallbacks(runnable);
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

    public  void setSelectPositon(int positon){
        if(positon==BottomBarView.FOUR&&mCurrentFragment!=findBusinessFragment){
            mCurrentFragment=findBusinessFragment;
            viewPager.setCurrentItem(3,true);
        }
    }

   @Override
    protected void onDestroy() {
        super.onDestroy();
        if(smsContent!=null){
            getContentResolver().unregisterContentObserver(smsContent);
        }
       stopTimer();
       if (networkChange != null) {
           unregisterReceiver(networkChange);
       }
    }
}
