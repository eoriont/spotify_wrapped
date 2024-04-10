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

import com.example.spotifywrappedapp.databinding.FragmentRecommendationsBinding;

public class RecommendationsFragment extends Fragment {

    private FragmentRecommendationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecommendationsViewModel viewModel =
                new ViewModelProvider(this).get(RecommendationsViewModel.class);

        binding = FragmentRecommendationsBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

        Button audio = binding.Audio;
        TextView newText = binding.newText;

        viewModel.getText().observe(getViewLifecycleOwner(), newText::setText);

        audio.setOnClickListener(v -> viewModel.performNetworkRequest());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
