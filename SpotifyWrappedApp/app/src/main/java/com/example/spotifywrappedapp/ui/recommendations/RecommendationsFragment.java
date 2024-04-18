package com.example.spotifywrappedapp.ui.recommendations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrappedapp.databinding.FragmentRecommendationsBinding;
import com.example.spotifywrappedapp.models.RecDTO;

import java.util.List;

public class RecommendationsFragment extends Fragment {

    private FragmentRecommendationsBinding binding;
    private List<RecDTO> recs;

    private static final int THREE = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecommendationsViewModel viewModel =
                new ViewModelProvider(this).get(RecommendationsViewModel.class);

        binding = FragmentRecommendationsBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

        Button audio = binding.audio;

        viewModel.getRecs().observe(getViewLifecycleOwner(), newList -> {
            recs = newList;

            binding.textView1.setText("Based on how much you listen to "
                    + recs.get(0) + ", our analysis recommends:");
            binding.textView2.setText(recs.get(1).toString());
            binding.textView3.setText(recs.get(2).toString());
            binding.textView4.setText(recs.get(THREE).toString());
        });

        audio.setOnClickListener(v -> {
            binding.textView1.setText("Loading audio analysis from API...");
            viewModel.getRecommendations();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
