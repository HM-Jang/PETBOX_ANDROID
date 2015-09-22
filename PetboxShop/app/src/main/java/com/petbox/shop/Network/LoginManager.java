package com.petbox.shop.Network;

import android.os.AsyncTask;
import com.petbox.shop.Delegate.LoginManagerDelegate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petbox on 2015-09-22.
 */
public class LoginManager {

    private static final String TAG = "LoginManager";
    public final static String SERVER_LOGIN_URL = "http://www.petbox.kr/m2/mem/login_ok.php";

    private static DefaultHttpClient httpClient;
    private static boolean isLogin = false;
    private static LoginManagerDelegate delegate;

    public static HttpClient getHttpClient(){
        if(httpClient == null){
            httpClient = new DefaultHttpClient();
        }
        return httpClient;
    }

    public static void setDelegate(LoginManagerDelegate _delegate){
        delegate = _delegate;
    }

    public static void connectAndLogin(String id, String pw){
        LoginTask task = new LoginTask();
        task.execute(id, pw);
    }

    static class LoginTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){

            if(delegate != null)
                delegate.prevRunning();

            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {

            String id = params[0];
            String pw = params[1];

            int result_code = 0;

            try {
                HttpPost httpPost = new HttpPost(SERVER_LOGIN_URL);

                List<Cookie> cookies = httpClient.getCookieStore().getCookies();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("m_id", id));
                nameValuePairs.add(new BasicNameValuePair("password", pw));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                System.out.println("Entity :  " + EntityUtils.toString(entity));

                result_code = response.getStatusLine().getStatusCode();

                System.out.println("Login form get: " + response.getStatusLine());
                if (entity != null) {
                    entity.consumeContent();
                }

                System.out.println("Post logon cookies:");

                cookies = httpClient.getCookieStore().getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(delegate != null)
                delegate.running();
            //httpClient.getConnectionManager().shutdown();
            return result_code;
        }

        @Override
        protected  void onPostExecute(Integer result){
            if(delegate != null)
                delegate.afterRunning(result);

            delegate = null;

            super.onPostExecute(result);
        }
    }

    /*
    private static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;

        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{

            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    */
}
