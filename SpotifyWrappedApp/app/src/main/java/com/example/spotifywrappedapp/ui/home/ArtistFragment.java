package com.example.spotifywrappedapp.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.example.spotifywrappedapp.databinding.ActivityFrame4Binding;
import com.example.spotifywrappedapp.models.Artist;
import com.example.spotifywrappedapp.models.History;
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

        TextView artist1 = view.findViewById(R.id.artist1);
        TextView artist2 = view.findViewById(R.id.artist2);
        TextView artist3 = view.findViewById(R.id.artist3);

        History history = ArtistFragmentArgs
                .fromBundle(getArguments()).getHistory();

        Log.d("History", history.getArtist1Id());

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<Artist> artist1Call = service.readArtist(history.getArtist1Id());
        RetrofitUtils.toCompletableFuture(artist1Call)
                .thenAccept(a -> {
                    artist1.setText(a.getName());
                })
                .exceptionally(ex -> {
                    return null;
                });


        Call<Artist> artist2Call = service.readArtist(history.getArtist2Id());
        RetrofitUtils.toCompletableFuture(artist2Call)
                .thenAccept(a -> {
                    artist2.setText(a.getName());
                })
                .exceptionally(ex -> {
                    return null;
                });


        Call<Artist> artist3Call = service.readArtist(history.getArtist3Id());
        RetrofitUtils.toCompletableFuture(artist3Call)
                .thenAccept(a -> {
                    artist3.setText(a.getName());
                })
                .exceptionally(ex -> {
                    return null;
                });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment
                        .findNavController(ArtistFragment.this)
                        .navigate(ArtistFragmentDirections
                                .actionArtistFragmentToHomeFragment());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
