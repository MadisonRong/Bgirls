package com.madisonrong.bgirls2.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MadisonRong on 7/6/16.
 */
public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesHelper() {}

    public static void init(Context context) {
        SharedPreferencesHelper helper = SharedPreferencesHelperHolder.getInstance();
        helper.initSharedPreferences(context);
    }

    public void initSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("bgirls2", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static class SharedPreferencesHelperHolder {
        private static SharedPreferencesHelper instance = new SharedPreferencesHelper();

        public static SharedPreferencesHelper getInstance() {
            return instance;
        }
    }

    public static boolean putString(String key, String value) {
        return SharedPreferencesHelperHolder.getInstance().editor.putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return SharedPreferencesHelperHolder.getInstance().sharedPreferences.getString(key, defaultValue);
    }
}
