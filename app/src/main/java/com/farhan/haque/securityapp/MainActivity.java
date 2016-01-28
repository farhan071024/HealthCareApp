package com.farhan.haque.securityapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    EditText et1,et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(Example.PREFS_NAME, 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if(hasLoggedIn)
        {
            //Go directly to main activity.
            Intent i  = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(i);
            finish();

        }

    }

    public void register(View v){
        et1= (EditText)findViewById(R.id.editText);
        String name= et1.getText().toString();
        et2= (EditText)findViewById(R.id.editText2);
        String email= et2.getText().toString();

 //User has successfully logged in, save this information
// We need an Editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences(Example.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
        editor.commit();

        TelephonyManager TM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiNo = TM.getDeviceId();
        Toast.makeText(MainActivity.this,imeiNo,Toast.LENGTH_LONG).show();


        Intent i  = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
