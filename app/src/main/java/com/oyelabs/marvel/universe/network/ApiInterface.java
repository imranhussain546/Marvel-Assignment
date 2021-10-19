package com.oyelabs.marvel.universe.network;

import com.oyelabs.marvel.universe.model.MarvelResponse;


import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/v1/public/characters")
    Call<MarvelResponse> getCharacters(@Query("apikey") String apikey,
                                       @Query("hash") String hash,
                                       @Query("ts") String ts
    );

    @GET("/v1/public/characters/{characterId}")
    Call<MarvelResponse> characterdetail(@Path("characterId") String characterId,
                                      @Query("apikey") String apikey,
                                      @Query("hash") String hash,
                                      @Query("ts") String ts
    );
}
