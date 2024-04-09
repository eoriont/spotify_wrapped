package com.example.spotifywrappedapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "57630b7e960946ab83c1e0dbda46a4ca";
    public static final String REDIRECT_URI = "spotifywrappedapp://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();

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

            // TODO: For now :(
            UserData userData = new UserData(getApplication());
            userData.setToken(mAccessToken);
            Log.d("Access Token", mAccessToken);
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
        }

        getUserProfile();
    }

    public void getUserProfile() {
        // TODO: make a cache
        final Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/v1/auth/login")
                .post(RequestBody.create(null, ""))
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(
                    Call call,
                    Response response
            ) throws IOException {
                if (response.isSuccessful()) {
                    UserData userData = new UserData(getApplication());
                    userData.setId(response.body().string());
                    onLoginSucceed();
                }
            }
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

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * Shift to the added page
     */
    private void configureNextButton() {
        Button EnterName =  (Button) findViewById(R.id.EnterName);
        EnterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EnterFriend.class));
            }
        });
    }

    private void configureTheNextButton() {
        Button Audio =  (Button) findViewById(R.id.Audio);
        Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MatchAudio.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
}
