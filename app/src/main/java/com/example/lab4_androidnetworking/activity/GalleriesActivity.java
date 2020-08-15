package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.example.lab4_androidnetworking.model.Galleries;
import com.example.lab4_androidnetworking.sevice.AlbumSevice;
import com.example.lab4_androidnetworking.adapter.ListGallAdapter;
import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.model.ExampleGall;
import com.example.lab4_androidnetworking.model.Gallery;
import com.example.lab4_androidnetworking.model.PrimaryPhotoExtras;
import com.example.lab4_androidnetworking.model.Title;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleriesActivity extends AppCompatActivity {
    RecyclerView rvListGall;
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private static final String USER_ID = "187035358@N07";
    private static final String KEY_TOKEN = "438781d8ef97e68c76a04d5be78b7361";
    private static final String GET_GALL = "flickr.galleries.getList";
    private int page = 0;
    ListGallAdapter listGallAdapter;
    private List<Gallery> galleryList;

    private SwipeRefreshLayout spFreshFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleries);
        setTitle("My Galleries");
        rvListGall=findViewById(R.id.rvListGall);
        spFreshFav=findViewById(R.id.spFreshFav);
        galleryList=new ArrayList<>();
        listGallAdapter=new ListGallAdapter(this,galleryList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvListGall.setAdapter(listGallAdapter);
        rvListGall.setLayoutManager(linearLayoutManager);
        galleryList.clear();
        loadImage(page);

        spFreshFav.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GalleriesActivity.this.page=1;
                galleryList.clear();
                listGallAdapter.notifyDataSetChanged();
                loadImage(page);
            }
        });
    }

    private void loadImage(int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlbumSevice flickService = retrofit.create(AlbumSevice.class);
        flickService.getListGall(FULL_EXTRAS,
                "1", USER_ID, "json", KEY_TOKEN, GET_GALL, page,
                20).enqueue(new Callback<ExampleGall>() {
            @Override
            public void onResponse(Call<ExampleGall> call, Response<ExampleGall> response) {
                spFreshFav.setRefreshing(false);
                List<Gallery> galleries=response.body().getGalleries().getGallery();
                galleryList.addAll(galleries);
                listGallAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ExampleGall> call, Throwable t) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}