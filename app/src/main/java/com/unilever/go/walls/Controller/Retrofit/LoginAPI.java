package com.unilever.go.walls.Controller.Retrofit;

import com.unilever.go.walls.Controller.Retrofit.jsonClass.loginClassJson;
import com.unilever.go.walls.Controller.Retrofit.jsonClass.myProfileClassJson;
import com.unilever.go.walls.Controller.Retrofit.jsonClass.registerJson;
import com.unilever.go.walls.Controller.intro.login;

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

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("registration")
    Call<registerJson> register(@Field("username") String username,
                                @Field("userpass") String userpass,
                                @Field("re_pass") String re_pass,
                                @Field("email") String email);

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("my-profile")
    Call<myProfileClassJson> getProfile(@Field("id_auth_user") String id_auth_user);
}