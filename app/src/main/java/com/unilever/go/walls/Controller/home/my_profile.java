package com.unilever.go.walls.Controller.home;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.unilever.go.walls.Activity.CometChatActivity;
import com.unilever.go.walls.Controller.SQL.DatabaseHandler;
import com.unilever.go.walls.Controller.home.home;
import com.unilever.go.walls.Controller.intro.login;
import com.unilever.go.walls.R;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class my_profile extends AppCompatActivity {
    android.app.Activity is_this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        TextView skip = findViewById(R.id.skip);
        skip.setText("My Profile");
        skip.setVisibility(View.VISIBLE);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });


    }
}
