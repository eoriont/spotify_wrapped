package com.example.spotifywrappedapp.ui.friends;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.models.Friendship;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class FriendsViewModel extends AndroidViewModel {
    private Application application;

    public FriendsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        fetchFriends();
    }

    private MutableLiveData<List<String>> friends = new MutableLiveData<>();

    public void addFriend(String friendId) {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Friendship> call = service.addFriend(id, friendId);

        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(friendship -> {
                    Log.d("FRIENDS",
                            "Successfully added friend "
                                    + friendship.getUser2Id());
                    fetchFriends();
                })
                .exceptionally(ex -> {
                    Log.e("FRIENDS",
                            Log.getStackTraceString(new Exception(ex)));
                    return null;
                });

    }

    public void fetchFriends() {
        UserData userData = new UserData(application);
        String id = userData.getId();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<List<Friendship>> call = service.getFriendsList(id);

        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(newFriends -> {
                    Log.d("FRIENDS", "Successfully retrieved friends!");

                    List<String> names = new ArrayList<>();
                    for (Friendship f : newFriends) {
                        names.add(f.getUser2Id());
                    }
                    friends.postValue(names);
                })
                .exceptionally(ex -> {
                    Log.e("FRIENDS",
                            Log.getStackTraceString(new Exception(ex)));
                    return null;
                });
    }

    public void deleteFriend(int pos) {
        UserData userData = new UserData(application);
        String id = userData.getId();

        String friend = getFriends().getValue().get(pos);

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Void> call = service.deleteFriend(id, friend);

        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(v -> {
                    Log.d("FRIENDS", "Successfully removed friend " + friend);

                    List<String> newFriends = friends.getValue();
                    newFriends.remove(pos);

                    friends.postValue(newFriends);
                })
                .exceptionally(ex -> {
                    Log.e("FRIENDS",
                            Log.getStackTraceString(new Exception(ex)));
                    return null;
                });
    }

    public LiveData<List<String>> getFriends() {
        return friends;
    }
}
