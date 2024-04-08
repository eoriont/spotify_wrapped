package com.example.spotifywrappedapp.ui.friends;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrappedapp.UserData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FriendsViewModel extends AndroidViewModel {
    private Application application;

    public FriendsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        fetchFriends();
    }

    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<List<String>> friends = new MutableLiveData<>();

    public LiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
        Log.d("Friends", username);
    }

    public void addFriend(String username) {
        UserData userData = new UserData(application);
        String id = userData.getId();
        final Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/v1/friend/"+id+"/"+username)
                .post(RequestBody.create(null, ""))
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("backend", "failure! "+ e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("Friends", "Successfully added friend!");
                    fetchFriends();
                }
            }
        });

    }

    public void fetchFriends() {
        UserData userData = new UserData(application);
        String id = userData.getId();
        final Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/v1/friend/"+id)
                .get()
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("backend", "failure! "+ e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        Log.d("JSON", json);
                        JSONArray arr = new JSONArray(json);
                        List<String> newFriends = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            newFriends.add(arr.getJSONObject(i).getString("user2Id"));
                        }
                        friends.postValue(newFriends);
                    }
                } catch (Exception e) {
                    Log.d("JSON", "Error parsing friends");
                    Log.e("JSON", e.getMessage());
                    Log.e("JSON", Log.getStackTraceString(e));
                }
            }
        });
    }

    public LiveData<List<String>> getFriends() {
        return friends;
    }
}