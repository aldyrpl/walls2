package com.inscripts.cometchatpulse.demo.Controller.intro;

import com.inscripts.cometchatpulse.demo.Controller.browser.browser;
import com.inscripts.cometchatpulse.demo.Controller.home.home;
import com.inscripts.cometchatpulse.demo.Controller.intro.intro;
import com.inscripts.cometchatpulse.demo.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class welcome extends AppCompatActivity {
    android.app.Activity is_this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        is_this = this;
        TextView masuk = findViewById(R.id.masuk);
//        TextView skip = findViewById(R.id.skip);
        ImageView back = findViewById(R.id.back);

        masuk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
//                FragmentManager fm = getSupportFragmentManager();
//                browser alertDialog = browser.newInstance("Title");
//                alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
////                browser.show(getSupportFragmentManager(), "DialogFragment");
//                alertDialog.show(fm, "fragment_alert");
                finish();
                Intent intent = new Intent(is_this, login.class);
                startActivity(intent);
            }
        });


    }
}
