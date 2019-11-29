package com.unilever.go.wallsopsi1.Controller.Retrofit;

import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.listJabatanClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.listLokasiClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.loginClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.myProfileClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.registerJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.updateProfileClassJson;
import com.unilever.go.wallsopsi1.Controller.intro.login;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by sulistiyanto on 07/12/16.
 */

public interface LoginAPI {

    login loginclass = new login();

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("login")
    Call<loginClassJson> login(@Field("email") String email,
                               @Field("password") String password,
                               @Field("fcm_token") String fcm_token);

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

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("update-profile")
    Call<updateProfileClassJson> updateProfileWithoutFoto(@Field("id_auth_user") String id_auth_user,
                                            @Field("username") String username,
                                            @Field("email") String email,
                                            @Field("phone") String phone,
                                            @Field("id_ref_jabatan") String id_ref_jabatan,
                                            @Field("id_ref_lokasi") String id_ref_lokasi);

    @Multipart
    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f"})
    @POST("update-profile")
    Call<updateProfileClassJson> updateProfile(@Part MultipartBody.Part img,
                                      @Part("id_auth_user") RequestBody id_auth_user,
                                      @Part("username") RequestBody username,
                                      @Part("email") RequestBody email,
                                      @Part("phone") RequestBody phone,
                                      @Part("id_ref_jabatan") RequestBody id_ref_jabatan,
                                      @Part("id_ref_lokasi") RequestBody id_ref_lokasi);


    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @GET("master/list-jabatan")
    Call<listJabatanClassJson> getJabatan();

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @GET("master/list-lokasi")
    Call<listLokasiClassJson> getLokasi();
}