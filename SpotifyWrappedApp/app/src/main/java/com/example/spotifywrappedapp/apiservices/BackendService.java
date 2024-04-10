package com.example.spotifywrappedapp.apiservices;

import com.example.spotifywrappedapp.models.Friendship;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BackendService {
    @POST("v1/auth/login")
    Call<String> login(@Header("Authorization") String authorization);

    @GET("v1/friend/{id}")
    Call<List<Friendship>> getFriendsList(@Path("id") String id);

    @POST("v1/friend/{id1}/{id2}")
    Call<Friendship> addFriend(@Path("id1") String id1,
                               @Path("id2") String id2);

    @DELETE("v1/friend/{id1}/{id2}")
    Call<Void> deleteFriend(@Path("id1") String id1,
                                  @Path("id2") String id2);

}
