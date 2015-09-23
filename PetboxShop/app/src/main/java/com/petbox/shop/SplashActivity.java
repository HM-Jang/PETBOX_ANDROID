package com.petbox.shop;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.Toast;

import com.petbox.shop.DB.DBConnector;


/**
 * Created by petbox on 2015-09-17.
 */
public class SplashActivity extends Activity {

    private static final int RES_SPLASH_CANCEL = 0;

    ImageView iv_splash;
    Thread  timerThread;
    Handler handler;

    boolean isRunning = true;

    private static final int RES_SPLASH_CANCEL = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
            setResult(RES_SPLASH_CANCEL);
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
