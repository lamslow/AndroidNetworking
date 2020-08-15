package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.adapter.CommentAdapter;
import com.example.lab4_androidnetworking.loader.SaveImageLoader;
import com.example.lab4_androidnetworking.loader.SetBGLoader;
import com.example.lab4_androidnetworking.model.Comment;
import com.example.lab4_androidnetworking.model.Example;
import com.example.lab4_androidnetworking.model.ExampleCMT;
import com.example.lab4_androidnetworking.model.Photo;
import com.example.lab4_androidnetworking.sevice.CommentService;
import com.example.lab4_androidnetworking.sevice.FlickService;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView tvTitle;
    private TextView tvViews;
    private Button btnLoad;
    private EditText edtComment;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private static final String KEY_TOKEN = "438781d8ef97e68c76a04d5be78b7361";
    private static final String GET_COMMENT = "flickr.photos.comments.getList";
    CommentAdapter commentAdapter;
    List<Comment> commentList;
    RecyclerView rvListComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        rvListComment=findViewById(R.id.rvListComment);
        setTitle("Detail Photo");
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);


        edtComment=findViewById(R.id.edtComment);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnLoad = findViewById(R.id.btnLoad);
        tvViews = (TextView) findViewById(R.id.tvViews);
        Bundle bundle = getIntent().getExtras();
        String link = bundle.getString("link");
        String view = bundle.getString("views");
        String title = bundle.getString("title");
        String photo_id = bundle.getString("photo_id");
        int widthM=bundle.getInt("widthM");
        int heightM=bundle.getInt("heightM");
        int widthSq=bundle.getInt("widthSq");
        int heightSq=bundle.getInt("heightSq");
        int widthL=bundle.getInt("widthL");
        int heightL=bundle.getInt("heightL");
        tvTitle.setText(title);
        tvViews.setText(view);
        Picasso.get().load(link).into(imgDetail);

        commentList=new ArrayList<>();
        commentAdapter=new CommentAdapter(this,commentList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvListComment.setLayoutManager(linearLayoutManager);
        rvListComment.setAdapter(commentAdapter);
        loadComment(photo_id);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =new AlertDialog.Builder(DetailsActivity.this);
                v= LayoutInflater.from(DetailsActivity.this).inflate(R.layout.download_view,null,false);
                builder.setView(v);
                builder.setTitle(" Mờii bạn chọn kích thước mong muốn ");
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                Button btnDownload1,btnDownload2,btnDownload3;
                btnDownload1=v.findViewById(R.id.btnDownload1);
                btnDownload2=v.findViewById(R.id.btnDownload2);
                btnDownload3=v.findViewById(R.id.btnDownload3);
                btnDownload1.setText(widthM+" x "+heightM);
                btnDownload2.setText(widthSq+" x "+heightSq);
                btnDownload3.setText(widthL+" x "+heightL);
                btnDownload1.setOnClickListener(new View.OnClickListener() {
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
                        alertDialog.dismiss();
                    }
                });

                btnDownload2.setOnClickListener(new View.OnClickListener() {
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
                        alertDialog.dismiss();
                    }
                });

                btnDownload3.setOnClickListener(new View.OnClickListener() {
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
                        alertDialog.dismiss();
                    }
                });

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

    private void loadComment(String id_photo){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CommentService commentService = retrofit.create(CommentService.class);
        commentService.getListCommnet(id_photo,
                "1","json", KEY_TOKEN, GET_COMMENT).enqueue(new Callback<ExampleCMT>() {
            @Override
            public void onResponse(Call<ExampleCMT> call, Response<ExampleCMT> response) {
                    List<Comment> comments=response.body().getComments().getComment();
                    if (comments.size()==0){
                        commentList.addAll(null);
                    }else {
                        commentList.addAll(comments);
                        commentAdapter.notifyDataSetChanged();
                    }


            }

            @Override
            public void onFailure(Call<ExampleCMT> call, Throwable t) {

            }
        });
    }


    public void addComment(View view) {
        String content=edtComment.getText().toString();
        String author="187035358@N07";
        Comment comment=new Comment();
        comment.setAuthor(author);
        comment.setContent(content);
        commentList.add(comment);
        commentAdapter.notifyDataSetChanged();
    }
}