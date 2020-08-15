package com.example.lab4_androidnetworking.sevice;

import com.example.lab4_androidnetworking.model.Example;
import com.example.lab4_androidnetworking.model.ExampleGall;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumSevice {
    @GET("services/rest/")
    Call<ExampleGall> getListGall(@Query("primary_photo_extras") String extras,
                                  @Query("nojsoncallback") String nojsoncallback,
                                  @Query("user_id") String user_id,
                                  @Query("format") String format,
                                  @Query("api_key") String api_key,
                                  @Query("method") String method,
                                  @Query("page") int page,
                                  @Query("per_page") int per_page);
}
