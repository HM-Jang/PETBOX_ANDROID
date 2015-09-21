package com.petbox.shop.DB;

import android.provider.BaseColumns;

/**
 * Created by petbox on 2015-09-21.
 */
public class Constants implements BaseColumns {

    public static final String DATABASE = "petbox";
    public static int DATABASE_VERSION = 1;

    /* 최근 검색어 */
    public static final String RECENT_SEARCH = "recent_search"; //테이블명

    public static final String RECENT_SEARCH_ROWID = "rowId";   //식별번호
    public static final String RECENT_SEARCH_TITLE = "title";   // 검색어
    public static final String RECENT_SEARCH_DATE = "date"; //검색날짜



}
