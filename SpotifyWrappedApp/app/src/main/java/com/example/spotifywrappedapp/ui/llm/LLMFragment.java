package com.example.spotifywrappedapp.ui.llm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrappedapp.databinding.ActivityLlmUiBinding;

public class LLMFragment extends Fragment {
    private ActivityLlmUiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LLMViewModel viewModel =
                new ViewModelProvider(this).get(LLMViewModel.class);
        binding = ActivityLlmUiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView text = binding.res;
        viewModel.getText().observe(getViewLifecycleOwner(), text::setText);
        viewModel.performNetworkRequest();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
