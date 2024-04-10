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
import androidx.navigation.ui.AppBarConfiguration;

import com.example.spotifywrappedapp.databinding.FragmentNotificationsBinding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;


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

        Button audio = binding.Audio;
        TextView newText = binding.newText;
        audio.setOnClickListener(v -> {
            newText.setText("Loading");
            performNetworkRequest(tracks, newText);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private AppBarConfiguration appBarConfiguration;

    private String[] tracks = {"Hello", "Hello"};
    private String result = "";

    private void performNetworkRequest(String[] trackNames, TextView newText) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String baseUrl = "https://colbyb1123.pythonanywhere.com/";
                List<String> dataList = new ArrayList<>();
                for (String s : trackNames) {
                    dataList.add(s);
                }
                String queryString = dataList.stream()
                        .map(s -> "data=" + s)
                        .collect(Collectors.joining("&"));
                String urlStr = baseUrl + "?" + queryString;
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection)
                        url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                result = response.toString();
                getActivity().runOnUiThread(() -> newText.setText(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
