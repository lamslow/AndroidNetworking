package com.example.lab4_androidnetworking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.activity.DetailGalActivity;
import com.example.lab4_androidnetworking.hoder.GalleryHoder;
import com.example.lab4_androidnetworking.model.Gallery;
import com.example.lab4_androidnetworking.model.Title;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListGallAdapter extends RecyclerView.Adapter<GalleryHoder> {
    Context context;
    List<Gallery> galleryList;

    public ListGallAdapter(Context context,List<Gallery> galleryList) {
        this.context = context;
        this.galleryList=galleryList;
    }

    @NonNull
    @Override
    public GalleryHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_gall,parent,false);
        GalleryHoder galleryHoder=new GalleryHoder(view);
        return galleryHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHoder holder, int position) {

        Gallery gallery=galleryList.get(position);
        holder.tvTitleGal.setText(gallery.getTitle().getContent());
        String linkk=gallery.getPrimaryPhotoExtras().getUrlM();
        Log.e("Link",linkk+"");
        Picasso.get().load(linkk).into(holder.imgGal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("galleries_key",gallery.getGalleryId());
                Intent intent=new Intent(context,DetailGalActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
}
