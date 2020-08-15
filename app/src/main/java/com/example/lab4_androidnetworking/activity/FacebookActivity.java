package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_androidnetworking.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView tvData;
    GraphRequest graphRequest;
    private ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        setTitle("Facebook Login");
        imgAvatar=findViewById(R.id.imgAvatar);
        FacebookSdk.sdkInitialize(getApplicationContext());
        tvData=findViewById(R.id.tvData);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                getProfile(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(FacebookActivity.this, "Bạn đã hủy đăng nhập", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(FacebookActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void getProfile(AccessToken accessToken){
        Profile profile=Profile.getCurrentProfile();
        if (profile!=null){
            String name=profile.getName();
            String pic_profile= profile.getProfilePictureUri(120,100).toString();
            Picasso.get().load(pic_profile).into(imgAvatar);
            tvData.setText(name);
        }
        graphRequest=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String email=object.getString("email");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        graphRequest.executeAsync();
    }
}