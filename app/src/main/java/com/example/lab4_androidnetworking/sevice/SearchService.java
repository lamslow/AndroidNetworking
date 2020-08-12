package com.example.lab4_androidnetworking.sevice;

import com.example.lab4_androidnetworking.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("services/rest/")
    Call<Example> getSearchList(@Query("extras") String extras,
                              @Query("nojsoncallback") String nojsoncallback,
                              @Query("tag_mode") String tag_Mode,
                              @Query("text") String textSearch,
                              @Query("format") String format,
                              @Query("api_key") String api_key,
                              @Query("method") String method,
                              @Query("page") int page,
                              @Query("per_page") int per_page);
}
