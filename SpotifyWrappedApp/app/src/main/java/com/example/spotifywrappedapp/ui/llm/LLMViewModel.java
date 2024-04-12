package com.example.spotifywrappedapp.ui.llm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.utils.RetrofitUtils;


import retrofit2.Call;

public class LLMViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private Application application;

    public LLMViewModel(Application app) {
        super(app);
        this.application = app;
        mText = new MutableLiveData<>();
        mText.setValue("This is LLM fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void performNetworkRequest() {
        mText.setValue("Loading...");

        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<String> llmcall = service.getLLMResponse(id);
        RetrofitUtils.toCompletableFuture(llmcall)
                .thenAccept(mText::postValue)
                .exceptionally(ex -> null);

//        final Request request = new Request.Builder()
//                .url("http://10.0.2.2:8080/v1/llm/rap/k-pop/hip-hop")
//                .get()
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call,
//                                  @NonNull IOException e) {
//                Log.d("backend", "failure! " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call,
//                                   @NonNull Response response) {
//                if (response.isSuccessful()) {
//                    try {
//                        String res = response.body().string();
//                        Log.d("backend", res);
//                        mText.postValue(res);
//                    } catch (Exception e) {
//                        Log.d("backend", Log.getStackTraceString(e));
//                    }
//                }
//            }
//        });
    }
}
