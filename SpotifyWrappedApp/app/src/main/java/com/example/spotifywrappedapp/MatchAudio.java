package com.example.spotifywrappedapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import java.util.stream.Collectors;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;


import com.example.spotifywrappedapp.databinding.ActivityMatchAudioBinding;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MatchAudio extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMatchAudioBinding binding;

    String[] tracks = {"Hello", "Hello"};
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchAudioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button Audio = findViewById(R.id.Audio);
        TextView newText = findViewById(R.id.newText);
        Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newText.setText("Loading");
                performNetworkRequest(tracks, newText);
            }
        });
    }
    private void performNetworkRequest(String[] tracks, TextView newText) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String baseUrl = "https://colbyb1123.pythonanywhere.com/";
                    List<String> dataList = new ArrayList<>();
                    for (String s : tracks) {
                        dataList.add(s);
                    }
                    String queryString = dataList.stream()
                            .map(s -> "data=" + s)
                            .collect(Collectors.joining("&"));
                    String urlStr = baseUrl + "?" + queryString;
                    URL url = new URL(urlStr);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    result = response.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newText.setText(result);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}