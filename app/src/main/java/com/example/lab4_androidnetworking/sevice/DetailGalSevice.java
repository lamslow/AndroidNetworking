package com.example.lab4_androidnetworking.sevice;

import com.example.lab4_androidnetworking.model.ExampleGal;
import com.example.lab4_androidnetworking.model.ExampleGall;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetailGalSevice {
    @GET("services/rest/")
    Call<ExampleGal> getListDetailGall(@Query("extras") String extras,
                                       @Query("gallery_id") String gallery_id,
                                       @Query("nojsoncallback") String nojsoncallback,
                                       @Query("user_id") String user_id,
                                       @Query("format") String format,
                                       @Query("api_key") String api_key,
                                       @Query("method") String method,
                                       @Query("page") int page,
                                       @Query("per_page") int per_page);
}
