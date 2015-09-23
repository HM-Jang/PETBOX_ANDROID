package com.petbox.shop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.Toast;

import com.petbox.shop.DB.Constants;
import com.petbox.shop.DB.DBConnector;
import com.petbox.shop.Network.LoginManager;

/**
 * Created by petbox on 2015-09-17.
 */
public class SplashActivity extends Activity {

    ImageView iv_splash;
    Thread  timerThread;
    Handler handler;

    boolean isRunning = true;

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
                LoginManager.getHttpClient();

                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                CookieSyncManager.getInstance().startSync();

                String cookieString = STPreferences.getString(Constants.PREF_KEY_COOKIE);

                if(!cookieString.equals("null")){   // stored_member_info Cookie가 존재할 시
                    cookieManager.setCookie(Constants.HTTP_URL_DOMAIN, cookieString);
                }

                Toast.makeText(this, "자동 로그인 기능 ON", Toast.LENGTH_SHORT).show();
            }
            else {
                CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();
                cookieSyncManager.sync();

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

    public void activityFinish(){
        setResult(RESULT_OK);
        this.finish();
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
}
