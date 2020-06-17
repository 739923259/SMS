package com.project.myapplicationsms.ui;

import android.Manifest;
import android.content.Intent;

import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseActivity;


public class WelcomeActivity extends BaseActivity {


    @Override
    protected void setLayout() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_welcome);
        requestPermissions(WelcomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void denied() {
                finish();
            }
        });
    }

}
