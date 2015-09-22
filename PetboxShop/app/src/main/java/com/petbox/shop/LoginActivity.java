package com.petbox.shop;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.petbox.shop.DB.Constants;
import com.petbox.shop.Delegate.LoginManagerDelegate;
import com.petbox.shop.Network.LoginManager;
import com.petbox.shop.Network.SessionManager;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginManagerDelegate {

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

        ibtn_login = (ImageButton) findViewById(R.id.ibtn_login_start);
        ibtn_login.setOnClickListener(this);

        ibtn_regist = (ImageButton) findViewById(R.id.ibtn_login_regist);
        ibtn_regist.setOnClickListener(this);

        chk_auto_login = (CheckBox) findViewById(R.id.chk_login_auto);
        chk_auto_login.setOnClickListener(this);

        String isChecked = STPreferences.getString(Constants.PREF_KEY_AUTO_LOGIN);

        System.out.println(isChecked);

        if(isChecked.equals("false")){
            chk_auto_login.setChecked(false);
            //Toast.makeText(getApplicationContext(), "초기상태 : false", Toast.LENGTH_SHORT).show();
        }else if(isChecked.equals("true")){
            chk_auto_login.setChecked(true);
            //Toast.makeText(getApplicationContext(), "초기상태 : true", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }
    */
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            pDialog.dismiss();
            String result = msg.getData().getString("RESULT");

            if(result.equals("success")){
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }

    };

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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.ibtn_login_start: // 로그인
                LoginManager.setDelegate(this);
                LoginManager.getHttpClient();
                LoginManager.connectAndLogin(edit_id.getText().toString(), edit_pw.getText().toString());
               // Toast.makeText(getApplicationContext(), "로그인 버튼 누름", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibtn_login_regist:    //등록
                Toast.makeText(getApplicationContext(), "등록버튼 누름", Toast.LENGTH_SHORT).show();
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
    public void prevRunning() {
        pDialog.show();
    }

    @Override
    public void running() {

    }

    @Override
    public void afterRunning(int responseCode) {

        if(responseCode == Constants.HTTP_RESPONSE_LOGIN_ERROR_NOT_MATCH ){
            Toast.makeText(this, "아이디나 비밀번호를 확인하세요..", Toast.LENGTH_SHORT).show();
        }else if(responseCode == Constants.HTTP_RESPONSE_LOGIN_ERROR_INPUT_TYPE){
            Toast.makeText(this, "아이디나 비밀번호 입력형식이 틀렸습니다..", Toast.LENGTH_SHORT).show();
        }else if(responseCode == Constants.HTTP_RESPONSE_LOGIN_ERROR_DENY){
            Toast.makeText(this, "해당 아이디는 차단되어있습니다..", Toast.LENGTH_SHORT).show();
        }else if (responseCode == Constants.HTTP_RESPONSE_LOGIN_SUCCESS){
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
        }

        pDialog.dismiss();
    }
}
