package com.example.spotifywrappedapp;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;

import com.example.spotifywrappedapp.databinding.ActivityEnterFriendBinding;

public class EnterFriend extends AppCompatActivity {

    private String username;
    private ActivityEnterFriendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_friend);
        binding = ActivityEnterFriendBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        EditText enterFriend = binding.enterFriend;
        Button find = binding.Find;
        //saves entry as username
        find.setOnClickListener(
                v -> username = enterFriend.getText().toString());
    }


}
