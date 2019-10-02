package com.unilever.go.walls.Controller.Retrofit;

import com.unilever.go.walls.Controller.Retrofit.jsonClass.EventClassJson;
import com.unilever.go.walls.Controller.Retrofit.jsonClass.eventCategoryJsonClass;
import com.unilever.go.walls.Controller.Retrofit.jsonClass.getEventClassJson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EventAPI {
    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("agenda/add")
    Call<EventClassJson> addEvent(@Field("ref_menu_id") String ref_menu_id,
                                  @Field("ref_event_loops_id") String ref_event_loops_id,
                                  @Field("description") String description,
                                  @Field("due_date") String due_date,
                                  @Field("due_time") String due_time,
                                  @Field("is_alarm") String is_alarm,
                                  @Field("user_id") String user_id);

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("agenda/list-agenda")
    Call<getEventClassJson> getEvent(@Field("limit") String limit,
                                     @Field("page") String page);

    @Headers({"GOWALLS-API-KEY: 6fba6be0-da86-4842-a7c6-80091dccb44f","Content-Type: application/x-www-form-urlencoded"})
    @GET("agenda/list-tasks")
    Call<eventCategoryJsonClass> getEvent();
}
