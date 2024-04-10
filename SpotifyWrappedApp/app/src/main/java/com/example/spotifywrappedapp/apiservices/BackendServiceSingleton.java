package com.example.spotifywrappedapp.apiservices;

public final class BackendServiceSingleton {

    private static BackendService backendService = null;

    private BackendServiceSingleton() {
        throw new UnsupportedOperationException();
    }
    public static BackendService getBackendService() {
        if (backendService == null) {
            backendService = RetrofitClient
                    .getClient()
                    .create(BackendService.class);
        }
        return backendService;
    }
}
