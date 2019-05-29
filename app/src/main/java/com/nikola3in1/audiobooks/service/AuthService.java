package com.nikola3in1.audiobooks.service;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.nikola3in1.audiobooks.R;

public class AuthService{
    private GoogleSignInClient mGoogleSignInClient;

    public void init(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.server_client_id))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public GoogleSignInAccount checkIfLoggedIn(Context context){
        return GoogleSignIn.getLastSignedInAccount(context);
    }


    public Intent signIn() {
        return mGoogleSignInClient.getSignInIntent();
    }

    public void singOut() {
        mGoogleSignInClient.signOut();
    }

    private AuthService() {
    }
    private static AuthService instance = new AuthService();
    public static AuthService getInstance() {
        return instance;
    }

}
