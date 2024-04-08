package com.example.spotifywrappedapp.ui.friends;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.databinding.FragmentFriendsBinding;

public class FriendsFragment extends Fragment {

    private FriendsViewModel mViewModel;
    private FragmentFriendsBinding binding;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);

        // Inflate the layout for this fragment using View Binding
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup UI components
        EditText enterFriend = binding.enterFriend;
        Button findButton = binding.Find;

        // Set click listener to update username in ViewModel
        findButton.setOnClickListener(v -> {
            String username = enterFriend.getText().toString();
            mViewModel.setUsername(username);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding when the view is destroyed
    }

}