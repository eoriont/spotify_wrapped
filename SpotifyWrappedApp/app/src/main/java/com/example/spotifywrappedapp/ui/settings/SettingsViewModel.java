package com.example.spotifywrappedapp.ui.settings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.spotifywrappedapp.UserData;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.utils.RetrofitUtils;

import retrofit2.Call;

public class SettingsViewModel extends AndroidViewModel {
    private Application application;

    public SettingsViewModel(Application app) {
        super(app);
        this.application = app;
    }

    public void deleteUser() {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Void> call = service.deleteUser(id);
        RetrofitUtils.toCompletableFuture(call)
                .exceptionally(ex -> null);
    }
}
