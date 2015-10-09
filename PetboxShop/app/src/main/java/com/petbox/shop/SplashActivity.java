package com.petbox.shop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.petbox.shop.DB.Constants;
import com.petbox.shop.DB.DBConnector;
import com.petbox.shop.Delegate.LoginManagerDelegate;
import com.petbox.shop.Network.LoginManager;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by petbox on 2015-09-17.
 */
public class SplashActivity extends Activity implements LoginManagerDelegate {

    ImageView iv_splash;
    Thread  timerThread;
    Handler handler;

    boolean isRunning = true;
    boolean finishable = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        STPreferences.getPref(getApplicationContext());    // 싱글톤 Preferences 초기 세팅

        if(!STPreferences.isExist(Constants.PREF_KEY_APP_FIRST)) {// 앱 처음 실행 시
            STPreferences.putString(Constants.PREF_KEY_APP_FIRST, "true");
            STPreferences.putString(Constants.PREF_KEY_AUTO_LOGIN, "false");
        }else{
            boolean auto_login = Boolean.parseBoolean(STPreferences.getString(Constants.PREF_KEY_AUTO_LOGIN));

            if(auto_login) {
                LoginManager.setDelegate(this);

                String id = STPreferences.getString(Constants.PREF_KEY_ID);
                String pw = STPreferences.getString(Constants.PREF_KEY_PASSWORD);

                /*
                String encoded_byte = STPreferences.getString(Constants.PREF_KEY_ENCODED_BYTE);
                String keyStr = STPreferences.getString(Constants.PREF_KEY_AES_KEY);


                Log.i("SplashAct", "ID : " + id);
                Log.i("SplashAct", "ENCODED_BYTE : " + encoded_byte);

//                byte[] aes_key = keyStr.getBytes();
                byte[] aes_key = Base64.decode(keyStr, Base64.DEFAULT);
                Log.i("SplashAct", "AES_KEY(Length) : " + aes_key.length + ", Value : " + aes_key.toString());


                SecretKeySpec sks = new SecretKeySpec(aes_key, "AES");

                byte[] decodedBytes = null;

                try {
                    Cipher c = Cipher.getInstance("AES");
                    c.init(Cipher.DECRYPT_MODE, sks);
                    decodedBytes = c.doFinal(encoded_byte.getBytes());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch(Exception e){
                    e.printStackTrace();
                }

                String decrypted_pw = new String(decodedBytes);
                Log.i("SplashAct", "DECRYPTED_PW : " + decrypted_pw);

                LoginManager.connectAndLogin(id, decrypted_pw, true);
                */

                LoginManager.connectAndLogin(id, pw, true);


                /*
                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                CookieSyncManager.getInstance().startSync();

                String cookieString = STPreferences.getString(Constants.PREF_KEY_COOKIE);

                if(!cookieString.equals("null")){   // stored_member_info Cookie가 존재할 시
                    cookieManager.setCookie(Constants.HTTP_URL_DOMAIN, cookieString);
                }
                */

                Toast.makeText(this, "자동 로그인 기능 ON", Toast.LENGTH_SHORT).show();

            }
            else {
                CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();
                cookieSyncManager.sync();

                finishable = true;

                Toast.makeText(this, "자동 로그인 기능 OFF", Toast.LENGTH_SHORT).show();
            }
        }

        iv_splash = (ImageView) findViewById(R.id.iv_splash);

        handler = new Handler(){
            public void handleMessage(Message msg){
                Log.e("SPLASH", "메시지 받음");

                activityFinish();
            }
        };
    }

    public void activityFinish() {
        if (finishable) {
            setResult(RESULT_OK);
            this.finish();
        }else
            finishable = true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onStart(){

        //if(!isRunning){
        // isRunning = true;

            timerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isRunning){
                        try{
                            timerThread.sleep(3000);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        isRunning = false;

                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }
            });
       // }
        timerThread.start();
        super.onStart();
        //FlurryAgent.onStartSession(this, Constants.FLURRY_APIKEY);
    }

    @Override
    public void onStop(){
        //Log.i(TAG, "++ ON STOP ++");
        super.onStop();
       // FlurryAgent.onEndSession(this);
    }

    @Override
    protected void onPause(){
        if(isRunning)
            isRunning = false;

        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
           // Toast.makeText(this, "Back키 누름", Toast.LENGTH_SHORT).show();
            setResult(Constants.RES_SPLASH_CANCEL);
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void prevRunningLogin() {

    }

    @Override
    public void runningLogin() {

    }

    @Override
    public void afterRunningLogin(int responseCode) {
        if(responseCode == Constants.HTTP_RESPONSE_LOGIN_ERROR_NOT_MATCH ){
            Toast.makeText(this, "아이디나 비밀번호를 확인하세요..", Toast.LENGTH_SHORT).show();
        }else if(responseCode == Constants.HTTP_RESPONSE_LOGIN_ERROR_INPUT_TYPE){
            Toast.makeText(this, "아이디나 비밀번호 입력형식이 틀렸습니다..", Toast.LENGTH_SHORT).show();
        }else if(responseCode == Constants.HTTP_RESPONSE_LOGIN_ERROR_DENY){
            Toast.makeText(this, "해당 아이디는 차단되어있습니다..", Toast.LENGTH_SHORT).show();
        }else if (responseCode == Constants.HTTP_RESPONSE_LOGIN_SUCCESS) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();

            LoginManager.setIsLogin(true);
            LoginManager.setDelegate(null);

            if (finishable) {
                setResult(RESULT_OK);
                this.finish();
            }else
                finishable = true;
        }
    }
}
