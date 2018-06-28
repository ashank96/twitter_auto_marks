

/* Author: Ashank Bharati 28-06-2018*/

package com.example.aloofwillow.chillpilltwitter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class TweetsActivity extends AppCompatActivity {
    Button signoutButton,dashboardButton;
    TwitterLoginButton loginButton;
    FirebaseAuth mAuth;
    public static FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                getString(R.string.consumer_key),
                getString(R.string.consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();
        Twitter.initialize(twitterConfig);

        setContentView(R.layout.activity_main);

        signoutButton=(Button)findViewById(R.id.button_twitter_signout);
        loginButton=(TwitterLoginButton)findViewById(R.id.button_twitter_login);
        dashboardButton=(Button)findViewById(R.id.button_dashboard);
        initialize_buttons();
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager<TwitterSession> sessionManager=TwitterCore.getInstance().getSessionManager();
                if(sessionManager.getActiveSession()!=null){
                    sessionManager.clearActiveSession();
                    mAuth.signOut();
                    initialize_buttons();
                }
            }
        });
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                Intent intent=new Intent(TweetsActivity.this,DashboardActivity.class);
                intent.putExtra("user",getTwitterName(user));
                intent.putExtra("authtoken",session.getAuthToken().token);
                intent.putExtra("authtokenSecret",session.getAuthToken().secret);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user!=null) {
            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
            Intent intent=new Intent(TweetsActivity.this,DashboardActivity.class);
            intent.putExtra("user",getTwitterName(user));
            intent.putExtra("authtoken",session.getAuthToken().token);
            intent.putExtra("authtokenSecret",session.getAuthToken().secret);
            startActivity(intent);
        }
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null) {
                    Toast.makeText(TweetsActivity.this, "Logged In as "+user.getDisplayName(), Toast.LENGTH_SHORT).show();

                    }

            }
        };


        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(TweetsActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();
                handleTwitterSession(result.data);
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                Intent intent=new Intent(TweetsActivity.this,DashboardActivity.class);
                intent.putExtra("user",getTwitterName(user));
                intent.putExtra("authtoken",session.getAuthToken().token);
                intent.putExtra("authtokenSecret",session.getAuthToken().secret);
                startActivity(intent);
                initialize_buttons();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(TweetsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                initialize_buttons();
            }
        });

    }


    private void initialize_buttons() {
        if (TwitterCore.getInstance().getSessionManager().getActiveSession() == null) {
            loginButton.setVisibility(View.VISIBLE);
            signoutButton.setVisibility(View.GONE);
            dashboardButton.setVisibility(View.GONE);
        } else {
            loginButton.setVisibility(View.GONE);
            signoutButton.setVisibility(View.VISIBLE);
            dashboardButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the Twitter login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void handleTwitterSession(final TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(TweetsActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                             user = mAuth.getCurrentUser();


                            initialize_buttons();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(TweetsActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                            initialize_buttons();
                        }
                    }
                });
    }

    String getTwitterName(FirebaseUser user){
        String name=null;
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                 name = profile.getDisplayName();
            }
        }

        return name;
    }


}
