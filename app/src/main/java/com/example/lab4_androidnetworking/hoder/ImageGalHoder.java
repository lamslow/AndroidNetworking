package com.example.lab4_androidnetworking.hoder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;

public class ImageGalHoder extends RecyclerView.ViewHolder {
    public ConstraintLayout constraintLayout2;
    public ImageView imgGalleries;
    public ImageGalHoder(@NonNull View itemView) {
        super(itemView);
        imgGalleries=itemView.findViewById(R.id.imgGalleries);
        constraintLayout2=itemView.findViewById(R.id.parentConstraint2);

    }
}
