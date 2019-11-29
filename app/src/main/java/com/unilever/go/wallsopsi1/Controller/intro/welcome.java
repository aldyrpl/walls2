package com.unilever.go.wallsopsi1.Controller.intro;

import com.unilever.go.wallsopsi1.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
//                finish();
                Intent intent = new Intent(is_this, login.class);
                startActivity(intent);
            }
        });

        TextView daftar = findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(is_this, register.class);
                startActivity(intent);
            }
        });


    }
}
