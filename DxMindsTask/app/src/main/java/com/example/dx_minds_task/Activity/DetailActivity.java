package com.example.dx_minds_task.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toolbar;

import com.example.dx_minds_task.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class DetailActivity extends AppCompatActivity {

    SpinKitView progressBar;
    Toolbar toolbar;
    WebView webView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressbar);
        //toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.web_view);

        //setSupportActionBar(toolbar);
        //setUpToolbar();
        //navigationView();
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //Toast.makeText(DetailActivity.this, "Page Loading.....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                String javaScript = "javascript:(function(){ var header = document.getElementsByTagName('header');header[0].hidden = 'true';})()";
                webView.loadUrl(javaScript);
            }
        });
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
    /*public void navigationView()
    {
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }*/
/*
    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawelayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }*/
}
