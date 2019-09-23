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

public class profile extends AppCompatActivity {
    android.app.Activity is_this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView txtHomeHeaderLocation = findViewById(R.id.txtHomeHeaderLocation);
        txtHomeHeaderLocation.setText(login.dataUser.getGroupName() + " - " + home.stateName);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        TextView txtHomeHeaderGreet2 = findViewById(R.id.txtHomeHeaderGreet2);
        txtHomeHeaderGreet2.setText(login.dataUser.getFullname());
        ImageView imgUserHome = findViewById(R.id.imgUserHome);
        imgUserHome.setImageBitmap(home.imageprofil);
        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                databaseHandler.deleteUser();
                Toast.makeText(profile.this, "Logout", Toast.LENGTH_SHORT).show();

                try{
                    home.is_this.finish();
                }catch (Exception e){}
                Intent intent = new Intent(profile.this, login.class);
                startActivity(intent);
                finish();
                CometChat.logout(new CometChat.CallbackListener<String>() {
                    @Override
                    public void onSuccess(String successMessage) {
                    }
                    @Override
                    public void onError(CometChatException e) {
//                        Toast.makeText(home.this, "Logout Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
