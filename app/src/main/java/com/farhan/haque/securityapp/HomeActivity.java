package com.farhan.haque.securityapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;


public class HomeActivity extends ActionBarActivity {

    private TextView tv1;
    //MixpanelAPI mMixpanel;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefs = getSharedPreferences(Example.PREFS_USER_NAME, MODE_PRIVATE);
        new Example(prefs);

        // Code for backend push notification service "Mixpanel"
 /*       mMixpanel = MixpanelAPI.getInstance(this, "3f84ca2c899cfb4c7ee4e8a08dd40912"); //Mixpanel token number
        MixpanelAPI.People people = mMixpanel.getPeople();
        people.identify("13793"); // Random app id
        people.initPushHandling("790072665483");// Google project number
*/
        // Textview taking "name" from the Login page
        tv1= (TextView) findViewById(R.id.textView6);
      //  SharedPreferences prefs = getSharedPreferences(Example.PREFS_USER_NAME, MODE_PRIVATE);
       // String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        tv1.setText("Hello "+Example.mobileUserName +"!");

    }

    public void emergency(View v){
        Intent i = new Intent(HomeActivity.this,Emergency.class);
        startActivity(i);
    }

    public void contactUs(View v){
        Intent i = new Intent(HomeActivity.this,ContactUs.class);
        startActivity(i);
    }
    public void escape(View v){
        Intent i = new Intent(HomeActivity.this,Escape.class);
        startActivity(i);
    }
    public void message(View v){
        Intent i = new Intent(HomeActivity.this,Message.class);
        startActivity(i);
    }
    public void information(View v){
        Intent i = new Intent(HomeActivity.this,Information.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    public void onDestroy() {
//        mMixpanel.flush();  // Flushing acquired data by Mixpanel
        super.onDestroy();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
