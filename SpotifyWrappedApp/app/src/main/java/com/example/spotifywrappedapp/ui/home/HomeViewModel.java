package com.example.spotifywrappedapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.models.History;
import com.example.spotifywrappedapp.utils.RetrofitUtils;
import java.util.List;

import retrofit2.Call;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<History>> summaries = new MutableLiveData<>();
    private Application application;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        fetchSummaries();
    }

    public void fetchSummaries() {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<List<History>> call = service.getSummaries(id,
                userData.getToken());
        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(newSummaries -> {
                    Log.d("SUMMARIES", "Successfully retrieved summaries!");
                    summaries.postValue(newSummaries);
                })
                .exceptionally(ex -> {
                    Log.e("SUMMARIES",
                            Log.getStackTraceString(new Exception(ex)));
                    return null;
                });
    }


    public LiveData<List<History>> getSummaries() {
        return summaries;
    }

}
