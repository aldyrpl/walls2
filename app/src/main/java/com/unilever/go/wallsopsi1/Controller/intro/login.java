package com.unilever.go.wallsopsi1.Controller.intro;
import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.wallsopsi1.Contracts.LoginActivityContract;
import com.unilever.go.wallsopsi1.Contracts.StringContract;
import com.unilever.go.wallsopsi1.Controller.Retrofit.LoginAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.loginClassJson;
import com.unilever.go.wallsopsi1.Controller.SQL.*;
import com.unilever.go.wallsopsi1.Controller.home.home;
import com.unilever.go.wallsopsi1.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity implements LoginActivityContract.LoginActivityView {
    android.app.Activity is_this;

    public static final String URL = "http://103.136.25.83:8000/api/";
    public static final String URL_WEATHER_API = "https://api.openweathermap.org/data/";
    public static final String WEATHER_API_KEY = "6fe455c439244b711f17c8330914e57e";
    DatabaseHandler databaseHandler;
    private LoginActivityContract.LoginActivityPresenter loginActivityPresenter;
    public static loginClassJson.Result dataUser;
    public String fcm_token = "";
    @BindView(R.id.email) EditText emailEt;
    @BindView(R.id.password) EditText passwordEt;

    @BindView(R.id.back) ImageView back;
    @OnClick(R.id.back) void back() {
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
        finish();
    }
    @BindView(R.id.masuk) TextView btnMasuk;

    @OnClick(R.id.masuk) void masuk() {
        btnMasuk.setText("LOADING...");
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("login", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance IDback token
                        String token = task.getResult().getToken();
                        fcm_token = token;
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("tokennih", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        LoginAPI api = retrofit.create(LoginAPI.class);
                        Log.d("tokennish", fcm_token);
                        Call<loginClassJson> call = api.login(email, password, fcm_token);
                        call.enqueue(new Callback<loginClassJson>() {
                            @Override
                            public void onResponse(Call<loginClassJson> call, Response<loginClassJson> response) {
                                Log.d("tokennish 1 ", fcm_token);
                                if(response.body().getMessage().length() > 4 && response.body().getMessage() != null) {
                                    Log.d("tokennish 2 ", fcm_token);
                                    String message = response.body().getMessage();
                                    boolean status = response.body().getStatus();
                                    dataUser = response.body().getResult();

                                    try {
                                        Log.d("tokennish 3 ", fcm_token);
                                        if (status == true && response.body().getResult().getToken() != "") {


                                            user_model usermodel = new user_model(
                                                    emailEt.getText().toString(),
                                                    passwordEt.getText().toString(), response.body().getResult().getToken());

                                            databaseHandler.insertEmailPassword(usermodel);
                                        Log.d("tokan", login.dataUser.getToken());
                                            CometChat.login(login.dataUser.getToken(), new CometChat.CallbackListener<User>() {
                                                @Override
                                                public void onSuccess(User user) {
                                                    Intent intent = new Intent(is_this, home.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                                                    btnMasuk.setText("MASUK");
                                                }

                                                @Override
                                                public void onError(CometChatException e) {
                                                    btnMasuk.setText("MASUK");
                                                    Toast.makeText(login.this, "Gagal Login!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_USER + "_" + dataUser.getId());
                                            FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_" + CometChatConstants.RECEIVER_TYPE_GROUP + "_" + dataUser.getIdUserGroup());
                                        }else{
                                            btnMasuk.setText("MASUK");
                                            Toast.makeText(login.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        btnMasuk.setText("MASUK");
                                        Toast.makeText(login.this, "Gagal Login!", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    btnMasuk.setText("MASUK");
                                    Toast.makeText(login.this, "Data belum lengkap!", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<loginClassJson> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                                btnMasuk.setText("MASUK");
                            }
                        });
                    }
                });



    }

    @Override
    public void startCometChatActivity() {

//        startActivity(new Intent(login.this, CometChatActivity.class));
//        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        skip.setVisibility(View.GONE);
        is_this = this;
        databaseHandler = new DatabaseHandler(this);
    }


}
