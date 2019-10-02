package com.unilever.go.walls.Controller.home;

import com.unilever.go.walls.Controller.Retrofit.LoginAPI;
import com.unilever.go.walls.Controller.Retrofit.jsonClass.myProfileClassJson;
import com.unilever.go.walls.Controller.intro.login;
import com.unilever.go.walls.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class my_profile extends AppCompatActivity {
    android.app.Activity is_this;
    public static final String URL = "http://13.228.214.159/mosii/belajar_api/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        EditText namalengkap = findViewById(R.id.namalengkap);
        EditText email = findViewById(R.id.email);
        EditText jabatan = findViewById(R.id.jabatan);
        EditText nohp = findViewById(R.id.nohp);
        EditText lokasi = findViewById(R.id.lokasi);
        TextView skip = findViewById(R.id.skip);
        skip.setText("My Profile");
        skip.setVisibility(View.VISIBLE);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<myProfileClassJson> call = api.getProfile(login.dataUser.getId());
        call.enqueue(new Callback<myProfileClassJson>() {
            @Override
            public void onResponse(Call<myProfileClassJson> call, Response<myProfileClassJson> response) {
                namalengkap.setText(response.body().getResult().getUsername());
                email.setText(response.body().getResult().getEmail());
//                nohp.setText(response.body().getResult().no());
//                namalengkap.setText(response.body().getResult().getUsername());
            }

            @Override
            public void onFailure(Call<myProfileClassJson> call, Throwable t) {
                Log.e(my_profile.class.getSimpleName(), "On Failure");
            }
        });
    }
}
