package com.example.spotifywrappedapp.ui.settings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.models.UpdateUserRequest;
import com.example.spotifywrappedapp.models.User;
import com.example.spotifywrappedapp.utils.RetrofitUtils;

import retrofit2.Call;

public class SettingsViewModel extends AndroidViewModel {
    private Application application;
    private final MutableLiveData<String> mText;

    public SettingsViewModel(Application app) {
        super(app);
        this.application = app;
        mText = new MutableLiveData<>();
        mText.setValue("Update your account info:");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void readUser() {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<User> call = service.readUser(id);
        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(u -> {
                    if (u.getFirstName() == null || u.getLastName() == null) {
                        mText
                                .setValue("Welcome "
                                        + u.getId()
                                        + "! "
                                        + "Update your account info:");
                    } else {
                        mText.setValue("Welcome "
                                + u.getFirstName()
                                + " " + u.getLastName()
                                + "! " + "Update your account info:");
                    }
                })
                .exceptionally(ex -> null);
    }

    public void updateUser(String firstName, String lastName,
                           String email, String password) {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<User> call = service.updateUser(id,
                new UpdateUserRequest(firstName, lastName, email, password));
        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(u -> {
                    if (u.getFirstName() == null || u.getLastName() == null) {
                        mText
                                .setValue("Welcome "
                                        + u.getId()
                                        + "! "
                                        + "Update your account info:");
                    } else {
                        mText.setValue("Welcome "
                                + u.getFirstName()
                                + " " + u.getLastName()
                                + "! " + "Update your account info:");
                    }
                })
                .exceptionally(ex -> null);
    }

    public void resetUserData() {
        UserData userData = new UserData(application);
        userData.setToken(null);
        userData.setId(null);
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
