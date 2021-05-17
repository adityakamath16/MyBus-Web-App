package com.args.ios.mybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class additional_web extends AppCompatActivity {

    WebView webView;
    ProgressBar web_progressbar;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_web);

        // for toolbar
        // for toolbar
        toolbar = findViewById(R.id.web_view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //when back button on toolbar is clicked
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                if(webView.canGoBack())
                {
                    webView.goBack();
                }
                else {
                    finish();
                }

            }
        });

        webView = findViewById(R.id.webload);
        webView.setVisibility(View.VISIBLE);

        web_progressbar = findViewById(R.id.web_progress);

        final String site = getIntent().getExtras().getString("web");

        final WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            //when web view starts loading
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                web_progressbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                web_progressbar.setVisibility(View.GONE);
                //no_con_image.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
            //in case the web page is not available error occurs
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {


            }
        });
        webView.loadUrl(""+site);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else {
            finish();
        }
    }
}