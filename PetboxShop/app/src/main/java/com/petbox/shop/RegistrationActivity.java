package com.petbox.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_email, edit_name, edit_pw, edit_pw_re;

    EditText edit_phone1, edit_phone2, edit_phone3, edit_allergy_etc, edit_recommend_id;
    CheckBox chk_dog, chk_cat, chk_etc, chk_agree_email, chk_agree_sms;
    Spinner spin_varieties, spin_age, spin_weight, spin_allergy;
    Button btn_regist;

    TextView btn_use_contract, btn_info_contract, tv_use_contract, tv_info_contract;
    FrameLayout frame_use_contract, frame_info_contract;

    boolean isVisible_use = false; // false : GONE, true : VISIBLE
    boolean isVisible_info = false;

    ScrollView sc_main, sc_use_constract, sc_info_constract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edit_email = (EditText) findViewById(R.id.edit_regist_email);
        edit_name = (EditText) findViewById(R.id.edit_regist_name);
        edit_pw = (EditText) findViewById(R.id.edit_regist_pw);
        edit_pw_re = (EditText) findViewById(R.id.edit_regist_pw_re);

        edit_phone1 = (EditText) findViewById(R.id.edit_regist_phone1);
        edit_phone2 = (EditText) findViewById(R.id.edit_regist_phone2);
        edit_phone3 = (EditText) findViewById(R.id.edit_regist_phone3);


        chk_dog = (CheckBox) findViewById(R.id.chk_regist_dog);
        chk_cat = (CheckBox) findViewById(R.id.chk_regist_cat);
        chk_etc = (CheckBox) findViewById(R.id.chk_regist_etc);

        spin_varieties = (Spinner) findViewById(R.id.spin_regist_varieties);
        spin_age = (Spinner) findViewById(R.id.spin_regist_age);
        spin_weight = (Spinner) findViewById(R.id.spin_regist_weight);
        spin_allergy = (Spinner) findViewById(R.id.spin_regist_allergy);

        edit_allergy_etc = (EditText) findViewById(R.id.edit_regist_allergy_etc);
        edit_recommend_id = (EditText) findViewById(R.id.edit_regist_recommend_id);

        chk_agree_email = (CheckBox) findViewById(R.id.chk_agree_email);
        chk_agree_sms = (CheckBox) findViewById(R.id.chk_agree_sms);

        btn_regist = (Button) findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(this);

        btn_use_contract = (TextView) findViewById(R.id.btn_regist_use_contract);
        btn_use_contract.setOnClickListener(this);
        tv_use_contract = (TextView) findViewById(R.id.tv_regist_use_contract);
        setTextFromTxt(tv_use_contract, R.raw.use_contract);
        frame_use_contract = (FrameLayout) findViewById(R.id.frame_regist_use_contract);

        btn_info_contract = (TextView) findViewById(R.id.btn_regist_info_contract);
        btn_info_contract.setOnClickListener(this);
        tv_info_contract = (TextView) findViewById(R.id.tv_regist_info_contract);
        setTextFromTxt(tv_info_contract, R.raw.info_contract);
        frame_info_contract = (FrameLayout) findViewById(R.id.frame_regist_info_contract);

        sc_main = (ScrollView) findViewById(R.id.sc_regist_main);
        //부모 스크롤의 자식 터치이벤트의 방해 허용
        sc_main.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.sc_regist_use_constract).getParent().requestDisallowInterceptTouchEvent(false);
                findViewById(R.id.sc_regist_info_constract).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        sc_use_constract = (ScrollView)findViewById(R.id.sc_regist_use_constract);
        //부모 스크롤의 자식 터치이벤트의 방해 차단
        sc_use_constract.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        sc_info_constract = (ScrollView) findViewById(R.id.sc_regist_info_constract);
        sc_info_constract.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    // TextFile 읽어 TextView에 출력
    public void setTextFromTxt(TextView tv, int rawTxtFile){
        String data = null;
        InputStream in = getResources().openRawResource(rawTxtFile);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int i;

        try{
            i = in.read();

            while(i != -1){
                out.write(i);
                i = in.read();
            }

            data = new String(out.toByteArray(), "MS949");
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        tv.setText(data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_regist:
                Toast.makeText(this, "가입완료 버튼", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_regist_use_contract:

                if(!isVisible_use){ //안보임상태일때
                    isVisible_use = true;
                    frame_use_contract.setVisibility(View.VISIBLE);
                    btn_use_contract.setText("내용접기 ▲");

                }else{  //보임 상태일때,
                    isVisible_use = false;
                    frame_use_contract.setVisibility(View.GONE);
                    btn_use_contract.setText("내용보기 ▼");
                }

                break;

            case R.id.btn_regist_info_contract:
                if(!isVisible_info){ //안보임상태일때
                    isVisible_info = true;
                    frame_info_contract.setVisibility(View.VISIBLE);
                    btn_info_contract.setText("내용접기 ▲");

                }else{  //보임 상태일때,
                    isVisible_info = false;
                    frame_info_contract.setVisibility(View.GONE);
                    btn_info_contract.setText("내용보기 ▼");
                }
                break;
        }
    }
}
