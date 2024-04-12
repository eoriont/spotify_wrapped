package com.example.spotifywrappedapp.apiservices;

import com.example.spotifywrappedapp.models.Artist;
import com.example.spotifywrappedapp.models.Friendship;
import com.example.spotifywrappedapp.models.History;
import com.example.spotifywrappedapp.models.Track;

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

    @GET("v1/artist/{artistId}")
    Call<Artist> readArtist(@Path("artistId") String artistId);

    @GET("v1/track/{trackId}")
    Call<Track> readTrack(@Path("trackId") String trackId);

    @GET("v1/friend/{id}")
    Call<List<Friendship>> getFriendsList(@Path("id") String id);

    @POST("v1/friend/{id1}/{id2}")
    Call<Friendship> addFriend(@Path("id1") String id1,
                               @Path("id2") String id2);

    @DELETE("v1/friend/{id1}/{id2}")
    Call<Void> deleteFriend(@Path("id1") String id1,
                                  @Path("id2") String id2);

    @GET("v1/history/{id}")
    Call<List<History>> getSummaries(@Path("id") String id,
                                @Header("Authorization") String authorization);

    @GET("v1/llm/{id}")
    Call<String> getLLMResponse(@Path("id") String userid);

}
