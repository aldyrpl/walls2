package com.unilever.go.walls.Controller;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.unilever.go.walls.Contracts.StringContract;
import com.unilever.go.walls.Controller.SQL.DatabaseHandler;
import com.unilever.go.walls.Controller.SQL.user_model;
import com.unilever.go.walls.Controller.home.home;
import com.unilever.go.walls.Controller.intro.intro;
import com.unilever.go.walls.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    android.app.Activity is_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        setContentView(R.layout.main);
        is_this = this;
        final Handler handler = new Handler();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CometChat.init(MainActivity.this, StringContract.AppDetails.APP_ID,new CometChat.CallbackListener<String>() {

            @Override
            public void onSuccess(String s) {
//                Toast.makeText(home.this, "SetUp Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(CometChatException e) {
//                Toast.makeText(home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d(home.class.getSimpleName(), "onError: "+e.getMessage());
            }

        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(databaseHandler.checkUser()) {

                    try {
                        List<user_model> user = databaseHandler.getUser();
                        Log.d(MainActivity.class.getSimpleName(),"id" + user.get(0).getId());
                        Log.d(MainActivity.class.getSimpleName(),"getId_user_group" + user.get(0).getId_user_group());
                        Log.d(MainActivity.class.getSimpleName(),"getGroup_name" + user.get(0).getGroup_name());
                        Log.d(MainActivity.class.getSimpleName(),"getFullname" + user.get(0).getFullname());
                        Log.d(MainActivity.class.getSimpleName(),"getImg" + user.get(0).getImg());
                        home.dataUser.setId(user.get(0).getId());
                        home.dataUser.setIdUserGroup(user.get(0).getId_user_group());
                        home.dataUser.setGroupName(user.get(0).getGroup_name());
                        home.dataUser.setFullname(user.get(0).getFullname());
                        home.dataUser.setImg(user.get(0).getImg());

                        Intent intent = new Intent(is_this, home.class);
                        startActivity(intent);
                    }catch (Exception e){
                        databaseHandler.deleteUser();
                        Intent intent = new Intent(is_this, intro.class);
                        startActivity(intent);
                    }

                }else{
                    Intent intent = new Intent(is_this, intro.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2200);
    }
}
