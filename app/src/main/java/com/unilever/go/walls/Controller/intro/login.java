package com.unilever.go.walls.Controller.intro;
import com.cometchat.pro.constants.CometChatConstants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.walls.Contracts.LoginActivityContract;
import com.unilever.go.walls.Contracts.StringContract;
import com.unilever.go.walls.Controller.Retrofit.LoginAPI;
import com.unilever.go.walls.Controller.Retrofit.jsonClass.loginClassJson;
import com.unilever.go.walls.Controller.SQL.*;
import com.unilever.go.walls.Controller.home.home;
import com.unilever.go.walls.Presenters.LoginAcitivityPresenter;
import com.unilever.go.walls.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

public class login extends AppCompatActivity implements LoginActivityContract.LoginActivityView {
    android.app.Activity is_this;

    public static final String URL = "http://13.228.214.159/mosii/belajar_api/api/";
    public static final String URL_WEATHER_API = "https://api.openweathermap.org/data/";
    public static final String WEATHER_API_KEY = "6fe455c439244b711f17c8330914e57e";
    DatabaseHandler databaseHandler;
    private LoginActivityContract.LoginActivityPresenter loginActivityPresenter;
    public static loginClassJson.Result dataUser;

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
                dataUser = response.body().getResult();
                Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                if(status == true) {
                    Intent intent = new Intent(is_this, home.class);
                    startActivity(intent);
                    finish();
                    btnMasuk.setText("MASUK");
                    user_model usermodel = new user_model(
                            emailEt.getText().toString(),
                            passwordEt.getText().toString());

                    databaseHandler.insertEmailPassword(usermodel);

                    loginActivityPresenter = new LoginAcitivityPresenter();
                    loginActivityPresenter.attach(login.this);
                    loginActivityPresenter.loginCheck();
                    loginActivityPresenter.Login(login.this,dataUser.getId());

                    FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID+"_"+ CometChatConstants.RECEIVER_TYPE_USER+"_"+dataUser.getId());
                    FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID+"_"+CometChatConstants.RECEIVER_TYPE_GROUP+"_"+dataUser.getIdUserGroup());
                }

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
