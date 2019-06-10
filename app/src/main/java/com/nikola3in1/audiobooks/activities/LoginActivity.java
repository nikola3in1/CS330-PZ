package com.nikola3in1.audiobooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.service.APIClient;
import com.nikola3in1.audiobooks.service.AuthService;

public class LoginActivity extends AppCompatActivity{
    private final Integer RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SignInButton button = findViewById(R.id.sign_in_btn);
        button.setOnClickListener((e)->{
            startActivityForResult(AuthService.getInstance().signIn(), RC_SIGN_IN);
        });
    }


    private void demoSendIdToken(GoogleSignInAccount account){
//        Toast.makeText(this, "tokenId sent for authentication with backend", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Hello "+account.getDisplayName()+", welcome to Audiobooks!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,HomeActivity.class));
    }


    private void sendIdToken(String token){
        //Backend authentication
        APIClient.login(token,this, response -> {
            Log.w("Backend response", response.toString());
            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            sendIdToken(account.getIdToken());

            // Demonstration
            demoSendIdToken(account);

        } catch (ApiException e) {
            Log.w("ERROR", e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        System.out.println("Token id: "+account.getIdToken());
        System.out.println("Account: " + account.getEmail() + " " + account.getDisplayName());
    }

    private void callTest() {
        APIClient.test(this,response -> {
            Log.w("Backend response", response.toString());
        });
        System.out.println("JWT: " + APIClient.accessToken);

    }

}
