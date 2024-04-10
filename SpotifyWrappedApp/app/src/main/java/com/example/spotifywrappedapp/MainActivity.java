package com.example.spotifywrappedapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifywrappedapp.apiservices.BackendService;
import com.example.spotifywrappedapp.utils.BackendServiceSingleton;
import com.example.spotifywrappedapp.utils.RetrofitUtils;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.io.IOException;

//import okhttp3.Call;
import okhttp3.Callback;
//import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
//import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "57630b7e960946ab83c1e0dbda46a4ca";
    public static final String REDIRECT_URI = "spotifywrappedapp://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();

//    public static JSONObject userProfile;

    private String mAccessToken, mAccessCode;
    private Call mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signInBtn = findViewById(R.id.buttonAuthSignIn);
        signInBtn.setOnClickListener((v) -> {
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

    public void getCode() {
        final AuthorizationRequest request =
                getAuthenticationRequest(
                        AuthorizationResponse.Type.CODE);
        AuthorizationClient
                .openLoginActivity(
                        MainActivity.this,
                        AUTH_CODE_REQUEST_CODE,
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

        BackendService service = BackendServiceSingleton.getBackendService();
        Call<String> call = service.login("Bearer " + mAccessToken);

        RetrofitUtils.toCompletableFuture(call)
                .thenAccept(userId -> {
                    Log.d("LOGIN", "Successfully logged in " + userId);
                    UserData userData = new UserData(getApplication());
                    userData.setId(userId);
                    onLoginSucceed();
                })
                .exceptionally(ex -> {
                    Log.e("LOGIN", Log.getStackTraceString(new Exception(ex)));
//                    Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private void onLoginSucceed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
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

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
}
