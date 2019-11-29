package com.unilever.go.wallsopsi1.Controller.home;

import com.unilever.go.wallsopsi1.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class about_us extends AppCompatActivity {
    android.app.Activity is_this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        TextView close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });


    }
}
