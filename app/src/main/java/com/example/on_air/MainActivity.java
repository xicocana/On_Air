package com.example.on_air;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        ConstraintLayout mlo = (ConstraintLayout) findViewById(R.id.banana);

        Switch butanu = (Switch) findViewById(R.id.switch2);
        Boolean switchState = butanu.isChecked();

        if(pref.getBoolean("value", false)){
            butanu.setChecked(true);
            mlo.setBackgroundResource(R.drawable.on);
        }

        butanu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                    mlo.setBackgroundResource(R.drawable.on);
                    new blahblah().execute("on");

                    editor.putBoolean("value", true);
                    editor.commit();

                } else {
                    //Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                    mlo.setBackgroundResource(R.drawable.off);
                    new blahblah().execute("off");

                    editor.putBoolean("value", false);
                    editor.commit();
                }
            } });
    }
}

class blahblah extends AsyncTask <String, Void, Void>{

    @Override
    protected Void doInBackground(String... inta) {
        System.out.println("VALUE SET TO : " + inta[0]);
        try {
            String url;
            if(inta[0].equals("on")){
                url = "http://192.168.1.152/LED_BUILTIN_on?";
            }else{
                url = "http://192.168.1.152/LED_BUILTIN_off?";
            }

            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

            // optional default is GET
            httpClient.setRequestMethod("GET");

            //add request header
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        }
        catch (MalformedURLException e) {
        }
        catch (IOException e) {
        }
        return null;
    }
}
