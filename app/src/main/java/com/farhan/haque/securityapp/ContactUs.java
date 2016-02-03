package com.farhan.haque.securityapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ContactUs extends Activity {
    EditText nameTxt,phoneTxt,emailTxt,appTxt;
    Button contactBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        nameTxt= (EditText) findViewById(R.id.editText3);
        emailTxt= (EditText) findViewById(R.id.editText4);
        phoneTxt= (EditText) findViewById(R.id.editText5);
        appTxt= (EditText) findViewById(R.id.editText6);
        contactBtn= (Button) findViewById(R.id.button6);
    }

    public void contact(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mdfarhanhaque@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"New App Idea From "+nameTxt.getText().toString()+"("+phoneTxt.getText().toString()+
        ","+emailTxt.getText().toString()+")");
        emailIntent.putExtra(Intent.EXTRA_TEXT,appTxt.getText().toString());
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUs.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_us, menu);
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
