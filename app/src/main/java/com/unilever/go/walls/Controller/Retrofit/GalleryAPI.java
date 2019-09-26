package com.unilever.go.walls.Controller.Retrofit;

import com.unilever.go.walls.Controller.intro.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;



/**
 * Created by sulistiyanto on 07/12/16.
 */

public interface GalleryAPI {

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("gallery/list-gallery")
    Call<galleryClassJson> getGallery(@Field("filters[ref_menu_category_id]") String cat_id,
                                      @Field("filters[is_public]") String is_public,
                                      @Field("limit") String limit,
                                      @Field("page") String page);
}