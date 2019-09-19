package com.inscripts.cometchatpulse.demo.Controller;

import com.inscripts.cometchatpulse.demo.Controller.intro.intro;
import com.inscripts.cometchatpulse.demo.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    android.app.Activity is_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        is_this = this;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(is_this, intro.class);
                startActivity(intent);
                finish();
            }
        }, 2200);
    }
}
