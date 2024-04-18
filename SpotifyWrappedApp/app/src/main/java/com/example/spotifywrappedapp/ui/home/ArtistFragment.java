package com.example.spotifywrappedapp.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.databinding.ActivityFrame4Binding;
import com.example.spotifywrappedapp.models.Artist;
import com.example.spotifywrappedapp.models.History;
import com.example.spotifywrappedapp.models.Track;
import com.example.spotifywrappedapp.utils.RetrofitUtils;

import retrofit2.Call;

public class ArtistFragment extends Fragment {
    private ActivityFrame4Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityFrame4Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        TextView artist1 = binding.artist1;
        TextView artist2 = binding.artist2;
        TextView artist3 = binding.artist3;

        ImageView img1 = binding.artist1img;
        ImageView img2 = binding.artist2img;
        ImageView img3 = binding.artist3img;

        History history = ArtistFragmentArgs
                .fromBundle(getArguments()).getHistory();

        inflateArtist(history.getArtist1Id(), artist1, img1);
        inflateArtist(history.getArtist2Id(), artist2, img2);
        inflateArtist(history.getArtist3Id(), artist3, img3);

        view.setOnClickListener(v -> NavHostFragment
                .findNavController(ArtistFragment.this)
                .navigate(ArtistFragmentDirections
                        .actionArtistFragmentToHomeFragment()));
    }

    public void inflateArtist(String artistId, TextView textView, ImageView imageView) {
        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Artist> artist = service.readArtist(artistId);
        RetrofitUtils.toCompletableFuture(artist)
                .thenAccept(a -> {
                    textView.setText(a.getName());
                    Glide.with(this)
                            .load(a.getImageUrl())
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
