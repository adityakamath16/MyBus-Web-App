package com.args.ios.mybus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar web_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview_main);
        web_progressbar = findViewById(R.id.web_progress);
        webView.setVisibility(View.VISIBLE);

        web_progressbar = findViewById(R.id.web_progress);


        final WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);

        PaymentInterface paymentInterface = new PaymentInterface();
        webView.addJavascriptInterface(paymentInterface, "PaymentInterface");
        String html = "<html><script> var options = {callback_url: my_url',redirect: true}</script></html>";
        webView.loadDataWithBaseURL("base_url",html , "text/html", "utf-8", null);

        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            //when web view starts loading
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equals("https://mybus-argsin.web.app/privacypolicy")||url.equals("https://mybus-argsin.web.app/termsandcondition")||url.equals("https://mybus-argsin.web.app/busroutes")||url.equals("https://mybus-argsin.web.app/developers"))
                {
                    String site = ""+url;
                    webView.loadUrl("https://mybus-argsin.web.app/");
                    Intent web_direct = new Intent(MainActivity.this, additional_web.class);
                    web_direct.putExtra("web",site);
                    startActivity(web_direct);


                }
                if(url.startsWith("whatsapp://"))
                {
                    webView.loadUrl("https://mybus-argsin.web.app/");
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "MyBus, an all in one app for bus tracking and booking : https://mybus-argsin.web.app");
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }
                else {
                    //Toast.makeText(MainActivity.this, ""+url, Toast.LENGTH_SHORT).show();
                    web_progressbar.setVisibility(View.VISIBLE);
                    super.onPageStarted(view, url, favicon);
                }

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
        webView.setWebChromeClient(new WebChromeClient() {
            //Other methods for your WebChromeClient here, if needed...
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("MyBus")
                        .setMessage(message)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                result.confirm();
                return true;
            }
        });
        webView.loadUrl("https://mybus-argsin.web.app/");

    }



    //when back is pressed
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

    class PaymentInterface{
        @JavascriptInterface
        public void success(String data)
        {

        }
        @JavascriptInterface
    public void error(String data)
        {

        }
    }
}