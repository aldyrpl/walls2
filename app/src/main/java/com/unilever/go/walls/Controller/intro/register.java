package com.unilever.go.walls.Controller.intro;
import com.cometchat.pro.constants.CometChatConstants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.walls.Activity.CometChatActivity;
import com.unilever.go.walls.Contracts.LoginActivityContract;
import com.unilever.go.walls.Contracts.StringContract;
import com.unilever.go.walls.Controller.MainActivity;
import com.unilever.go.walls.Controller.Retrofit.LoginAPI;
import com.unilever.go.walls.Controller.Retrofit.loginClassJson;
import com.unilever.go.walls.Controller.Retrofit.registerJson;
import com.unilever.go.walls.Controller.SQL.*;
import com.unilever.go.walls.Controller.home.home;
import com.unilever.go.walls.Presenters.LoginAcitivityPresenter;
import com.unilever.go.walls.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
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

public class register extends AppCompatActivity{
    android.app.Activity is_this;

    public static final String URL = "http://13.228.214.159/mosii/belajar_api/api/";

    @BindView(R.id.namalengkap) EditText namalengkap;
    @BindView(R.id.email) EditText _email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.password_retype) EditText password_retype;

    @BindView(R.id.daftar) TextView btnDaftar;

    @OnClick(R.id.daftar) void daftar() {
        btnDaftar.setText("LOADING...");
        String username = namalengkap.getText().toString();
        String email = _email.getText().toString();
        String userpass = password.getText().toString();
        String re_pass = password_retype.getText().toString();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d(register.class.getSimpleName(), "name = " + username + " email = " + email + " userpass = " + userpass + " repass = " + re_pass);
        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<registerJson> call = api.register(username, userpass, re_pass, email);
        call.enqueue(new Callback<registerJson>() {
            @Override
            public void onResponse(Call<registerJson> call, Response<registerJson> response) {
//                Log.d(register.class.getSimpleName(), response.body().toString());
//                Log.d(register.class.getSimpleName(), "code : " + response.body().toString());

                boolean status = response.body().getStatus();

                if (status) {
                    String message = response.body().getMessage();
                    Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String error = response.body().getError().get(0).getMessage();
                    Toast.makeText(register.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<registerJson> call, Throwable t) {
//                progress.dismiss();
                Toast.makeText(register.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                btnDaftar.setText("DAFTAR");
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
    }


}
