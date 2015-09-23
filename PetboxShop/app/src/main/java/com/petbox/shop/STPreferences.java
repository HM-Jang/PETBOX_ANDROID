package com.petbox.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.petbox.shop.DB.Constants;

/**
 * Created by petbox on 2015-09-22.
 */

//싱글톤 SharedPreferences
public class STPreferences {

    private static SharedPreferences pref;

    public static SharedPreferences getPref(Context context){
        if(pref == null){
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return pref;
    }

    //삽입
    public static void putString(String key, String value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //삭제
    public static void deleteValue(String key){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    //검색
    public static boolean isExist(String key){
        String callValue = pref.getString(key, "null");

        if(callValue == "null")
            return false;

        return true;
    }

    public static String getString(String key){
        String str = pref.getString(key, "null");
        return str;
    }



}
