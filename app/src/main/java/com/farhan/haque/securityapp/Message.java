package com.farhan.haque.securityapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Message extends ActionBarActivity {

    EditText name,email,phone,age,insuranceNumber,allergy,emergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        name= (EditText) findViewById(R.id.editText8);
        email= (EditText) findViewById(R.id.editText9);
        phone= (EditText) findViewById(R.id.editText10);
        age= (EditText) findViewById(R.id.editText11);
        insuranceNumber= (EditText) findViewById(R.id.editText12);
        allergy= (EditText) findViewById(R.id.editText13);
        emergency= (EditText) findViewById(R.id.editText14);
        name.setText(Example.mobileUserName.toString());
        email.setText(Example.mobileUserEmail.toString());
        phone.setText(Example.mobileUserPhone.toString());
        //age.setText("34");
        //insuranceNumber.setText("#123456789");
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                name.setText("Name: ");
                return false;
            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                email.setText("Email: ");
                return false;
            }
        });
        phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                phone.setText("Phone: ");
                return false;
            }
        });
        age.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                age.setText("Age: ");
                return false;
            }
        });
        insuranceNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                insuranceNumber.setText("Insurance Number: ");
                return false;
            }
        });
        allergy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                allergy.setText("Allergies: ");
                return false;
            }
        });
        emergency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                emergency.setText("Medical Conditions: ");
                return false;
            }
        });

    }
    public void update(View v){
        Toast.makeText(Message.this,"Updating Patient Database",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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
