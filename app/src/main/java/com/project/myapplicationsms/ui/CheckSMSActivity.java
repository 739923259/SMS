package com.project.myapplicationsms.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.myapplicationsms.R;
import com.project.myapplicationsms.base.BaseActivity;
import com.project.myapplicationsms.utils.StringUtils;

public class CheckSMSActivity extends BaseActivity implements  View.OnClickListener {
    private RelativeLayout rlBack;
    private TextView tvTitle;

    private EditText etUrl;
    private  TextView etSign;
    private  TextView tvSubmit;
    private  TextView tvClear;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_check_sms);
        rlBack=findViewById(R.id.common_back_rl);
        rlBack.setOnClickListener(this);
        tvTitle=findViewById(R.id.common_title_tv);
        tvTitle.setText("检验短信");
        etUrl=findViewById(R.id.et_url);
        etSign=findViewById(R.id.et_sign);
        tvSubmit=findViewById(R.id.tv_submit);
        tvClear=findViewById(R.id.tv_clear);
        tvClear.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_submit){
            setResult();
        }else if(v.getId()==R.id.tv_clear){
            etSign.setText("");
            etUrl.setText("");
        }else  if(v.getId()==R.id.common_back_rl){
            finish();
        }
    }
    public  void setResult() {
        String input=etUrl.getText().toString();
        if(!TextUtils.isEmpty(input)){
            String bankName= StringUtils.parseBankName(input);
            String money=StringUtils.parseInMoney(input);
            String cardNo=StringUtils.parseBankLastFour(input);
            String result="银行："+bankName+"\n"+"卡号："+cardNo+"\n"+"金额："+money;
            etSign.setText(result);
        }else{
            etSign.setText("");
        }
    }
}
