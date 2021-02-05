package com.example.dx_minds_task.Adapter;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dx_minds_task.Activity.DetailActivity;
import com.example.dx_minds_task.Objects.Article;
import com.example.dx_minds_task.R;
import com.google.gson.Gson;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PostViewHolder>{

    private Context context;
    private List<Article> articleList;
    private final String TAG = NewsAdapter.this.getClass().getSimpleName();

    public NewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item, parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        final Article item = articleList.get(position);

        Log.i(TAG, "onBindViewHolder: Items :::" + new Gson().toJson(item));
        holder.posttitle.setText(item.getTitle());
        holder.postDescription.setText(item.getPublishedAt());

        Glide.with(context).load(item.getUrlToImage()).into(holder.postimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("url",item.getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView postimage;
        TextView posttitle;
        TextView postDescription;

        public PostViewHolder(View itemView) {

            super(itemView);
            posttitle = (TextView) itemView.findViewById(R.id.post_title);
            postDescription = (TextView) itemView.findViewById(R.id.post_description);
            postimage = (ImageView)itemView.findViewById(R.id.post_image);

        }
    }
}
