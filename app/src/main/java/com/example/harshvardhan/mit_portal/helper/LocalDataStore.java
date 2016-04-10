package com.example.harshvardhan.mit_portal.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.harshvardhan.mit_portal.R;


public class LocalDataStore {

    private static SharedPreferences getSharedPreferences(AppCompatActivity activity){
        return activity.getSharedPreferences(activity.getResources().getString(R.string.preferences), Context.MODE_PRIVATE);
    }

    public static void store(AppCompatActivity activity,String key,String value){
        SharedPreferences sharedPref=getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String retrieve(AppCompatActivity activity,String key){
        return getSharedPreferences(activity).getString(key,"");
    }

}
