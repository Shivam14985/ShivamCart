package com.example.shivamscart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // on below line we are
                // creating a new intent
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);

                // on the below line we are finishing
                // our current activity.
                finish();
            }
        }, 4300);
    }
}