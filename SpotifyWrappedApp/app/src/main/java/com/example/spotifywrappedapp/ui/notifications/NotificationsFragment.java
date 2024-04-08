package com.example.spotifywrappedapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrappedapp.MainActivity;
import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.databinding.FragmentNotificationsBinding;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private TextView tokenTextView, codeTextView, profileTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText()
//                .observe(getViewLifecycleOwner(), textView::setText);

        // Initialize the views
        tokenTextView = binding.tokenTextView;
        codeTextView = binding.codeTextView;
        profileTextView = binding.responseTextView;

        // Initialize the buttons
        Button tokenBtn = binding.tokenBtn;
        Button codeBtn = binding.codeBtn;
        Button profileBtn = binding.profileBtn;

        // Set the click listeners for the buttons

        tokenBtn.setOnClickListener((v) -> {
            getToken();
        });

        codeBtn.setOnClickListener((v) -> {
            getCode();
        });

        profileBtn.setOnClickListener((v) -> {
            getUserProfile();
        });

        return root;
    }

    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(
                AuthorizationResponse.Type.TOKEN);
        AuthorizationClient
                .openLoginActivity(MainActivity.this,
                        AUTH_TOKEN_REQUEST_CODE,
                        request);
    }

    public void getCode() {
        final AuthorizationRequest request =
                getAuthenticationRequest(
                        AuthorizationResponse.Type.CODE);
        AuthorizationClient
                .openLoginActivity(
                        MainActivity.this,
                        AUTH_CODE_REQUEST_CODE,
                        request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
