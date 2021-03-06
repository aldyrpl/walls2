package com.unilever.go.wallsopsi1.Controller.home;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.wallsopsi1.Activity.CometChatActivity;
import com.unilever.go.wallsopsi1.Contracts.StringContract;
import com.unilever.go.wallsopsi1.Controller.SQL.DatabaseHandler;
import com.unilever.go.wallsopsi1.Controller.home.gallery.gallery;
import com.unilever.go.wallsopsi1.Controller.home.remindme.MainActivity;
import com.unilever.go.wallsopsi1.Controller.intro.login;
import com.unilever.go.wallsopsi1.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class profile extends AppCompatActivity {
    android.app.Activity is_this;
    Bitmap bitmapimage = null;
    ImageView imgUserHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        is_this  = this;
        setContentView(R.layout.profile);
        TextView txtHomeHeaderLocation = findViewById(R.id.txtHomeHeaderLocation);
        txtHomeHeaderLocation.setText(login.dataUser.getGroupName() + " - " + home.stateName);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        TextView txtHomeHeaderGreet2 = findViewById(R.id.txtHomeHeaderGreet2);
        txtHomeHeaderGreet2.setText(login.dataUser.getFullname());
        imgUserHome = findViewById(R.id.imgUserHome);

        TextView contact_us = findViewById(R.id.aboutus);
        contact_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(profile.this, about_us.class);
                startActivity(intent);
            }
        });

        TextView myprofile = findViewById(R.id.my_profile);
        myprofile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(profile.this, my_profile.class);
                startActivity(intent);
            }
        });

        ImageView imgHomeMessage = findViewById(R.id.imgHomeMessage);
        imgHomeMessage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(profile.this, CometChatActivity.class));
                finish();
            }
        });

        ImageView imgHomeCalendar = findViewById(R.id.imgHomeCalendar);
        imgHomeCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView imgHomeCamera = findViewById(R.id.imgHomeCamera);
        imgHomeCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, gallery.class);
                startActivity(intent);
                finish();
            }
        });

        if(home.imageprofil != null) {
            imgUserHome.setImageBitmap(home.imageprofil);
        }else if(my_profile.isChange == true){
            new Thread(new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgUserHome.setImageBitmap(my_profile.bitmapProfil);
                        }
                    });

                }
            }).start();
        }
        else{

            new Thread(new Runnable() {
                public void run() {
                    URL url = null;
                    try {
                        url = new URL(login.dataUser.getImg());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {
                        bitmapimage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgUserHome.setImageBitmap(bitmapimage);
                        }
                    });

                }
            }).start();
        }
        TextView about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(profile.this, termscondition.class);
                startActivity(intent);
            }
        });
        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    databaseHandler.deleteUser();
                    Toast.makeText(profile.this, "Logout", Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
                try{
                    home.is_this.finish();
                }catch (Exception e){}
                Intent intent = new Intent(profile.this, login.class);
                startActivity(intent);
                finish();
                try {
                    try {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_USER + "_" + login.dataUser.getId());
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_GROUP + "_" + login.dataUser.getIdUserGroup());
                    }catch (Exception e){
                        Log.d("unsubscripe nih ", e.getMessage());
                    }

                    CometChat.logout(new CometChat.CallbackListener<String>() {
                        @Override
                        public void onSuccess(String successMessage) {
                        }

                        @Override
                        public void onError(CometChatException e) {
//                        Toast.makeText(home.this, "Logout Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch(Exception e){

                }
            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();

        if(my_profile.isChange == true){
            new Thread(new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgUserHome.setImageBitmap(my_profile.bitmapProfil);
                        }
                    });

                }
            }).start();
        }
    }
}
