package com.unilever.go.wallsopsi1.Controller.Retrofit;

import com.unilever.go.wallsopsi1.Controller.Retrofit.WeatherJson.JsonWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by sulistiyanto on 07/12/16.
 */

public interface WeatherAPI {

    @GET("2.5/weather")
    Call<JsonWeather> getWeather(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("units") String units,
                                 @Query("appid") String appid);
}