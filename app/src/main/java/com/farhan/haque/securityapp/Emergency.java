package com.farhan.haque.securityapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


public class Emergency extends ActionBarActivity implements LocationListener {

    private static final int CAMERA_REQUEST = 1888;
    protected static final int RESULT_SPEECH = 1;

    ImageView mimageView;
    private ImageButton btnSpeak;
    private TextView txtText;
    private Spinner dropdown;
    private LocationManager locationManager;
    private String provider;
    double lat, lng;
    Geocoder geocoder;
    static List<Address> addresses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        mimageView = (ImageView)findViewById(R.id.imageView);
       // mimageView.setImageResource(R.drawable.androidlogo);
        txtText = (EditText)findViewById(R.id.textView3);
        btnSpeak = (ImageButton) findViewById(R.id.imageButton);
        dropdown = (Spinner)findViewById(R.id.spinner);

       // Creates the Spinner or drop down menu
        String[] items = new String[]{"Gun", "Fight", "Fire"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // Gets the device location
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        criteria.setAccuracy(1);
        criteria.setPowerRequirement(2);
        criteria.isCostAllowed();
        criteria.setCostAllowed(true);

        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
           // latituteField.setText("Location not available");
           // longitudeField.setText("Location not available");
            System.out.println("Provider is  " + provider );

        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);

    }
// Takes the picture
    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// deals with camera intent
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            mimageView.setImageBitmap(mphoto);
        }
// deals with voice recognition intent
        if (requestCode == RESULT_SPEECH && resultCode == RESULT_OK && null != data){
            ArrayList<String> text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            txtText.setText(text.get(0));
        }
    }

   // uses voice recognition api to create implicit intent for voice
    public void speech(View v){
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, RESULT_SPEECH);
            txtText.setText("");
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }

    // Updates location information within the activity life cycle
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 20000, 0, this);

    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat =  location.getLatitude();
        lng =  location.getLongitude();

        // Calculates address using Geo-coding
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Background task using async task to send location information
    protected class Background extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            HttpPost httppost = new HttpPost("http://utsasecurity.comxa.com/index.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("lat", String.valueOf(lat)));
            nameValuePairs.add(new BasicNameValuePair("lng", String.valueOf(lng)));
            nameValuePairs.add(new BasicNameValuePair("event",dropdown.getSelectedItem().toString()));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpClient httpclient = new DefaultHttpClient();
            try {
                httpclient.execute(httppost);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(Emergency.this,"This is a big test",Toast.LENGTH_LONG).show();
        }
    }

   // callbacks of Location Listener
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(this, "Enabled new provider " + provider,
               // Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
       // Toast.makeText(this, "Disabled provider " + provider,
                //Toast.LENGTH_SHORT).show();
    }

    // sends an email with image, voice text and location information
    public void send(View v){
        Drawable d =mimageView.getDrawable();
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bitmap = bitDw.getBitmap();
        File mFile = savebitmap(bitmap);

        Uri u = null;
        u = Uri.fromFile(mFile);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String text = dropdown.getSelectedItem().toString();
        if(text.matches("Gun")){
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mdfarhanhaque@gmail.com"});
        }else if(text.matches("Fight||Fire")){
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"farhanusad@gmail.com"});
        } else {
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"farhan071024@gmail.com"});
        }

        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Emergency Alert Message" );
        emailIntent.putExtra(Intent.EXTRA_TEXT, txtText.getText().toString()+"\nLocation:"+ addresses.get(0).getAddressLine(0)+
                ","+addresses.get(0).getLocality()+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName()+
        ","+addresses.get(0).getPostalCode()+","+addresses.get(0).getFeatureName() );
        emailIntent.putExtra(Intent.EXTRA_STREAM, u);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Emergency.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        //Async task to send location information(lat,lng) to php server
        Background bg=new Background();
        bg.execute();
    }
    // Method to save bitmap image to external storage in order to send to email
    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory,   "oci"+".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory,  "oci"+".png");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency, menu);
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
