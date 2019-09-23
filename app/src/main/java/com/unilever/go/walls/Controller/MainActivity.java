package com.unilever.go.walls.Controller;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.walls.Contracts.StringContract;
import com.unilever.go.walls.Controller.Retrofit.LoginAPI;
import com.unilever.go.walls.Controller.Retrofit.loginClassJson;
import com.unilever.go.walls.Controller.SQL.DatabaseHandler;
import com.unilever.go.walls.Controller.SQL.user_model;
import com.unilever.go.walls.Controller.home.home;
import com.unilever.go.walls.Controller.intro.intro;
import com.unilever.go.walls.Controller.intro.login;
import com.unilever.go.walls.Presenters.LoginAcitivityPresenter;
import com.unilever.go.walls.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    android.app.Activity is_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        setContentView(R.layout.main);
        is_this = this;
        final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(databaseHandler.checkUser()) {

//                    try {
                        List<user_model> user = databaseHandler.getUser();
//                        Log.d(MainActivity.class.getSimpleName(),"id" + user.get(0).getId());
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(login.URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    LoginAPI api = retrofit.create(LoginAPI.class);
                    Call<loginClassJson> call = api.login(user.get(0).getEmail(), user.get(0).getPassword());
                    call.enqueue(new Callback<loginClassJson>() {
                        @Override
                        public void onResponse(Call<loginClassJson> call, Response<loginClassJson> response) {
                            String message = response.body().getMessage();
                            boolean status = response.body().getStatus();
                            login.dataUser = response.body().getResult();
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            if(status == true) {
                                Intent intent = new Intent(is_this, home.class);
                                startActivity(intent);
                                finish();

                                FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID+"_"+ CometChatConstants.RECEIVER_TYPE_USER+"_"+login.dataUser.getId());
                                FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID+"_"+CometChatConstants.RECEIVER_TYPE_GROUP+"_"+login.dataUser.getIdUserGroup());
                            }

                        }

                        @Override
                        public void onFailure(Call<loginClassJson> call, Throwable t) {
                        }
                    });

                }else{
                    Intent intent = new Intent(is_this, intro.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2200);
    }
}
