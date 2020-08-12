package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
    private List<PrimaryPhotoExtras> primaryPhotoExtrasList;
    private List<Title> titleList;
    private List<Gallery> galleryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleries);
        rvListGall=findViewById(R.id.rvListGall);

        loadImage(page);
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
                //          swFresh.setRefreshing(false);
                primaryPhotoExtrasList=new ArrayList<>();
                titleList=new ArrayList<>();
                galleryList=new ArrayList<>();
                int count=response.body().getGalleries().getTotal();
                for (int i=0;i<count;i++){
                    Title title=new Title();
                    PrimaryPhotoExtras primaryPhotoExtras=new PrimaryPhotoExtras();
                    Gallery gallery=new Gallery();
                    title.setContent(response.body().getGalleries().getGallery().get(i).getTitle().getContent());
                    Log.e("abc",response.body().getGalleries().getGallery().get(i).getTitle().getContent()+"") ;
                   //primaryPhotoExtras.setUrlM(response.body().getGalleries().getGallery().get(i).getPrimaryPhotoExtras().getUrlM());
                    gallery.setGalleryId(response.body().getGalleries().getGallery().get(i).getGalleryId());
                    galleryList.add(gallery);
                    titleList.add(title);
                }
                ListGallAdapter listGallAdapter=new ListGallAdapter(GalleriesActivity.this,titleList,galleryList);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(GalleriesActivity.this);
                rvListGall.setAdapter(listGallAdapter);
                rvListGall.setLayoutManager(linearLayoutManager);

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