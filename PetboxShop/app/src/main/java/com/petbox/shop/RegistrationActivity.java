package com.petbox.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.petbox.shop.CustomView.RegistFailedDialog;
import com.petbox.shop.DB.Constants;
import com.petbox.shop.Delegate.HttpPostDelegate;
import com.petbox.shop.Delegate.LoginManagerDelegate;
import com.petbox.shop.Http.HttpPostManager;
import com.petbox.shop.Network.LoginManager;
import com.petbox.shop.Utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,HttpPostDelegate,LoginManagerDelegate {

    EditText edit_email, edit_name, edit_pw, edit_pw_re;

    EditText edit_phone1, edit_phone2, edit_phone3, edit_allergy_etc, edit_recommend_id;
    RadioGroup radio_animals;
    //RadioButton radio_dog, radio_cat, radio_etc;
    CheckBox chk_agree_email, chk_agree_sms;
    Spinner spin_varieties, spin_age, spin_weight, spin_allergy;
    Button btn_regist;

    TextView btn_use_contract, btn_info_contract, tv_use_contract, tv_info_contract;
    FrameLayout frame_use_contract, frame_info_contract;

    boolean isVisible_use = false; // false : GONE, true : VISIBLE
    boolean isVisible_info = false;

    boolean isRadioCheck = false; // false : 아무것도 체크 안됨, true: 체크됨.
    boolean finishable = false;     // false : 종료 대기, true : 종료

    ScrollView sc_main, sc_use_constract, sc_info_constract;

    ArrayAdapter<String> varietiesAdapter, ageAdapter, weightAdapter, allergyAdapter;

    String[] str_varieties, str_age, str_weight, str_allergy;

    /*Http Post*/
    String email_id, name, pw, phone, animal_type,varieties, age, weight, allergy, allergy_etc, recommend_id, mailling, sms = "";

    ImageView iv_regist_ok;

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


        radio_animals = (RadioGroup) findViewById(R.id.radio_animals);
        radio_animals.setOnCheckedChangeListener(this);
        /*
        radio_dog = (RadioButton) findViewById(R.id.radio_regist_dog);
        radio_cat = (RadioButton) findViewById(R.id.radio_regist_cat);
        radio_etc = (RadioButton) findViewById(R.id.radio_regist_etc);
        */

        str_varieties = getResources().getStringArray(R.array.regist_dog);
        str_age = getResources().getStringArray(R.array.regist_age);
        str_weight = getResources().getStringArray(R.array.regist_weight);
        str_allergy = getResources().getStringArray(R.array.regist_allergy);

        spin_varieties = (Spinner) findViewById(R.id.spin_regist_varieties);    //품종
        String[] str_varieties = {"품종"};
        varieties = "품종";

        varietiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_varieties);
        spin_varieties.setAdapter(varietiesAdapter);
        spin_varieties.setEnabled(false);

        spin_age = (Spinner) findViewById(R.id.spin_regist_age);    //나이
        //spin_age.setOnClickListener(this);
        ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_age);
        spin_age.setAdapter(ageAdapter);
        spin_age.setEnabled(false);
        age = "나이";

        spin_weight = (Spinner) findViewById(R.id.spin_regist_weight);  //무게

        //spin_weight.setOnClickListener(this);
        //String[] str_weight = {"무게"};
        weightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_weight);
        spin_weight.setAdapter(weightAdapter);
        spin_weight.setEnabled(false);
        weight = "무게";

        spin_allergy = (Spinner) findViewById(R.id.spin_regist_allergy);    //알러지
        //spin_allergy.setOnClickListener(this);
        //String[] str_allergy = {"알러지"};
        allergyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_allergy);
        spin_allergy.setAdapter(allergyAdapter);
        spin_allergy.setEnabled(false);
        allergy = "알러지";


        edit_allergy_etc = (EditText) findViewById(R.id.edit_regist_allergy_etc);
        edit_allergy_etc.setEnabled(false);

        edit_recommend_id = (EditText) findViewById(R.id.edit_regist_recommend_id);
        edit_recommend_id.setEnabled(false);

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

        iv_regist_ok = (ImageView) findViewById(R.id.iv_regist_ok);

        //iv_regist_ok.setImageBitmap(getBitmapFromURL("http://petbox.godohosting.com/files/petbox_images/reg_ok.jpg"));

        ImageDownloader.download("http://petbox.godohosting.com/files/petbox_images/reg_ok.jpg", iv_regist_ok);

        /*
        URL imageURL = null;
        URLConnection conn = null;
        InputStream is= null;

        try {
            imageURL = new URL("http://petbox.godohosting.com/files/petbox_images/reg_ok.jpg");
            conn = imageURL.openConnection();
            conn.connect();
            is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bitMap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            iv_regist_ok.setImageBitmap(bitMap);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        */
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

            data = new String(out.toByteArray(), "UTF-8");
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        tv.setText(data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.btn_regist:

                email_id = edit_email.getText().toString();

                if(email_id.equals("")){    //이메일 공백
                    Toast.makeText(this, "이메일란이 공백입니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(!Utility.isEmailValid(email_id)){ //이메일 형식 x
                    Toast.makeText(this, "이메일 형식 오류",Toast.LENGTH_SHORT).show();
                    break;
                }

                pw = edit_pw.getText().toString();
                String pw_re = edit_pw_re.getText().toString();

                if(!pw.equals(pw_re)){ // 비밀번호 != 비밀번호 확인
                    Toast.makeText(this, "입력하신 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                    break;
                }

                if(pw.length() < 6 || pw.length() > 16){   // 비밀번호 6자리 미만, 16자리 초과

                    Toast.makeText(this, "비밀번호는 6자리 이상 16자리 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    //영문자 하나 이상 들어가는지?
                    if(!Utility.isPasswordValid(pw)){
                        Toast.makeText(this, "영문 1개가 포함되어야합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    //Toast.makeText(this, "유효한 비밀번호", Toast.LENGTH_SHORT).show();
                }

                name = edit_name.getText().toString();

                /*
                int name_bytes = name.getBytes().length;

                if(name_bytes < 4 || name_bytes > 20) {  // 한글 2자이상

                    if(!Utility.isNameValid(name)){

                    }

                    Toast.makeText(this, "이름은 한글2자 이상, 영문이름 4자 이상입니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
                */

                if(!Utility.isNameValid(name)){
                    Toast.makeText(this, "이름은 한글2자 이상, 영문이름 4자 이상입니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

                /* 한글2자에 공백 들어있나, 영문이름 5자에 공백 들어가있나 검사.
                if(){

                }
                */

                phone = edit_phone1.getText().toString() + "-" + edit_phone2.getText().toString() + "-" + edit_phone3.getText().toString();

                /* 부가정보이므로, HTTP Post로 값 넘길때 검사
                if(!Utility.isPhoneValid(phone)){
                    Toast.makeText(this, "유효하지 않은 핸드폰번호입니다.", Toast.LENGTH_SHORT).show();
                }
                */
                mailling = "n";
                if(chk_agree_email.isChecked())
                    mailling = "y";

                sms = "n";
                if(chk_agree_sms.isChecked())
                    sms = "y";

                varieties = (String)spin_varieties.getSelectedItem();
                age = (String)spin_age.getSelectedItem();
                weight = (String)spin_weight.getSelectedItem();
                allergy = (String)spin_allergy.getSelectedItem();
                allergy_etc = edit_allergy_etc.getText().toString();
                recommend_id = edit_recommend_id.getText().toString();

                HttpPostManager httpPostManager = new HttpPostManager(this);
                httpPostManager.start();

                //Toast.makeText(this, "가입완료 버튼", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_regist_use_contract:

                if (!isVisible_use) { //안보임상태일때
                    isVisible_use = true;
                    frame_use_contract.setVisibility(View.VISIBLE);
                    btn_use_contract.setText("내용접기 ▲");
                    //sc_main.fullScroll(View.FOCUS_DOWN);

                } else {  //보임 상태일때,
                    isVisible_use = false;
                    frame_use_contract.setVisibility(View.GONE);
                    btn_use_contract.setText("내용보기 ▼");
                }

                break;

            case R.id.btn_regist_info_contract:
                if (!isVisible_info) { //안보임상태일때
                    isVisible_info = true;
                    frame_info_contract.setVisibility(View.VISIBLE);
                    btn_info_contract.setText("내용접기 ▲");
                    //sc_main.fullScroll(View.FOCUS_DOWN);

                } else {  //보임 상태일때,
                    isVisible_info = false;
                    frame_info_contract.setVisibility(View.GONE);
                    btn_info_contract.setText("내용보기 ▼");
                }
                break;
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(connection!=null)connection.disconnect();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        isRadioCheck = true;

        spin_allergy.setEnabled(true);
        edit_allergy_etc.setEnabled(true);
        edit_recommend_id.setEnabled(true);

        if(radio_animals.getCheckedRadioButtonId() == R.id.radio_regist_dog){   //강아지 선택
            str_varieties = getResources().getStringArray(R.array.regist_dog);
            varietiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_varieties);
            spin_varieties.setAdapter(varietiesAdapter);
            spin_varieties.setEnabled(true);
            spin_age.setEnabled(true);
            spin_weight.setEnabled(true);

            animal_type = "강아지";

        }else if(radio_animals.getCheckedRadioButtonId() == R.id.radio_regist_cat){ //고양이 선택
            str_varieties = getResources().getStringArray(R.array.regist_cat);
            varietiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_varieties);
            spin_varieties.setAdapter(varietiesAdapter);
            spin_varieties.setEnabled(true);
            spin_age.setEnabled(true);
            spin_weight.setEnabled(true);
            animal_type = "고양이";

        }else if(radio_animals.getCheckedRadioButtonId() == R.id.radio_regist_etc){ //기타 선택
            String[] str_varieties = {"품종"};
            varietiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_varieties);
            spin_varieties.setAdapter(varietiesAdapter);
            spin_varieties.setEnabled(false);
            animal_type = "기타";
        }
    }


    /* Http Post Delegate */
    @Override
    public void prevRunningHttpPost() {

    }

    @Override
    public String getPostUrl() {
        return Constants.HTTP_URL_REGIST;
    }

    @Override
    public List<NameValuePair> getNameValuePairs() {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mode", "member_join"));
        nameValuePairs.add(new BasicNameValuePair("id_chk", "1"));
        nameValuePairs.add(new BasicNameValuePair("pwd_chk", "1"));
        nameValuePairs.add(new BasicNameValuePair("passwordSkin", "y"));
        nameValuePairs.add(new BasicNameValuePair("ex_en", "y"));   // 웹서버에서 한글 인코딩 처리 유/무
        nameValuePairs.add(new BasicNameValuePair("android", "y"));

        nameValuePairs.add(new BasicNameValuePair("m_id", email_id));
        nameValuePairs.add(new BasicNameValuePair("email",email_id));
        nameValuePairs.add(new BasicNameValuePair("password", pw));
        nameValuePairs.add(new BasicNameValuePair("name", name));

        if(Utility.isPhoneValid(phone)){
            nameValuePairs.add(new BasicNameValuePair("mobile", phone));
        }

        nameValuePairs.add(new BasicNameValuePair("mailling", mailling));
        nameValuePairs.add(new BasicNameValuePair("sms", sms));

        if(isRadioCheck){   // 동물 종류가 선택되었을 시,
            nameValuePairs.add(new BasicNameValuePair("ex1", animal_type));

            if(!varieties.equals("품종")){    // '품종'이 아닐때
                nameValuePairs.add(new BasicNameValuePair("ex4", varieties));
            }

            if(!age.equals("나이")){  // '나이'가 아닐때
                nameValuePairs.add(new BasicNameValuePair("ex2", age));
            }

            if(!weight.equals("무게")){   // '무게'가 아닐때
                nameValuePairs.add(new BasicNameValuePair("ex3", weight));
            }

            if(!allergy.equals("알러지")){ // '알러지'가 아닐때
                nameValuePairs.add(new BasicNameValuePair("ex5", allergy));
            }

            if(!allergy_etc.equals("")){
                nameValuePairs.add(new BasicNameValuePair("ex6", allergy_etc));
            }
        }

        if(!recommend_id.equals(""))
            nameValuePairs.add(new BasicNameValuePair("recommid", recommend_id));

        //System.out.println("malling : " + mailling + "// sms : " + sms);

        return nameValuePairs;
    }

    @Override
    public void runningHttpPost() {

    }

    @Override
    public void afterRunningHttpPost(int responseCode) {
        //Toast.makeText(this, responseCode+"", Toast.LENGTH_SHORT).show();

        if(responseCode == Constants.HTTP_RESPONSE_REGIST_ERROR_INPUT_TYPE_ID){
            //Toast.makeText(this, "서버(801) : 이메일 형식 틀림", Toast.LENGTH_SHORT).show();
            RegistFailedDialog dialog = new RegistFailedDialog(this, "회원가입 실패", "서버(801) \n 이메일 형식 틀림");
            dialog.show();
        }else if(responseCode == Constants.HTTP_RESPONSE_REGIST_ERROR_EXIST){
            //Toast.makeText(this, "서버(802) : 이미 가입된 아이디(중복)", Toast.LENGTH_SHORT).show();
            RegistFailedDialog dialog = new RegistFailedDialog(this, "회원가입 실패", "서버(802) \n 이미 가입된 아이디(중복)");
            dialog.show();
        }else if(responseCode == Constants.HTTP_RESPONSE_REGIST_ERROR_INPUT_TYPE_PW){
            //Toast.makeText(this, "서버(803) : 비밀번호 형식 틀림", Toast.LENGTH_SHORT).show();
            RegistFailedDialog dialog = new RegistFailedDialog(this, "회원가입 실패", "서버(803) \n 비밀번호 형식 틀림");
            dialog.show();
        }else if(responseCode == Constants.HTTP_RESPONSE_REGIST_ERROR_INPUT_TYPE_ID){
            //Toast.makeText(this, "서버(804) : 비밀번호 형식 틀림", Toast.LENGTH_SHORT).show();
            RegistFailedDialog dialog = new RegistFailedDialog(this, "회원가입 실패", "서버(804) \n 비밀번호 형식 틀림");
            dialog.show();
        }else if(responseCode == Constants.HTTP_RESPONSE_REGIST_SUCCESS){
            //Toast.makeText(this, "서버(805) : 가입 성공 ", Toast.LENGTH_SHORT).show();
            //RegistFailedDialog dialog = new RegistFailedDialog(this, "회원가입 성공", "서버(805) \n 가입 성공");
            //dialog.show();
            iv_regist_ok.setVisibility(View.VISIBLE);
            LoginManager.setDelegate(this);
            LoginManager.connectAndLogin(email_id, pw, true);


        }

    }

    /*Login Manager Delegate*/
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
        }else if (responseCode == Constants.HTTP_RESPONSE_LOGIN_SUCCESS){
            LoginManager.setIsLogin(true);

            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            CookieSyncManager.getInstance().startSync();

            String cookieString = LoginManager.getCookie().getName() + "=" + LoginManager.getCookie().getValue();
            STPreferences.putString(Constants.PREF_KEY_COOKIE, cookieString);

            cookieManager.setCookie(Constants.HTTP_URL_DOMAIN, cookieString);

            STPreferences.putString(Constants.PREF_KEY_ID, email_id);
            STPreferences.putString(Constants.PREF_KEY_PASSWORD, pw);

            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();

            try {
                Thread.sleep(500);  // CookieManager Sync Time 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setResult(Constants.RES_REGIST_LOGIN_SUCCESS);
            this.finish();
        }
    }
}
