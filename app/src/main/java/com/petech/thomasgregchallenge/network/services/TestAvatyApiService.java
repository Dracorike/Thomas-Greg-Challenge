package com.petech.thomasgregchallenge.network.services;

import com.petech.thomasgregchallenge.network.dtos.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestAvatyApiService {
    @POST("Desafio/rest/desafioRest")
    Call<Void> postUser(@Body UserDTO user);
}
