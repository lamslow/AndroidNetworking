package com.example.lab4_androidnetworking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.hoder.ImageGalHoder;
import com.example.lab4_androidnetworking.model.PhotoGal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageGalAdapter extends RecyclerView.Adapter<ImageGalHoder> {
    Context context;
    List<PhotoGal> list;

    public ImageGalAdapter(Context context, List<PhotoGal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ImageGalHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_gal,parent,false);
        ImageGalHoder imageGalHoder=new ImageGalHoder(view);
        return imageGalHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGalHoder holder, int position) {
            PhotoGal photoGal=list.get(position);
            String link=photoGal.getUrlM();
        Picasso.get().load(link).into(holder.imgGalleries);

//        ConstraintSet constraintSet1 =new ConstraintSet();
//        String imageRatio=String.format("%d:%d",photoGal.getWidthM(),photoGal.getHeightM());
//        constraintSet1.clone(holder.constraintLayout2);
//        constraintSet1.setDimensionRatio(holder.imgGalleries.getId(), imageRatio);
//        constraintSet1.applyTo(holder.constraintLayout2);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
