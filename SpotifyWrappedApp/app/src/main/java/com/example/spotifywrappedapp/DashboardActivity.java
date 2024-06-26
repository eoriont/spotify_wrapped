package com.example.spotifywrappedapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spotifywrappedapp.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(
                    R.id.navigation_home,
                    R.id.navigation_dashboard,
                    R.id.navigation_recommendations)
                .build();
        NavController navController = Navigation
                .findNavController(this,
                        R.id.nav_host_fragment_activity_dashboard);
        NavigationUI.setupActionBarWithNavController(
                this,
                navController,
                appBarConfiguration
        );
        NavigationUI.setupWithNavController(navView, navController);
    }
}
