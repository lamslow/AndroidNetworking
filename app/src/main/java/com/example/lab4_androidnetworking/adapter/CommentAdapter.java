package com.example.lab4_androidnetworking.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_androidnetworking.R;
import com.example.lab4_androidnetworking.hoder.CommentHoder;
import com.example.lab4_androidnetworking.model.Comment;
import com.example.lab4_androidnetworking.model.Comments;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentHoder> {
    private Context context;
    private List<Comment> list;

    public CommentAdapter(Context context, List<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment,parent,false);
        CommentHoder comment=new CommentHoder(view);
        return comment;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHoder holder, int position) {
        Comment comment=list.get(position);
        holder.tvComment.setText(Html.fromHtml(comment.getContent()));
        holder.tvAuthor.setText(comment.getAuthor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
