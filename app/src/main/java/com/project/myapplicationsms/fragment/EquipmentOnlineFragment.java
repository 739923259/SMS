package com.project.myapplicationsms.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.myapplicationsms.base.BaseFragment;


public class EquipmentOnlineFragment  extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView textView=new TextView(getContext());
        textView.setText("EquipmentOnlineFragment");
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}
