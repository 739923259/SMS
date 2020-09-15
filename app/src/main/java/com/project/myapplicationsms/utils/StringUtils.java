package com.project.myapplicationsms.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String parseBankName(String input) {
        try {
            input=input.trim();
            Pattern pattern = Pattern.compile("【(.*?)】|\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(input);
            ArrayList<String> list=new ArrayList();
            while (matcher.find()) {
                list.add(matcher.group());
            }
            for(int i=0;i<list.size();i++){
                if(input.startsWith(list.get(i))||input.endsWith(list.get(i))){
                   return list.get(i).replace("【", "").replace("】", "").replace("[", "").replace("]", "");
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static String parseInMoney(String input) {
        try {
            String bankName=parseBankName(input);
            int indexFlag=-1;
            if(bankName.equals("工商银行")){
                input=input.replaceAll("\\(.*\\)", "");//去除括号备注
                indexFlag=input.indexOf("对方户名");
                if(indexFlag>0){
                    input=input.substring(0,indexFlag);
                }
            }else if(bankName.equals("兴业银行")){
                indexFlag=input.indexOf("对方户名");
                if(indexFlag>0){
                    input=input.substring(0,indexFlag);
                }
            }else if(bankName.equals("光大银行")){
                indexFlag=input.indexOf("摘要");
                if(indexFlag>0){
                    input=input.substring(0,indexFlag);
                }
            }else if(bankName.equals("交通银行")){
                indexFlag=input.indexOf("附言");
                if(indexFlag>0){
                    input=input.substring(0,indexFlag);
                }
            }else if(bankName.equals("招商银行")){
                indexFlag=input.indexOf("备注");
                if(indexFlag>0){
                    input=input.substring(0,indexFlag);
                }
            }else if(bankName.equals("民生银行")){
                indexFlag=input.indexOf("余额");
                if(indexFlag>0){
                    input=input.substring(0,indexFlag);
                }
            }



            input=input.replaceAll(",(?=(\\d{3},?)+(?:\\.\\d{1,3})?)","@#");
            List<String> result = Arrays.asList(input.split("，|,"));
            if (result != null) {
                List list = new ArrayList();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).indexOf("存入") >= 0 ||
                        result.get(i).indexOf("提现") >= 0 ||
                        result.get(i).indexOf("转入") >= 0 ||
                        result.get(i).indexOf("收入") >= 0 ||
                        result.get(i).indexOf("收款") >= 0 ||
                        result.get(i).indexOf("来账") >= 0 ||
                        result.get(i).indexOf("转存") >=0) {
                        input=result.get(i);
                        input = input.replaceAll("@#", "");
                       Pattern pattern = Pattern.compile("[\\*0-9\\.]+");
                        Matcher matcher = pattern.matcher(input);
                        while (matcher.find()) {
                            list.add(matcher.group());
                        }
                    }
                }
                return (String) list.get(list.size() - 1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static String parseBankLastFour(String input) {
        try {
            int beginIndex = input.indexOf("尾号");
            int endIndex = input.indexOf("尾号");
            String zhanghao = null;
            if (beginIndex >= 0) {
                zhanghao = input.substring(beginIndex + 2, endIndex + 7);
            } else {
                beginIndex = input.indexOf("账户");
                endIndex = input.indexOf("账户");
                zhanghao = input.substring(beginIndex + 2, endIndex + 7);
            }
            zhanghao = zhanghao.replace("*", "");
            Pattern pattern = Pattern.compile("[\\*0-9\\.]+");
            Matcher matcher = pattern.matcher(input);
            String reg = "[\u4e00-\u9fa5]";
            Pattern pat = Pattern.compile(reg);
            Matcher m = pat.matcher(zhanghao);
            zhanghao = m.replaceAll("");
            return zhanghao;
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean isNumber(String string) {
        if (string == null)
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(string).matches();
    }

}
