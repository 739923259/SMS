package com.project.myapplicationsms;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /*


您尾号7293的储蓄卡6月12日7时49分支付宝提现收入人民币500.09元,活期余额611.33元。[建设银行]


【中国农业银行】您尾号1977账户06月12日15:02完成代付交易人民币500.54，余额3803.82。


尾号6965账户14:46存入300.64元，余额2127.53元，摘要:王祺禛支付宝转账 王祺禛支付宝转账。[光大银行]


【邮储银行】20年06月12日16:15您尾号019账户提现金额250.46元，余额2458.07元。

    */

    @Test
    public void useAppContext() {

        Pattern pattern = Pattern.compile("[\\*0-9\\.:]+");
        Matcher matcher = pattern.matcher("【华夏银行】您的华夏卡（**6999），05月29日11:03到账人民币0.34元，银联代付，余额12.86元");
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);

        }
    }
}