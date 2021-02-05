package com.example.dx_minds_task.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toolbar;


import com.example.dx_minds_task.API.NewsApi;
import com.example.dx_minds_task.Adapter.NewsAdapter;
import com.example.dx_minds_task.Objects.Article;
import com.example.dx_minds_task.Objects.NewsList;
import com.example.dx_minds_task.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

//    DrawerLayout drawerLayout;
  //  Toolbar toolbar;
    //ActionBarDrawerToggle actionBarDrawerToggle;
    //NavigationView navigationView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    //PostAdapter adapter;
    NewsAdapter adapter;
    //List<Item> items = new ArrayList<>();
    List<Article> items = new ArrayList<>();
    Boolean isScrolling = false;
    int currentItems, totalItems, scrolledOutItems;
    String token ="";
    SpinKitView progress;

    public String temporary;
    public String userid;
    public static int cx,cy ;
    private int MODE_PRIVATE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startauthentication();
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.Postlist);


        manager = new LinearLayoutManager(this);
        //adapter = new PostAdapter(this,items); // comment for news
        adapter = new NewsAdapter(this,items);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progress = findViewById(R.id.spin_kit);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrolledOutItems == totalItems))
                {
                    isScrolling = false;
                    getData();
                }
            }
        });
        getData();
    }


    private void getData()
    {
        /*String url = BloggerApi.url + "?key=" +BloggerApi.key;
        if (token !="")
        {
            url = url+"&pageToken="+ token;
        }
        if (token==null)
        {
            return;
        }*/
        String url = NewsApi.url +"?sources=techcrunch"+ "&apiKey=" +NewsApi.key;
        progress.setVisibility(View.VISIBLE);


        Call<NewsList> postList = NewsApi.getService().getPostList(url);
        postList.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {

                NewsList list = response.body();
                token = list.getTotalResults().toString();
                items.addAll(list.getArticles());
                adapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Network Error")
                        .setMessage("Do you Want to..")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                //MainActivity.super.onBackPressed();
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void onBackPressed()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Really exit")
                .setMessage("Are you sure?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        MainActivity.super.onBackPressed();
                    }
                }).setNegativeButton("Cancel", null);
        AlertDialog alert = builder.create();
        alert.show();
    }


}

