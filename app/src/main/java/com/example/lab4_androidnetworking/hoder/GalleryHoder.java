package com.example.lab4_androidnetworking.hoder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;

public class GalleryHoder extends RecyclerView.ViewHolder {
    public TextView tvTitleGal;
    public ImageView imgGal;
    public GalleryHoder(@NonNull View itemView) {
        super(itemView);
        tvTitleGal=itemView.findViewById(R.id.tvTitleGal);
        imgGal=itemView.findViewById(R.id.imgGal);

    }
}
