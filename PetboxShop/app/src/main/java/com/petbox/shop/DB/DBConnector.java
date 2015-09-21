package com.petbox.shop.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.petbox.shop.Item.RecentSearchInfo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by petbox on 2015-09-21.
 */
public class DBConnector extends SQLiteOpenHelper {

    private static final String LOG = "DBConnetor";
    Context mContext;

    public DBConnector(Context context){
        super(context, Constants.DATABASE, null, Constants.DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // 최근 검색어
        db.execSQL("CREATE TABLE " + Constants.RECENT_SEARCH + " ("
                + Constants.RECENT_SEARCH_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.RECENT_SEARCH_TITLE + " TEXT,"
                + Constants.RECENT_SEARCH_DATE + " DATE"
                + ");");

        Log.i(LOG, "++ DB Created ++");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // DB내 모든 테이블 레코드 정보 화면 출력
    public void showAllDB(String TABLE){

        SQLiteDatabase db = new DBConnector(mContext).getReadableDatabase();
        String query = "SELECT * FROM " + TABLE  + ";";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        if(c.getCount() >0){

            Log.i(LOG, " ++ Show All DB ++");

            int count =0;
            do{
                String log = "";

                for(int i=0; i<c.getColumnCount(); i++){
                    log += c.getColumnName(i);
                    log += " : ";

                    int type = c.getType(i);

                    switch(type){
                        case Cursor.FIELD_TYPE_INTEGER :
                            log += "(INT)" + c.getInt(i) + " ";
                            break;
                        case Cursor.FIELD_TYPE_STRING :
                            log += "(STR)" + c.getString(i) + " ";
                            break;
                        case Cursor.FIELD_TYPE_NULL :
                            log += "(NULL)" + " ";
                            break;
                        default :
                            log += "(ETC)" + " ";
                    }
                }
                count++;
                Log.i(LOG, "[" + count + "] //" + log);
            }while(c.moveToNext());
            Log.i(LOG, " ++ Show All DB : End ++");
        }else{
            Log.i(LOG, " ++ Show All DB : Empty ++");
        }
        db.close();
    }

    /* ### 최근검색어 START ####################################################### */

    //삽입
    public boolean  insertToRecentSearch(String title, String date){
        SQLiteDatabase db = new DBConnector(mContext).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.RECENT_SEARCH_TITLE, title);
        values.put(Constants.RECENT_SEARCH_DATE, date);

        db.insert(Constants.RECENT_SEARCH, null, values);
        Log.i(LOG, "Insert " + title);

        db.close();

        return true;
    }

    //삭제
    public boolean deleteRecentSearchInfo(int rowId){
        SQLiteDatabase db = new DBConnector(mContext).getWritableDatabase();

        String whereClause = Constants.RECENT_SEARCH_ROWID + "=?";  // Where절
        String[] whereArgs = new String[]{Integer.toBinaryString(rowId)};

        db.delete(Constants.RECENT_SEARCH, whereClause, whereArgs);

        db.close();
        return true;
    }



    //반환
    public ArrayList<RecentSearchInfo> returnFromRecentSearch(){
        SQLiteDatabase db = new DBConnector(mContext).getReadableDatabase();

        ArrayList<RecentSearchInfo> itemList = new ArrayList<RecentSearchInfo>();

        String query = "SELECT * FROM " + Constants.RECENT_SEARCH + " ORDER BY " + Constants.RECENT_SEARCH_ROWID + " desc";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        do{
            int id = c.getInt(c.getColumnIndex(Constants.RECENT_SEARCH_ROWID));
            String title = c.getString(c.getColumnIndex(Constants.RECENT_SEARCH_TITLE));
            String date = c.getString(c.getColumnIndex(Constants.RECENT_SEARCH_DATE));

            RecentSearchInfo item = new RecentSearchInfo(id, title, date);
            itemList.add(item);

        }while(c.moveToNext());

        db.close();

        return itemList;
    }

    /* ### 최근검색어 END ####################################################### */


}
