package com.example.shehzad.finaltabchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

public class SplashScreen extends Activity {



    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
//       TextView tv = (TextView) findViewById(R.id.textView31);
//         Typeface face = Typeface.createFromAsset(getAssets(), "fonts/queen.ttf");
//        tv.setTypeface(face);





        SharedPreferences sp = getSharedPreferences("My", MODE_PRIVATE);
        final String s = sp.getString("username", "");




        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


//    else
//
//    {
//        new Handler().postDelayed(new Runnable() {
//
//
//            @Override
//            public void run() {
//
//                Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
//    }
//    }

    }
}