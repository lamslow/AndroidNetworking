package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.loader.SaveImageLoader;
import com.example.lab4_androidnetworking.loader.SetBGLoader;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView tvTitle;
    private TextView tvViews;
    private Button btnLoad;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);


        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnLoad = findViewById(R.id.btnLoad);
        tvViews = (TextView) findViewById(R.id.tvViews);
        Bundle bundle = getIntent().getExtras();
        String link = bundle.getString("link");
        String view = bundle.getString("views");
        String title = bundle.getString("title");
        tvTitle.setText(title);
        tvViews.setText(view);
        Picasso.get().load(link).into(imgDetail);


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Toast.makeText(DetailsActivity.this, "WRITE_EXTERNAL_STORAGE permission allows us to Access SD CARD app", Toast.LENGTH_LONG).show();

                } else {

                    ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9999);

                }
                SaveImageLoader saveImageLoader = new SaveImageLoader(DetailsActivity.this,title);
                saveImageLoader.execute(link);
            }
        });
        findViewById(R.id.btnSetBackground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,
                        Manifest.permission.SET_WALLPAPER)) {

                    Toast.makeText(DetailsActivity.this, "WRITE_EXTERNAL_STORAGE permission allows us to Access SD CARD app", Toast.LENGTH_LONG).show();

                } else {

                    ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{
                            Manifest.permission.SET_WALLPAPER}, 1234);

                }
                SetBGLoader setBGLoader=new SetBGLoader(DetailsActivity.this);
                setBGLoader.execute(link);
            }
        });
        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent=new ShareLinkContent.Builder()
                        .setQuote("This a beautyful piture")
                        .setContentUrl(Uri.parse(link)).build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(linkContent);
                }
            }
        });
    }



}