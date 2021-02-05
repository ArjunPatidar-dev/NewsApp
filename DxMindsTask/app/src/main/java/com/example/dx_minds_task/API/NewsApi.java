package com.example.dx_minds_task.API;

import com.example.dx_minds_task.Objects.NewsList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class NewsApi {

    public static final String key = "f892e83989da4789a14e42d63f8a1619"; // news API key
    //public static final String url = "http://newsapi.org/v2/everything/?q=bitcoin/&from=2020-12-06/&sortBy=publishedAt/";
    public static final String url = "http://newsapi.org/v2/top-headlines/";

    public static PostService postService = null;
    public static PostService getService()
    {
        if (postService==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService = retrofit.create(PostService.class);
        }
        return postService;
    }


    public interface PostService
    {
        @GET
        Call<NewsList> getPostList(@Url String url);
    }
}
