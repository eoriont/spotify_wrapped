package com.example.spotifywrappedapp.ui.recommendations;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.models.RecDTO;
import com.example.spotifywrappedapp.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;


public class RecommendationsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<RecDTO>> mRecs;
    private Application application;

    public RecommendationsViewModel(Application app) {
        super(app);
        this.application = app;
        mRecs = new MutableLiveData<>();
    }

    public LiveData<List<RecDTO>> getRecs() {
        return mRecs;
    }


    public void getRecommendations() {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<List<RecDTO>> call = service.getRecommendations(
                userData.getToken(), id);
        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(v -> {
                    System.out.println(v);
                    mRecs.postValue(v);
                })
                .exceptionally(ex -> null);

    }
}
