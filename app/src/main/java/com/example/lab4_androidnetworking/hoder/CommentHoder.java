package com.example.lab4_androidnetworking.hoder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;

public class CommentHoder extends RecyclerView.ViewHolder {
    public TextView tvAuthor,tvComment;
    public CommentHoder(@NonNull View itemView) {
        super(itemView);
        tvComment=itemView.findViewById(R.id.tvCommment);
        tvAuthor=itemView.findViewById(R.id.tvAuthor);
    }
}
