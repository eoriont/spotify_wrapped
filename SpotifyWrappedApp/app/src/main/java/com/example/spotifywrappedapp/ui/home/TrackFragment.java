package com.example.spotifywrappedapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
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

        TextView track1 = binding.track1;
        TextView track2 = binding.track2;
        TextView track3 = binding.track3;

        ImageView img1 = binding.track1img;
        ImageView img2 = binding.track2img;
        ImageView img3 = binding.track3img;

        History history = TrackFragmentArgs
                .fromBundle(getArguments()).getHistory();

        inflateTrack(history.getTrack1Id(), track1, img1);
        inflateTrack(history.getTrack2Id(), track2, img2);
        inflateTrack(history.getTrack3Id(), track3, img3);

        view.setOnClickListener(v -> NavHostFragment
                .findNavController(TrackFragment.this)
                .navigate(TrackFragmentDirections
                                .actionTrackFragmentToArtistFragment()
                                .setHistory(history))
        );
    }

    public void inflateTrack(String trackId, TextView textView,
                             ImageView imageView) {
        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Track> track1Call = service.readTrack(trackId);
        RetrofitUtils.toCompletableFuture(track1Call)
                .thenAccept(t -> {
                    textView.setText(t.getName());
                    Glide.with(this)
                            .load(t.getImageUrl())
                            .into(imageView);
                })
                .exceptionally(ex -> {
                    return null;
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
