package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.lab4_androidnetworking.adapter.ImageGalAdapter;
import com.example.lab4_androidnetworking.sevice.DetailGalSevice;
import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.model.ExampleGal;
import com.example.lab4_androidnetworking.model.PhotoGal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailGalActivity extends AppCompatActivity {
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private static final String USER_ID = "187035358@N07";
    private static final String KEY_TOKEN = "438781d8ef97e68c76a04d5be78b7361";
    private static final String GET_PHOTO_IN_GAL = "flickr.galleries.getPhotos";
    private int page = 0;
    String galleries_ID;
    List<PhotoGal> photoGalList;
    RecyclerView rvListImgGal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gal);
        rvListImgGal=findViewById(R.id.rvListImgGal);
        Bundle bundle= getIntent().getExtras();
        galleries_ID=bundle.getString("galleries_key");
        Log.e("AAAA",galleries_ID+"");
        loadDetailGal(page);
    }
    private void loadDetailGal(int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        DetailGalSevice detailGalSevice=retrofit.create(DetailGalSevice.class);
        detailGalSevice.getListDetailGall(FULL_EXTRAS,galleries_ID ,"1",USER_ID,"json",
                KEY_TOKEN,GET_PHOTO_IN_GAL,page,20).enqueue(new Callback<ExampleGal>() {
            @Override
            public void onResponse(Call<ExampleGal> call, Response<ExampleGal> response) {
                photoGalList=new ArrayList<>();
                int count1=(response.body().getPhotos().getTotal());
                for (int i = 0; i < count1; i++) {
                    PhotoGal photoGal=new PhotoGal();
                    photoGal.setUrlM(response.body().getPhotos().getPhoto().get(i).getUrlM());
                    photoGal.setViews(response.body().getPhotos().getPhoto().get(i).getViews());
                    photoGalList.add(photoGal);
                }
                ImageGalAdapter imageGalAdapter=new ImageGalAdapter(DetailGalActivity.this,photoGalList);
                rvListImgGal.setAdapter(imageGalAdapter);
                StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                rvListImgGal.setLayoutManager(staggeredGridLayoutManager);
            }

            @Override
            public void onFailure(Call<ExampleGal> call, Throwable t) {

            }
        });

    }
}