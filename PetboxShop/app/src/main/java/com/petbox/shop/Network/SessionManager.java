package com.petbox.shop.Network;

import android.content.Context;
import android.webkit.CookieManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by petbox on 2015-09-21.
 */
public class SessionManager {

    private static final String USER_AGENT = "Mozilla/5.0";
    public final static String SERVER_LOGIN_URL = "http://www.petbox.kr/m2/mem/login.php";
    public final static String SERVER_URL = "www.petbox.kr";
    public final static String SERVER_HOST = "211.233.50.208";
    public final static int SERVER_PORT = 80;

    private static HttpURLConnection connection = null;

    private Context mContext;

    public SessionManager(Context context){
        mContext = context;
    }

    public static HttpURLConnection getConnection(String id, String pw) throws IOException{
        if(connection == null) {
            URL url = new URL(SERVER_LOGIN_URL);
            connection = (HttpURLConnection) url.openConnection();

            String cookieString = null;//CookieManager.getInstance().getCookie(SERVER_URL);

            if(cookieString != null){   // 이전의 쿠키가 있을 시, 쿠키로 세션 연결 시도
                connection.setRequestProperty("Cookie", cookieString);
            }else{  //쿠기가 없을 경우, 정상적인 로그인 과정
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", USER_AGENT);
                //connection.setDoInput(true);
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_id = "m_id="+id+";password="+pw;
                bufferedWriter.write(post_id);
                bufferedWriter.flush();
                bufferedWriter.close();

                int responseCode = connection.getResponseCode();
                System.out.println("POST Response Code : " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST request not worked");
                }


                //bufferedWriter.write();
            }
        }
        return connection;
    }
}
/*
public class SocketManager {

    public final static String SERVER_HOST = "211.233.50.208";
    public final static int SERVER_PORT = 80;

    private static Socket socket;

    public static Socket getSocket() throws IOException{
        if(socket == null)
            socket = new Socket();

        if(!socket.isConnected())
            socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

        return socket;
    }

    public static void closeSocket() throws IOException{
        if(socket != null)
            socket.close();
    }
}

 */