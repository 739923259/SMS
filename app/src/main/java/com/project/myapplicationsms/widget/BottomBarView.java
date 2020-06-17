package com.project.myapplicationsms.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.myapplicationsms.R;


/**
 * Create by xuqunxing on  2019/3/21
 */
public class BottomBarView extends RelativeLayout implements View.OnClickListener {

    private LinearLayout firstLL;
    private LinearLayout secondLL;
    private LinearLayout thirdLL;
    private LinearLayout fourLL;

    private ImageView firstIV;
    private ImageView secondIV;
    private ImageView thirdIV;
    private ImageView foureIV;

    private TextView firstTv;
    private TextView sencondTv;
    private TextView thirdTv;
    private TextView foureTv;

    public static int FIRST = 0;
    public static int SECODE = 1;
    public static int THIRD = 2;
    public static int FOUR = 3;


    public BottomBarView(Context context) {
        this(context, null);
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initListener();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_main_bottom_bar, this);
        firstLL = findViewById(R.id.app_bottombar_ll1);
        secondLL = findViewById(R.id.app_bottombar_ll2);
        thirdLL = findViewById(R.id.app_bottombar_ll3);
        fourLL = findViewById(R.id.app_bottombar_ll4);


        firstIV = findViewById(R.id.app_bottombar_iv1);
        secondIV= findViewById(R.id.app_bottombar_iv2);
        thirdIV = findViewById(R.id.app_bottombar_iv3);
        foureIV = findViewById(R.id.app_bottombar_iv4);


        firstTv = findViewById(R.id.app_bottombar_tv1);
        sencondTv = findViewById(R.id.app_bottombar_tv2);
        thirdTv = findViewById(R.id.app_bottombar_tv3);
        foureTv = findViewById(R.id.app_bottombar_tv4);

    }

    private void initListener() {
        firstLL.setOnClickListener(this);
        secondLL.setOnClickListener(this);
        thirdLL.setOnClickListener(this);
        fourLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.isPressed()){
            if (v == firstLL) {
                onSelectPos(FIRST);
            } else if (v == secondLL) {
                onSelectPos(SECODE);
            } else if (v == thirdLL) {
                onSelectPos(THIRD);
            } else if (v == fourLL) {
                onSelectPos(FOUR);
            }
        }
    }

    public void onSelectPos(int pos) {
        firstIV.setBackgroundResource(R.drawable.point_unselect);
        secondIV.setBackgroundResource(R.drawable.app_customer_manager_unselect);
        thirdIV.setBackgroundResource(R.drawable.app_red_package_unselect);
        foureIV.setBackgroundResource(R.drawable.app_find_unselect);


        firstTv.setTextColor(Color.parseColor("#8B8B8B"));
        sencondTv.setTextColor(Color.parseColor("#8B8B8B"));
        thirdTv.setTextColor(Color.parseColor("#8B8B8B"));
        foureTv.setTextColor(Color.parseColor("#8B8B8B"));


        if (pos == FIRST) {
            firstIV.setBackgroundResource(R.drawable.point_select);
            firstTv.setTextColor(Color.parseColor("#03A9F4"));
        } else if (pos == SECODE) {
            secondIV.setBackgroundResource(R.drawable.app_customer_manager_select);
            sencondTv.setTextColor(Color.parseColor("#03A9F4"));
        } else if (pos == THIRD) {
            thirdIV.setBackgroundResource(R.drawable.app_red_package_selected);
            thirdTv.setTextColor(Color.parseColor("#03A9F4"));
        } else if (pos == FOUR) {
            foureIV.setBackgroundResource(R.drawable.app_find_selected);
            foureTv.setTextColor(Color.parseColor("#03A9F4"));
        }
        if (onViewClickListener != null) {
            onViewClickListener.onViewClick(pos);
        }
    }

    private onViewClickListener onViewClickListener;

    public void setOnViewClickListener(BottomBarView.onViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public void setSelectPos(int selectPos) {
        onSelectPos(selectPos);
    }

    public interface onViewClickListener {
        void onViewClick(int pos);
    }
}
