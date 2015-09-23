package com.petbox.shop.DB;

import android.provider.BaseColumns;

/**
 * Created by petbox on 2015-09-21.
 */
public class Constants implements BaseColumns {

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 91e77c4... 웹뷰 쿠키 세션 연결 완료 외 1가지
    /* ### Http ### Start ################################################################*/

    public static final int HTTP_RESPONSE_LOGIN_ERROR_NOT_MATCH = 901;
    public static final int HTTP_RESPONSE_LOGIN_ERROR_INPUT_TYPE = 902;
    public static final int HTTP_RESPONSE_LOGIN_ERROR_DENY = 903;
    public static final int HTTP_RESPONSE_LOGIN_SUCCESS = 200;


    /* ### Http ### End ################################################################*/

    /* ### Preferences ### Start ################################################################*/

    public static final String PREF_KEY_APP_FIRST = "APP_FIRST";    // 앱 처음 실행 여부
    public static final String PREF_KEY_AUTO_LOGIN = "AUTO_LOGIN";  // 자동로그인 Key

    /* ### Preferecnes ### End ################################################################*/

    /* ### DB ### Start ################################################################*/
>>>>>>> parent of 91e77c4... 웹뷰 쿠키 세션 연결 완료 외 1가지
    public static final String DATABASE = "petbox";
    public static int DATABASE_VERSION = 1;

    /* 최근 검색어 */
    public static final String RECENT_SEARCH = "recent_search"; //테이블명

    public static final String RECENT_SEARCH_ROWID = "rowId";   //식별번호
    public static final String RECENT_SEARCH_TITLE = "title";   // 검색어
    public static final String RECENT_SEARCH_DATE = "date"; //검색날짜

<<<<<<< HEAD
<<<<<<< HEAD

=======
    /* ### DB ### End ################################################################*/
>>>>>>> parent of 91e77c4... 웹뷰 쿠키 세션 연결 완료 외 1가지
=======
    /* ### DB ### End ################################################################*/
>>>>>>> parent of 91e77c4... 웹뷰 쿠키 세션 연결 완료 외 1가지

}
