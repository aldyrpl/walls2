package com.inscripts.cometchatpulse.demo.Controller.intro;
import com.inscripts.cometchatpulse.demo.Controller.Retrofit.LoginAPI;
import com.inscripts.cometchatpulse.demo.Controller.Retrofit.loginClassJson;
import com.inscripts.cometchatpulse.demo.Controller.home.home;
import com.inscripts.cometchatpulse.demo.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class login extends AppCompatActivity {
    android.app.Activity is_this;

    public static final String URL = "http://13.228.214.159/mosii/belajar_api/api/";


    @BindView(R.id.email) EditText emailEt;
    @BindView(R.id.password) EditText passwordEt;
    @BindView(R.id.masuk) TextView btnMasuk;

    @OnClick(R.id.masuk) void masuk() {
        btnMasuk.setText("LOADING...");
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<loginClassJson> call = api.login(email, password);
        call.enqueue(new Callback<loginClassJson>() {
            @Override
            public void onResponse(Call<loginClassJson> call, Response<loginClassJson> response) {
                String message = response.body().getMessage();
                boolean status = response.body().getStatus();
                home.dataUser = response.body().getResult();
                Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                if(status == true) {
                    Intent intent = new Intent(is_this, home.class);
                    startActivity(intent);
                    finish();
                }
                btnMasuk.setText("MASUK");
            }

            @Override
            public void onFailure(Call<loginClassJson> call, Throwable t) {
//                progress.dismiss();
                Toast.makeText(login.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                btnMasuk.setText("MASUK");
            }
        });
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
    }

}
