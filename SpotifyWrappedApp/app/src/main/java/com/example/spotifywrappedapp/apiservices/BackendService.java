package com.example.spotifywrappedapp.apiservices;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BackendService {
    @POST("v1/auth/login")
    Call<String> login(@Header("Authorization") String authorization);


}
