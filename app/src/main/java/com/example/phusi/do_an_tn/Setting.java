package com.example.phusi.do_an_tn;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by phusi on 3/24/2017.
 */

public class Setting  {
    public static final  String GPS = "gps";
    public static final  String LAZE = "laze";
    public static final  String SERCURITY = "sercurity";
    public static final  String LIGHT = "light";
    public static final  String USER = "user";
    public static final String PASSWORD = "pw";
    public static final String IP_ADDRESS = "ip_address";
    public static final String PORT = "port";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String SETTING_FILE = "setting_values";

    public Setting(Context context){
        this.sharedPref = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();
    }


    public void putString(String key, String value){
        this.editor.putString(key,value);
        this.editor.commit();
    }
    public String getString(String key){
        return this.sharedPref.getString(key,null);
    }
    public void putInt(String key, int value){
        this.editor.putInt(key,value);
        this.editor.commit();
    }
    public int getInt(String key){
        return this.sharedPref.getInt(key,0);
    }
    public void putBoolean(String key,boolean value){
        this.editor.putBoolean(key,value);
        this.editor.commit();
    }
    public boolean getBoolean(String key){
        return  this.sharedPref.getBoolean(key,false);
    }
}
