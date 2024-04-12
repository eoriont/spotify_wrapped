package com.example.spotifywrappedapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.databinding.ActivityFrame2Binding;
import com.example.spotifywrappedapp.models.History;
import com.example.spotifywrappedapp.models.Track;
import com.example.spotifywrappedapp.utils.RetrofitUtils;

import retrofit2.Call;

public class TrackFragment extends Fragment {
    private ActivityFrame2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityFrame2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        TextView track1 = view.findViewById(R.id.track1);
        TextView track2 = view.findViewById(R.id.track2);
        TextView track3 = view.findViewById(R.id.track3);

        History history = TrackFragmentArgs
                .fromBundle(getArguments()).getHistory();

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Track> track1Call = service.readTrack(history.getTrack1Id());
        RetrofitUtils.toCompletableFuture(track1Call)
                .thenAccept(t -> {
                    track1.setText(t.getName());
                })
                .exceptionally(ex -> {
                    return null;
                });


        Call<Track> track2Call = service.readTrack(history.getTrack2Id());
        RetrofitUtils.toCompletableFuture(track2Call)
                .thenAccept(t -> {
                    track2.setText(t.getName());
                })
                .exceptionally(ex -> {
                    return null;
                });


        Call<Track> track3Call = service.readTrack(history.getTrack3Id());
        RetrofitUtils.toCompletableFuture(track3Call)
                .thenAccept(t -> {
                    track3.setText(t.getName());
                })
                .exceptionally(ex -> {
                    return null;
                });

        view.setOnClickListener(v -> NavHostFragment
                .findNavController(TrackFragment.this)
                .navigate(TrackFragmentDirections
                                .actionTrackFragmentToArtistFragment()
                                .setHistory(history))
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
