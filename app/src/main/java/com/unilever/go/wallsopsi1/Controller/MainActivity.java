package com.unilever.go.wallsopsi1.Controller;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.wallsopsi1.Contracts.StringContract;
import com.unilever.go.wallsopsi1.Controller.Retrofit.LoginAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.loginClassJson;
import com.unilever.go.wallsopsi1.Controller.SQL.DatabaseHandler;
import com.unilever.go.wallsopsi1.Controller.SQL.user_model;
import com.unilever.go.wallsopsi1.Controller.home.home;
import com.unilever.go.wallsopsi1.Controller.intro.intro;
import com.unilever.go.wallsopsi1.Controller.intro.login;
import com.unilever.go.wallsopsi1.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
    public String fcm_token = "";
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(this);
        setContentView(R.layout.main);
        is_this = this;
        final Handler handler = new Handler();
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Connecting...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.show();
                if(databaseHandler.checkUser()) {
                    String token = databaseHandler.getUser().get(0).getToken();
//                    logout(token);
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w("login", "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    // Get new Instance ID token
                                    String token = task.getResult().getToken();
                                    fcm_token = token;
                                    List<user_model> user = databaseHandler.getUser();
                                    Log.d(MainActivity.class.getSimpleName(),"tokennih" + fcm_token);
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(login.URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    LoginAPI api = retrofit.create(LoginAPI.class);
                                    Call<loginClassJson> call = api.login(user.get(0).getEmail(), user.get(0).getPassword(), fcm_token);
                                    call.enqueue(new Callback<loginClassJson>() {
                                        @Override
                                        public void onResponse(Call<loginClassJson> call, Response<loginClassJson> response) {
                                            String message = response.body().getMessage();
                                            boolean status = response.body().getStatus();
                                            login.dataUser = response.body().getResult();


                                            try {
                                                if (status == true && response.body().getResult().getToken() != "") {



                                                    try {
                                                        Log.d("tokenmosi ", login.dataUser.getToken());
                                                        CometChat.login(login.dataUser.getToken(), new CometChat.CallbackListener<User>() {
                                                            @Override
                                                            public void onSuccess(User user) {
                                                                progress.dismiss();
                                                                Intent intent = new Intent(is_this, home.class);
                                                                startActivity(intent);
                                                                finish();
                                                                Log.d("loginn cometchat", "Login completed successfully for user: " + user.toString());
                                                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                                            }

                                                            @Override
                                                            public void onError(CometChatException e) {
                                                                progress.dismiss();
                                                                Toast.makeText(getApplicationContext(), "Gagal Login!", Toast.LENGTH_SHORT).show();
                                                                Log.d("loginn cometchat", "Login failed with exception: " + e.getMessage());
                                                            }
                                                        });
                                                    } catch (Exception ee) {
                                                        Intent intentt = new Intent(is_this, intro.class);
                                                        startActivity(intentt);
                                                        progress.dismiss();
                                                        finish();
                                                    }
                                                    try {
                                                        FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_USER + "_" + login.dataUser.getId());
                                                        FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_GROUP + "_" + login.dataUser.getIdUserGroup());
                                                    } catch (Exception e) {
                                                        Log.d("lnihah", e.getMessage());
                                                    }
                                                }
                                            }catch (Exception e){
                                                progress.dismiss();
                                                Toast.makeText(getApplicationContext(), "Gagal Login!", Toast.LENGTH_SHORT).show();
                                            }

//                                            finish();

                                        }

                                        @Override
                                        public void onFailure(Call<loginClassJson> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                                            progress.dismiss();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                }
                                            }, 2000);

                                        }
                                    });
                                    // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                                    Log.d("tokennih", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Intent intent = new Intent(is_this, intro.class);
                    startActivity(intent);
                    finish();
                    progress.dismiss();
                }
//                finish();
            }
        }, 2200);


    }

//    void logout(String token){
//        Log.d("token", token);
//        CometChat.login(token, new CometChat.CallbackListener<User>() {
//            @Override
//            public void onSuccess(User user) {
//                Log.d("login cometchat", "Login completed successfully for user: " + user.toString());
//                try {
//                    try {
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_USER + "_" + login.dataUser.getId());
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_GROUP + "_" + login.dataUser.getIdUserGroup());
//                    }catch (Exception e){
//                        Log.d("unsubscripe nih ", e.getMessage());
//                    }
//
//                    CometChat.logout(new CometChat.CallbackListener<String>() {
//                        @Override
//                        public void onSuccess(String successMessage) {
//                            FirebaseInstanceId.getInstance().getInstanceId()
//                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                            if (!task.isSuccessful()) {
//                                                Log.w("login", "getInstanceId failed", task.getException());
//                                                return;
//                                            }
//
//                                            // Get new Instance ID token
//                                            String token = task.getResult().getToken();
//                                            fcm_token = token;
//                                            List<user_model> user = databaseHandler.getUser();
//                                            Log.d(MainActivity.class.getSimpleName(),"tokennih" + fcm_token);
//                                            Retrofit retrofit = new Retrofit.Builder()
//                                                    .baseUrl(login.URL)
//                                                    .addConverterFactory(GsonConverterFactory.create())
//                                                    .build();
//
//                                            LoginAPI api = retrofit.create(LoginAPI.class);
//                                            Call<loginClassJson> call = api.login(user.get(0).getEmail(), user.get(0).getPassword(), fcm_token);
//                                            call.enqueue(new Callback<loginClassJson>() {
//                                                @Override
//                                                public void onResponse(Call<loginClassJson> call, Response<loginClassJson> response) {
//                                                    String message = response.body().getMessage();
//                                                    boolean status = response.body().getStatus();
//                                                    login.dataUser = response.body().getResult();
//                                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                                                    if(status == true) {
//                                                        Intent intent = new Intent(is_this, home.class);
//                                                        startActivity(intent);
//                                                        finish();
//                                                        try {
//                                                            CometChat.login(login.dataUser.getToken(), StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
//                                                                @Override
//                                                                public void onSuccess(User user) {
//                                                                    Log.d("login cometchat", "Login completed successfully for user: " + user.toString());
//                                                                }
//
//                                                                @Override
//                                                                public void onError(CometChatException e) {
//                                                                    Log.d("login cometchat", "Login failed with exception: " + e.getMessage());
//                                                                }
//                                                            });
//                                                        }catch(Exception ee) {
//                                                            Intent intentt = new Intent(is_this, intro.class);
//                                                            startActivity(intentt);
//                                                            finish();
//                                                        }
//                                                        try {
//                                                            FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_USER + "_" + login.dataUser.getId());
//                                                            FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_GROUP + "_" + login.dataUser.getIdUserGroup());
//                                                        }catch (Exception e){
//                                                            Log.d("lnihah", e.getMessage());
//                                                        }
//
//                                                    }
//                                                    finish();
//
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<loginClassJson> call, Throwable t) {
//                                                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
//                                                    finish();
//                                                }
//                                            });
//                                        }
//                                    });
//                        }
//
//                        @Override
//                        public void onError(CometChatException e) {
////                        Toast.makeText(home.this, "Logout Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }catch(Exception e){
//
//                }
//            }
//
//            @Override
//            public void onError(CometChatException e) {
//                Log.d("login cometchat", "Login failed with exception: " + e.getMessage());
//            }
//        });
//    }
}
