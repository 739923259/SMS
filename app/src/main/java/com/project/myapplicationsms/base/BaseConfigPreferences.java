package com.project.myapplicationsms.base;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Create by xuqunxing on  2019/3/14
 */
public class BaseConfigPreferences {
    public static final String NAME = "dpzxsp";
    private static SharedPreferences baseSP;
    private static BaseConfigPreferences baseConfig;
    public static final String APP_JPUSH_UUID = "app_jpush_uuid";

    protected BaseConfigPreferences(Context context) {
        baseSP = context.getSharedPreferences(NAME, 4);
    }

    public static BaseConfigPreferences getInstance(Context context) {
        if (null == baseConfig) {
            baseConfig = new BaseConfigPreferences(context);
        }

        return baseConfig;
    }

    public SharedPreferences getBaseSP() {
        return baseSP;
    }

    //------------------------------------------------------
    public static final String LOGIN_ACCOUNT = "login_account";
    public static final String APP_GUIDE_SHOW = "app_guide_show";
    public static final String APP_USER_TOKEN = "app_user_token";
    public static final String APP_USER_VOICE = "app_user_voice";
    public static final String APP_USER_SET_VOICE = "app_set_user_voice";

    //------------------------------------------------------
    public void setLoginAccount(String count) {
        baseSP.edit().putString(LOGIN_ACCOUNT, count).commit();
    }

    public String getLoginAccount() {
        return baseSP.getString(LOGIN_ACCOUNT, (String) null);
    }

    public void setAppGuideShow(boolean hasShow) {
        baseSP.edit().putBoolean(APP_GUIDE_SHOW, hasShow).commit();
    }

    public boolean getAppGuideShow() {
        return baseSP.getBoolean(APP_GUIDE_SHOW, false);
    }

    public void setUserToken(String token) {
        baseSP.edit().putString(APP_USER_TOKEN, token).commit();
    }

    public String getUserToken() {
        return baseSP.getString(APP_USER_TOKEN, (String) null);
    }

    public void setAppJpushUUId(int uuid) {
        baseSP.edit().putInt(APP_JPUSH_UUID, uuid).commit();
    }

    public int getAppJpushUUId() {
        return baseSP.getInt(APP_JPUSH_UUID, -1);
    }

    public void setHasSetVocie(boolean voice) {
        baseSP.edit().putBoolean(APP_USER_SET_VOICE, voice).commit();
    }

    public boolean getHasSetVocie() {
        return baseSP.getBoolean(APP_USER_SET_VOICE, false);
    }


    public void setVoice(boolean voice) {
        baseSP.edit().putBoolean(APP_USER_VOICE, voice).commit();
    }

    public boolean getVoice() {
        return baseSP.getBoolean(APP_USER_VOICE, false);
    }


    //------------------------------------------------------
    public static final String UPDATE_FILE_INFO = "update_file_info";
    public static final String UPDATE_IMG_INFO = "update_img_info";
    public static final String UPDATE_LAST_TIME = "update_last_time";
    public static final String UPDATE_BASE_INFO = "update_base_info";
    public static final String ROOT_URL = "root_url";
    public static final String FILE_LIST = "file_list";

    //------------------------------------------------------

    public void removeParamsByKey(String key) {
        baseSP.edit().remove(key).commit();
    }

    public void setParamsKeyAndValue(String key, String value) {
        baseSP.edit().putString(key, value).commit();
    }

    public String getParamsKeyAndValue(String key) {
        return baseSP.getString(key, null);
    }

    public void setParamsKeyAndValueInt(String key, int value) {
        baseSP.edit().putInt(key, value).commit();
    }

    public int getParamsKeyAndValueInt(String key) {
        return baseSP.getInt(key, 0);
    }


    public static void putHashMapData(Context context, String key, Map<String, String> datas) {
        JSONArray mJsonArray = new JSONArray();
        Iterator<Map.Entry<String, String>> iterator = datas.entrySet().iterator();
        JSONObject object = new JSONObject();
        if(datas!=null&&datas.size()>=100){
            List<Map.Entry<String, String>> lists=new ArrayList<Map.Entry<String, String>>(datas.entrySet());
            if(lists!=null&&lists.size()>0){
                datas.remove(lists.get(lists.size()-1).getKey());
            }
        }
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {

            }
        }
        mJsonArray.put(object);
        SharedPreferences.Editor editor = baseSP.edit();
        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }

    public static Map<String, String> getHashMapData(Context context, String key) {
        Map<String, String> datas = new HashMap<>();
        String result = baseSP.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        datas.put(name, value);
                    }
                }
            }
        } catch (JSONException e) {

        }

        return datas;
    }

}
