package com.petbox.shop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

import com.petbox.shop.DB.Constants;

/**
 * Created by petbox on 2015-09-22.
 */
public class CartWebFragment extends WebViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = super.onCreateView(inflater, container, savedInstanceState);

        WebView webView = getWebView();
        webView.loadUrl(Constants.HTTP_URL_CART);
        webView.getSettings().setJavaScriptEnabled(true);

        return view;
    }


}
