package com.inscripts.cometchatpulse.demo.Controller.Retrofit;

import android.util.Log;

import com.inscripts.cometchatpulse.demo.Controller.intro.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;



/**
 * Created by sulistiyanto on 07/12/16.
 */

public interface LoginAPI {

    login loginclass = new login();

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("login")
    Call<loginClassJson> login(@Field("email") String email,
                                @Field("password") String password);

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("my-profile")
    Call<loginClassJson> myProfile();
}