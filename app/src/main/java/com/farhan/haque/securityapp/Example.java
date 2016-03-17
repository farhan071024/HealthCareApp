package com.farhan.haque.securityapp;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by ASUS on 1/27/2016.
 */
public class Example extends ActionBarActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_USER_NAME = "UserName";
    public static  String mobileUserName=null;
    public static  String mobileUserEmail=null;
    public static  String mobileUserPhone=null;
    public static boolean mobileUserRegisterAsHelper=false;

    public Example(SharedPreferences preferences) {
       // SharedPreferences prefs = getSharedPreferences(Example.PREFS_USER_NAME, MODE_PRIVATE);
        mobileUserName = preferences.getString("name", "No name defined");//"No name defined" is the default value.
        mobileUserEmail=preferences.getString("email", "No email defined");
        mobileUserPhone=preferences.getString("phone", "No phone number defined");
        mobileUserRegisterAsHelper=preferences.getBoolean("registerAsHelper", false);
    }
}
