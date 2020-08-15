package com.example.lab4_androidnetworking.adapter;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.activity.DetailsActivity;
import com.example.lab4_androidnetworking.hoder.ImageHoder;
import com.example.lab4_androidnetworking.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageHoder> {
    Context context;
    ArrayList<Photo> photoList;

    public ImageAdapter(Context context, ArrayList<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ImageHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image,parent,false);
        ImageHoder imageHoder=new ImageHoder(view);
        return imageHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHoder holder, int position) {

            try {
                Photo photo=photoList.get(position);
                String link=photo.getUrlM();
                Picasso.get().load(link).into(holder.imgFlickr);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("photo_id",photo.getId());
                        bundle.putString("link",link);
                        bundle.putString("title",photo.getTitle());
                        bundle.putString("views",photo.getViews());
                        bundle.putInt("widthM", photo.getWidthM());
                        bundle.putInt("heightM",photo.getHeightM());
                        bundle.putInt("widthSq", photo.getWidthSq());
                        bundle.putInt("heightSq",photo.getHeightSq());
                        bundle.putInt("widthL", photo.getWidthL());
                        bundle.putInt("heightL",photo.getHeightL());
                        Intent intent=new Intent(context, DetailsActivity.class);
                        intent.putExtras(bundle);
                        ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation
                                ((Activity) context,holder.imgFlickr, ViewCompat.getTransitionName(holder.imgFlickr));
                        context.startActivity(intent,activityOptionsCompat.toBundle());
                    }
                });

                ConstraintSet constraintSet =new ConstraintSet();

                String imageRatio = String.format("%d:%d",photo.getWidthM(),photo.getHeightM());
                constraintSet.clone(holder.constraintLayout);
                constraintSet.setDimensionRatio(holder.imgFlickr.getId(), imageRatio);
                constraintSet.applyTo(holder.constraintLayout);
            }catch (Exception e){
                e.printStackTrace();
            }


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

}
