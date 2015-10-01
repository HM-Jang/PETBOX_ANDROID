package com.petbox.shop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.petbox.shop.DB.Constants;

/**
 * Created by petbox on 2015-09-22.
 */
public class CartWebView extends AppCompatActivity implements WebView.OnKeyListener{

    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);

        webView = (WebView) findViewById(R.id.webview_test);
        //webView.loadUrl(Constants.HTTP_URL_CART);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(Constants.HTTP_URL_CART);
        webView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(event.getAction() != KeyEvent.ACTION_DOWN)
            return true;

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                this.onBackPressed();
            }
            return true;
        }

        return false;
    }
}
