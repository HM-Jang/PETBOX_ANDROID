package com.petbox.shop.Network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by petbox on 2015-09-21.
 */
public class SocketListener extends Thread {

    private InputStream inputStream;
    private BufferedReader bufferedReader;

    Handler mHandler;
    Context mContext;

    public SocketListener(Context context, Handler handler){
        mContext = context;
        this.mHandler = handler;

        try{
            inputStream = SocketManager.getSocket().getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }catch(IOException e){
            Log.e("SocketListener", e.getMessage());
        }
    }

    @Override
    public void run(){
        super.run();

        while(true){
            try{
                String receivedMsg;

                while((receivedMsg = bufferedReader.readLine()) != null){
                    Log.e("SocketListener", receivedMsg);
                    Message msg = Message.obtain(mHandler, 0, 0, 0, receivedMsg);
                    mHandler.sendMessage(msg);
                }
            }catch(IOException e){
                Log.e("SocketListener", e.getMessage());
            }
        }
    }
}

/* 사용법

public void getMessage()
{
    sl = new SocketListener(getApplicationContext(), mainHandler);
    sl.start();
}
 */
