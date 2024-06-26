package com.example.spotifywrappedapp.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrappedapp.MainActivity;
import com.example.spotifywrappedapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this)
                .get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel
                .getText()
                .observe(getViewLifecycleOwner(), binding.header::setText);
        viewModel.readUser();

        return root;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonUpdateAccount
                .setOnClickListener(v -> {
                    viewModel
                            .getText()
                            .observe(
                                    getViewLifecycleOwner(),
                                    binding.header::setText
                            );

                    viewModel.updateUser(
                            binding.firstName.getText().toString(),
                            binding.lastName.getText().toString(),
                            binding.email.getText().toString(),
                            binding.password.getText().toString()
                    );
                });

        binding.buttonDeleteAccount
                .setOnClickListener(v -> {
                    viewModel.deleteUser();

                    Intent intent =
                            new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                });

        binding.buttonSignOut.setOnClickListener(v -> {
            viewModel.resetUserData();
            Intent intent =
                    new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
