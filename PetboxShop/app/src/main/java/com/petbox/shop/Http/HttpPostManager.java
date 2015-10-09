package com.petbox.shop.Http;

import android.os.AsyncTask;

import com.petbox.shop.DB.Constants;
import com.petbox.shop.Delegate.HttpPostDelegate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petbox on 2015-10-08.
 */
public class HttpPostManager {
    private static final String TAG = "HttpPostManager";

    private DefaultHttpClient httpClient;
    private HttpPostDelegate delegate;

    public HttpPostManager(){
        httpClient = new DefaultHttpClient();
    }

    public HttpPostManager(HttpPostDelegate delegate){
        httpClient = new DefaultHttpClient();
        this.delegate = delegate;
    }

    public void setDelegate(HttpPostDelegate delegate){
        this.delegate = delegate;
    }

    public void start(){
        HttpPostTask task = new HttpPostTask();
        task.execute();
    }

    class HttpPostTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected void onPreExecute(){
            if(delegate != null)
                delegate.prevRunningHttpPost();

            super.onPreExecute();

        }


        @Override
        protected Integer doInBackground(Void... params) {

           int result_code = 0;

            String postURL = delegate.getPostUrl();
            HttpPost httpPost = new HttpPost(postURL);

            List<NameValuePair> nameValuePairs = delegate.getNameValuePairs();
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                result_code = response.getStatusLine().getStatusCode();


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(delegate != null)
                delegate.runningHttpPost();
            //httpClient.getConnectionManager().shutdown();
            return result_code;
        }

        @Override
        protected  void onPostExecute(Integer result){
            if(delegate != null)
                delegate.afterRunningHttpPost(result);

            delegate = null;

            super.onPostExecute(result);
        }
    }



}
