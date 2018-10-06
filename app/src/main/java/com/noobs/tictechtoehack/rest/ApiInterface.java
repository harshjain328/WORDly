package com.noobs.tictechtoehack.rest;

import com.noobs.tictechtoehack.models.Meaning;
import com.noobs.tictechtoehack.models.Word;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/api/search/word")
    Call<Meaning> getDef(@Body Word wordReq);
}
