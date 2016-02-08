package com.farhan.haque.securityapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class HomeActivity extends ActionBarActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv= (TextView) findViewById(R.id.textView);
        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    public void call(View v){
        Uri number = Uri.parse("tel:2108711544");
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);
    }

    public void emergency(View v){
        Intent i = new Intent(HomeActivity.this,Emergency.class);
        startActivity(i);
    }

    public void contactUs(View v){
        Intent i = new Intent(HomeActivity.this,ContactUs.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
