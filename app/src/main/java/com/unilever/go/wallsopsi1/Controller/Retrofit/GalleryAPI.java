package com.unilever.go.wallsopsi1.Controller.Retrofit;

import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.galleryClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.uploadImageJson;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by sulistiyanto on 07/12/16.
 */

public interface GalleryAPI {

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("gallery/list-gallery")
    Call<galleryClassJson> getGallery(@Field("filters[author_id]") String author_id,
                                      @Field("filters[ref_menu_category_id]") String cat_id,
                                      @Field("filters[is_public]") String is_public,
                                      @Field("limit") String limit,
                                      @Field("page") String page);

    @Multipart
    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f"})
    @POST("gallery/add")
    Call<uploadImageJson> uploadImage(@Part MultipartBody.Part img, @Part("ref_menu_category_id") RequestBody ref_menu_category_id, @Part("user_id") RequestBody user_id);
}