package com.example.spotifywrappedapp.utils;

import com.example.spotifywrappedapp.apiservices.BackendService;

public class BackendServiceSingleton {

    private static BackendService backendService = null;

    public static BackendService getBackendService() {
        if (backendService == null) {
            backendService = RetrofitClient.getClient().create(BackendService.class);
        }
        return backendService;
    }
}
