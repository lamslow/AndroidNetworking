package com.example.lab4_androidnetworking.sevice;

import com.example.lab4_androidnetworking.model.Example;
import com.example.lab4_androidnetworking.model.ExampleCMT;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentService {
    @GET("services/rest/")
    Call<ExampleCMT> getListCommnet(@Query("photo_id") String photo_id,
                                 @Query("nojsoncallback") String nojsoncallback,
                                 @Query("format") String format,
                                 @Query("api_key") String api_key,
                                 @Query("method") String method);
}
