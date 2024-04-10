package com.example.spotifywrappedapp.apiservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private RetrofitClient() {
        throw new UnsupportedOperationException();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
