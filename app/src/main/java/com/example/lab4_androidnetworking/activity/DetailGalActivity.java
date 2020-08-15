package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ImageGalAdapter imageGalAdapter;
    String galleries_ID;
    List<PhotoGal> photoGalList;
    RecyclerView rvListImgGal;
    private SwipeRefreshLayout spFreshPhotoFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gal);
        setTitle("My Detail Galleries");
        rvListImgGal=findViewById(R.id.rvListImgGal);
        spFreshPhotoFav=findViewById(R.id.spFreshPhotoFav);
        Bundle bundle= getIntent().getExtras();
        galleries_ID=bundle.getString("galleries_key");
        photoGalList=new ArrayList<>();
        imageGalAdapter=new ImageGalAdapter(this,photoGalList);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvListImgGal.setAdapter(imageGalAdapter);
        rvListImgGal.setLayoutManager(staggeredGridLayoutManager);
        rvListImgGal.setItemAnimator(null);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvListImgGal.getRecycledViewPool().clear();
        photoGalList.clear();

        loadDetailGal(page);

        spFreshPhotoFav.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DetailGalActivity.this.page=1;
                photoGalList.clear();
                imageGalAdapter.notifyDataSetChanged();
                loadDetailGal(DetailGalActivity.this.page);
            }
        });

        rvListImgGal.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                DetailGalActivity.this.page=page;
                Log.e("page",page+"");
                loadDetailGal(page+1);



            }
        });
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
                spFreshPhotoFav.setRefreshing(false);
                List<PhotoGal> photoGals=response.body().getPhotos().getPhoto();
                photoGalList.addAll(photoGals);
                imageGalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ExampleGal> call, Throwable t) {

            }
        });

    }
}