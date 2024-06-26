package com.example.spotifywrappedapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.apiservices.BackendServiceSingleton;
import com.example.spotifywrappedapp.databinding.ActivityMainBinding;
import com.example.spotifywrappedapp.models.AuthDTO;
import com.example.spotifywrappedapp.utils.RetrofitUtils;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "57630b7e960946ab83c1e0dbda46a4ca";
    public static final String REDIRECT_URI = "spotifywrappedapp://auth";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private String mAccessToken, mAccessCode;
    private ActivityMainBinding binding;

    // Sorry kinda weird :(
    private AuthDTO currentAuthInfo = new AuthDTO("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button signInBtn = binding.buttonAuthSignIn;
        signInBtn.setOnClickListener((v) -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();

            if (email.equals("") || password.equals("")) {
                Log.d("LOGIN", "Enter password!");
                return;
            }

            this.currentAuthInfo = new AuthDTO(email, password,
                    "");

            getToken();
        });
    }

    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(
                AuthorizationResponse.Type.TOKEN);
        AuthorizationClient
                .openLoginActivity(MainActivity.this,
                        AUTH_TOKEN_REQUEST_CODE,
                        request);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response =
                AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();

            // TODO: Agree on the best way to cache user data
            UserData userData = new UserData(getApplication());
            userData.setToken(mAccessToken);
            Log.d("ACCESS TOKEN", mAccessToken);
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
        }

        getUserProfile();
    }

    public void getUserProfile() {
        this.currentAuthInfo.setBearerToken("Bearer " + mAccessToken);
        Log.d("USER", "getUserProfile: " + currentAuthInfo.getEmail());

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<String> call = service.login(this.currentAuthInfo);

        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(userId -> {
                    if (userId.equals("")) {
                        Log.d("LOGIN", "Login failed!!!");
                        Toast.makeText(this, "Incorrect Credentials!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.d("LOGIN", "Successfully logged in " + userId);
                    UserData userData = new UserData(getApplication());
                    userData.setId(userId);
                    onLoginSucceed();
                })
                .exceptionally(ex -> {
                    Log.e("LOGIN", Log.getStackTraceString(new Exception(ex)));
                    return null;
                });
    }

    private void onLoginSucceed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private AuthorizationRequest getAuthenticationRequest(
            AuthorizationResponse.Type type) {
        return new AuthorizationRequest
                .Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email",
                        "user-top-read"})
                .setCampaign("your-campaign-token")
                .build();
    }

    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

}
