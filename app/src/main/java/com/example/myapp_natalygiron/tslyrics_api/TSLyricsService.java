package com.example.myapp_natalygiron.tslyrics_api;

import com.example.myapp_natalygiron.model.Lyrics;
import com.example.myapp_natalygiron.model.LyricsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TSLyricsService {

//    @GET("get-all?album={album}")
//    Call<LyricsResponse> getLyricsList(@Path("album") String album);

//    @GET("get-all?album=folklore")
//    Call<List<Lyrics>> getLyricsList();

    @GET("get-all")
    Call<List<Lyrics>> getLyricsList(@Query("album") String album);


}
