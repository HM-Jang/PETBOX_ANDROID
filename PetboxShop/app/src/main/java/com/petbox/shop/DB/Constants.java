package com.petbox.shop.DB;

import android.provider.BaseColumns;

/**
 * Created by petbox on 2015-09-21.
 */
public class Constants implements BaseColumns {



    /* ### Static Values ### Start #######################################################*/
    public static final int AUTO_SLIDE_TIME = 5000;

    /* ### Static Values ### End #######################################################*/


    /* ### Static Values ### Start #######################################################*/
    public static final String FLURRY_APIKEY = "FJ4GZG2YXGGDT98V7NVQ";

    /* ### Static Values ### End #######################################################*/


    /* ### Http ### Start ################################################################*/

    public static final String HTTP_URL_DOMAIN = "www.petbox.kr";
    public static final String HTTP_URL_CART = "http://www.petbox.kr/m2/goods/cart.php?app=3";
    public static final String HTTP_URL_MYPAGE = "http://www.petbox.kr/m2/myp/menu_list.php?app=3";
    public final static String HTTP_URL_LOGIN = "http://www.petbox.kr/m2/mem/login_ok.php";
    public final static String HTTP_URL_REGIST = "http://www.petbox.kr/m2/mem/indb.php";

    public static final int HTTP_RESPONSE_LOGIN_ERROR_NOT_MATCH = 901;  // 아이디&비밀번호 매치 x
    public static final int HTTP_RESPONSE_LOGIN_ERROR_INPUT_TYPE = 902; // 아이디|비밀번호 형식 틀림
    public static final int HTTP_RESPONSE_LOGIN_ERROR_DENY = 903;   // 차단된 아이디
    public static final int HTTP_RESPONSE_LOGIN_SUCCESS = 200;  //로그인 성공

    public static final int HTTP_RESPONSE_REGIST_ERROR_INPUT_TYPE_ID = 801; //이메일 형식 틀림
    public static final int HTTP_RESPONSE_REGIST_ERROR_EXIST = 802; // 이미 가입된 아이디(중복)
    public static final int HTTP_RESPONSE_REGIST_ERROR_INPUT_TYPE_PW = 803;    // 비밀번호 형식 틀림
    public static final int HTTP_RESPONSE_REGIST_ERROR_INPUT_TYPE_NAME = 804; //이름 형식 틀림
    public static final int HTTP_RESPONSE_REGIST_SUCCESS = 805;


    /* ### Http ### End ################################################################*/

    /* ### Preferences ### Start ################################################################*/

    public static final String PREF_KEY_APP_FIRST = "APP_FIRST";    // 앱 처음 실행 여부
    public static final String PREF_KEY_AUTO_LOGIN = "AUTO_LOGIN";  // 자동로그인 Key
    public static final String PREF_KEY_COOKIE = "COOKIE";  // stored_member_info Cookie
    public static final String PREF_KEY_ID = "ID";
    public static final String PREF_KEY_ENCODED_BYTE = "ENCODED_BYTE";
    public static final String PREF_KEY_PASSWORD = "PASSWORD";
    public static final String PREF_KEY_AES_KEY = "AES_KEY";

    /* ### Preferecnes ### End ################################################################*/

    /* ### DB ### Start ################################################################*/
    public static final String DATABASE = "petbox";
    public static int DATABASE_VERSION = 1;

    /* 최근 검색어 */
    public static final String RECENT_SEARCH = "recent_search"; //테이블명

    public static final String RECENT_SEARCH_ROWID = "rowId";   //식별번호
    public static final String RECENT_SEARCH_TITLE = "title";   // 검색어
    public static final String RECENT_SEARCH_DATE = "date"; //검색날짜

    /* ### DB ### End ################################################################*/

    /* ### DB ### Start ################################################################*/

    public static final int REQ_SPLASH = 1; // startActivityForResult에서 Splash엑티비티
    public static final int RES_SPLASH_CANCEL = 0;  // 스플래쉬 백버튼 눌렀을 시, setResult에 세팅

    public static final int REQ_LOGIN = 2;
    public static final int RES_LOGIN_FAILED = 0;   //
    public static final int RES_LOGIN_SUCCESS = 1;  // 로그인 성공 시, setResult

    public static final int REQ_REGIST = 30;
    public static final int RES_REGIST_LOGIN_FAILED = 31;
    public static final int RES_REGIST_LOGIN_SUCCESS = 32;



    /* ### DB ### End ################################################################*/

}
