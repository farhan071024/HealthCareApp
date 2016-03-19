package com.farhan.haque.securityapp;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
    // Method to save bitmap image to external storage in order to send to email
    public static File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory,"oci"+".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory,"oci"+".png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
