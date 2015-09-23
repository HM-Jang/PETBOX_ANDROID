package com.petbox.shop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.petbox.shop.DB.Constants;

/**
 * Created by petbox on 2015-09-22.
 */
public class TestWebView extends AppCompatActivity {

    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);

        webView = (WebView) findViewById(R.id.webview_test);
        webView.loadUrl(Constants.HTTP_URL_MYPAGE);
        webView.getSettings().setJavaScriptEnabled(true);

    }

}
