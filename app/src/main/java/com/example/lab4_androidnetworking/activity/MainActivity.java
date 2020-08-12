package com.example.lab4_androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lab4_androidnetworking.sevice.FlickService;
import com.example.lab4_androidnetworking.adapter.ImageAdapter;
import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.model.Example;
import com.example.lab4_androidnetworking.model.Photo;
import com.example.lab4_androidnetworking.sevice.SearchService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ArrayList<Photo> list;
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private static final String USER_ID = "187035358@N07";
    private static final String KEY_TOKEN = "438781d8ef97e68c76a04d5be78b7361";
    private static final String GET_FAVO = "flickr.favorites.getList";

    private static final String GET_SEARCH = "flickr.photos.search";
    private int page = 1;
    private int pageSearch=1;
    ImageAdapter imageAdapter;

    private RecyclerView rvList;
    private SwipeRefreshLayout swFresh;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        swFresh=findViewById(R.id.spFresh);

        list=new ArrayList<>();
        Log.e("size111",list.size()+"");
        imageAdapter = new ImageAdapter(MainActivity.this, list);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvList.setLayoutManager(staggeredGridLayoutManager);
        rvList.setAdapter(imageAdapter);
        rvList.setItemAnimator(null);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvList.getRecycledViewPool().clear();
        list.clear();
        loadImage(page);

        swFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            MainActivity.this.page=1;
            list.clear();
            imageAdapter.notifyDataSetChanged();
            loadImage(MainActivity.this.page);
                rvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        MainActivity.this.page++;
                        loadImage(MainActivity.this.page++);
                    }
                });
            }
        });
        rvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                MainActivity.this.page++;
                loadImage(MainActivity.this.page++);

            }
        });



    }





    private void loadImage(int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickService flickService = retrofit.create(FlickService.class);
        flickService.getListFavo(FULL_EXTRAS,
                "1", USER_ID, "json", KEY_TOKEN, GET_FAVO, page,
                10).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                swFresh.setRefreshing(false);
                List<Photo> photos=response.body().getPhotos().getPhoto();
                    list.addAll(photos);
                    imageAdapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    private void searchImage(int page,String text){
        if (text== null){
            Toast.makeText(this, "Bạn chưa nhập gì à", Toast.LENGTH_SHORT).show();
        }else {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchService searchService = retrofit.create(SearchService.class);
        searchService.getSearchList(FULL_EXTRAS,"1","any",text,"json",
                KEY_TOKEN,GET_SEARCH,page,10
        ).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                swFresh.setRefreshing(false);
                if (Integer.parseInt(response.body().getPhotos().getTotal()) ==0){

                    List<Photo> photos=new ArrayList<>();
                    list.addAll(photos);
                    imageAdapter.notifyDataSetChanged();
                }else {
                    list.clear();
                    List<Photo> photos=response.body().getPhotos().getPhoto();
                    Log.e("SIZE",response.body().getPhotos().getTotal()+"");
                    list.addAll(photos);
                    imageAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        list=new ArrayList<>();
        imageAdapter = new ImageAdapter(MainActivity.this, list);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvList.setLayoutManager(staggeredGridLayoutManager);
        rvList.setAdapter(imageAdapter);
        rvList.setItemAnimator(null);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvList.getRecycledViewPool().clear();
        list.clear();
        String text=newText.toLowerCase().trim();
        if (text.length()==0){
            Toast.makeText(this, "Bạn chưa điền gì", Toast.LENGTH_SHORT).show();
        }else {
            searchImage(pageSearch,text);
        }

        return true;
    }
}