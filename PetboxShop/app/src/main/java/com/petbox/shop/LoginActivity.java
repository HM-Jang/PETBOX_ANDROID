package com.petbox.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.petbox.shop.DB.Constants;
import com.petbox.shop.Delegate.LoginManagerDelegate;
import com.petbox.shop.Network.LoginManager;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginManagerDelegate {

    private static final String TAG = "LoginAct";

    EditText edit_id, edit_pw;
    ImageButton ibtn_login, ibtn_regist;    // 로그인, 회원가입
    CheckBox chk_auto_login;    // 자동로그인 체크박스

    ProgressDialog pDialog;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("로그인 중");
        pDialog.setMessage("로그인 중..");

        edit_id = (EditText)findViewById(R.id.edit_login_id);
        edit_pw = (EditText)findViewById(R.id.edit_login_pw);

        edit_id.setText("petbox12@nate.com");
        edit_pw.setText("aaaa11");

        ibtn_login = (ImageButton) findViewById(R.id.ibtn_login_start);
        ibtn_login.setOnClickListener(this);

        ibtn_regist = (ImageButton) findViewById(R.id.ibtn_login_regist);
        ibtn_regist.setOnClickListener(this);

        chk_auto_login = (CheckBox) findViewById(R.id.chk_login_auto);
        chk_auto_login.setOnClickListener(this);

        String isChecked = null;

        if(STPreferences.getString(Constants.PREF_KEY_AUTO_LOGIN) == null){

        }else{
            isChecked = STPreferences.getString(Constants.PREF_KEY_AUTO_LOGIN);

            if(isChecked.equals("false")){
                chk_auto_login.setChecked(false);
                //Toast.makeText(getApplicationContext(), "초기상태 : false", Toast.LENGTH_SHORT).show();
            }else if(isChecked.equals("true")){
                chk_auto_login.setChecked(true);
                //Toast.makeText(getApplicationContext(), "초기상태 : true", Toast.LENGTH_SHORT).show();
            }
        }

        System.out.println(isChecked);
    }

    /*
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.REQ_REGIST:
                if (resultCode == Constants.RES_REGIST_LOGIN_SUCCESS) {
                    setResult(Constants.RES_LOGIN_SUCCESS);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.ibtn_login_start: // 로그인
                LoginManager.setDelegate(this);
                LoginManager.getHttpClient();

                Boolean autoLogin = Boolean.parseBoolean(STPreferences.getString(Constants.PREF_KEY_AUTO_LOGIN));
                LoginManager.connectAndLogin(edit_id.getText().toString(), edit_pw.getText().toString(), autoLogin);
               // Toast.makeText(getApplicationContext(), "로그인 버튼 누름", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_login_regist:    //등록
                //Toast.makeText(getApplicationContext(), "등록버튼 누름", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, Constants.REQ_REGIST);
                break;

            case R.id.chk_login_auto:
                //CheckBox chk_v = (CheckBox) v;

                if(chk_auto_login.isChecked()){ // 체크할시
                    //chk_v.setSelected(false);
                    //chk_auto_login.setChecked(false);
                    STPreferences.putString(Constants.PREF_KEY_AUTO_LOGIN, "true");
                    //Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();

                }else{  //체크 해제시
                    //chk_v.setSelected(true);
                    //chk_auto_login.setChecked(true);
                    STPreferences.putString(Constants.PREF_KEY_AUTO_LOGIN, "false");
                    //Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /* LoginManagerDelegate */

    @Override
    public void prevRunningLogin() {
        pDialog.show();
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
        }else if (responseCode == Constants.HTTP_RESPONSE_LOGIN_SUCCESS){
            LoginManager.setIsLogin(true);

            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            CookieSyncManager.getInstance().startSync();

            String cookieString = LoginManager.getCookie().getName() + "=" + LoginManager.getCookie().getValue();
            STPreferences.putString(Constants.PREF_KEY_COOKIE, cookieString);

            cookieManager.setCookie(Constants.HTTP_URL_DOMAIN, cookieString);

            String id = edit_id.getText().toString();
            String pw = edit_pw.getText().toString();

            STPreferences.putString(Constants.PREF_KEY_ID, id);
            STPreferences.putString(Constants.PREF_KEY_PASSWORD, pw);

            /* aes 암호화
            SecretKeySpec sks = null;

            try {
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                sr.setSeed("any data used as random seed".getBytes());
                KeyGenerator kg = KeyGenerator.getInstance("AES");
                kg.init(128, sr);   //128bit 키 생성

                byte[] key =  kg.generateKey().getEncoded();

                String keyStr = Base64.encodeToString(key, Base64.DEFAULT);

                STPreferences.putString(Constants.PREF_KEY_AES_KEY, keyStr);

                Log.e("SplashAct", "AES_KEY(LENGTH) : " + key.length + ",Value : " + key.toString());

                sks = new SecretKeySpec(key, "AES");

            } catch (Exception e) {
                Log.e("SplashAct", "AES encryption error");
            }

            byte[] encodedBytes = null;

            try {
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.ENCRYPT_MODE, sks);
                encodedBytes = c.doFinal(pw.getBytes());

            } catch (Exception e) {
                Log.e(TAG, "AES encryption error");
            }

            STPreferences.putString(Constants.PREF_KEY_ENCODED_BYTE, new String(encodedBytes));
            String encrypted_pw = Base64.encodeToString(encodedBytes, Base64.DEFAULT);

            STPreferences.putString(Constants.PREF_KEY_ID, id);


            Log.i(TAG, "ID : " + id);
            Log.i(TAG, "ENCRYPTED_PW : " + encrypted_pw);

            Log.i(TAG, "PREF ID : " + STPreferences.getString(Constants.PREF_KEY_ID));
            Log.i(TAG, "PREF ENCODED_BYTE : " + STPreferences.getString(Constants.PREF_KEY_ENCODED_BYTE));

            byte[] decodedBytes = null;

            try {
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.DECRYPT_MODE, sks);
                decodedBytes = c.doFinal(encodedBytes);

            } catch (Exception e) {
                Log.e(TAG, "AES decryption error");
            }

            String decrypted_pw = new String(decodedBytes);

            Log.i(TAG, "DECRYPTED_PW : " + decrypted_pw);
            */

            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();

            try {
                Thread.sleep(500);  // CookieManager Sync Time 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setResult(Constants.RES_LOGIN_SUCCESS);
            this.finish();
        }

        pDialog.dismiss();
    }
}
