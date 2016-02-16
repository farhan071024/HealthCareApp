package com.farhan.haque.securityapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    EditText et1,et2,et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1= (EditText)findViewById(R.id.editText);
        et2= (EditText)findViewById(R.id.editText2);
        et3= (EditText) findViewById(R.id.editText7);


        //Using shared Preferences and boolean value to determine the appearence of the page for only first time
        SharedPreferences settings = getSharedPreferences(Example.PREFS_NAME, 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if(hasLoggedIn)
        {
            //Go directly to main activity.
            Intent i  = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(i);
            finish(); // Cleans the activity stack
        }

    }

    public void register(View v){
        String name= et1.getText().toString();
        String email= et2.getText().toString();
        String phone=et3.getText().toString();

        // Gets the IEMI number
        TelephonyManager TM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiNo = TM.getDeviceId();

        // Gets the Telephone number if stored in the SIM
        TelephonyManager manager =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = manager.getLine1Number();

        // Sends the email
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mdfarhanhaque@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Registration Information ");
        if(mPhoneNumber == null) {
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Name:" + name + "\nEmail:" + email + "\nPhone:" + phone + "\nDevice ID:" + imeiNo);
            Toast.makeText(MainActivity.this,"Unable to acquire the phone number...\nEnter valid phone number",Toast.LENGTH_LONG).show();
        }else{
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Name:" + name + "\nEmail:" + email + "\nPhone:" + mPhoneNumber + "\nDevice ID:" + imeiNo);
        }
        try {
            startActivity(Intent.createChooser(emailIntent, "Registering..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

 //User has successfully logged in, save this information
// We need an Editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences(Example.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
        editor.commit();

        // Saves the user information for later usage
        SharedPreferences.Editor editor2 = getSharedPreferences(Example.PREFS_USER_NAME, MODE_PRIVATE).edit();
        editor2.putString("name",name );
        editor2.putString("phone",phone);
        editor2.putString("email",email);
        editor2.commit();
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
