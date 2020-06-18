package com.project.myapplicationsms.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class StringUtils {
    public  static  String parseBankName(String input){
        try {
            Pattern pattern = Pattern.compile("【(.*?)】|\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                return  matcher.group().replace("【","").replace("】","").replace("[","").replace("]","");
            }
            return  null;
        }catch ( Exception e){
            return  null;
        }
    }


    public  static  String parseInMoney(String input){
        try {
            Pattern pattern = Pattern.compile("[\\*0-9\\.]+");
            Matcher matcher = pattern.matcher(input);
            List list=new ArrayList();
            while (matcher.find()) {
                list.add(matcher.group());
            }
            if(list.size()>0){
                return (String) list.get(list.size()-2);
            }
        }catch (Exception e){

        }
        return  null;
    }

    public  static  String parseBankLastFour(String input){
            try {
                String zhanghao=input.substring(input.indexOf("尾号")+2,input.indexOf("尾号")+6);
                Pattern pattern = Pattern.compile("[\\*0-9\\.]+");
                Matcher matcher = pattern.matcher(input);
                String reg = "[\u4e00-\u9fa5]";
                Pattern pat = Pattern.compile(reg);
                Matcher m=pat.matcher(zhanghao);
                zhanghao=m.replaceAll("");
                return  zhanghao;
            }catch (Exception e){
                return  null;
            }

    }

}
