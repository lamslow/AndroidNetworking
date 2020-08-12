package com.example.lab4_androidnetworking.hoder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;

public class ImageHoder extends RecyclerView.ViewHolder {
    public ConstraintLayout constraintLayout;
    public ImageView imgFlickr;
    public TextView tvView;
    public ImageHoder(@NonNull View itemView) {
        super(itemView);
        imgFlickr=itemView.findViewById(R.id.imgFlickr);
        constraintLayout=itemView.findViewById(R.id.parentConstraint);

    }
}
