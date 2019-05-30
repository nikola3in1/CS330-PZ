package com.nikola3in1.audiobooks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.service.APIClient;
import com.nikola3in1.audiobooks.service.AuthService;

public class StartingActivity extends AppCompatActivity {

    private final Integer TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        GoogleSignInAccount account = AuthService.getInstance().checkIfLoggedIn(this);
        AuthService.getInstance().init(this);

        Context ctx = this;
        new Handler().postDelayed(() -> {
            if (account != null) {
                //user is already signed in, fetch access token and opet home
                APIClient.login(account.getIdToken()
                        , ctx, response -> {
                            Log.w("Backend response", response.toString());
                            startActivity(new Intent(ctx, HomeActivity.class));
                        });
            } else {
                //user is not signed in, open sign in page
                startActivity(new Intent(ctx, LoginActivity.class));
            }

            finish();
        }, TIME_OUT);
    }
}
