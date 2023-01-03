package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Timestamp;

public class DbConnection extends SQLiteOpenHelper {

    public static final String db_name="android.db";
    public static final String table_name="entries";
    public static final String col_id="ID";
    public static final String col_userID="USERID";
    public static final String col_lng="LNG";
    public static final String col_lat="LAT";
    public static final String col_dt="DT";


    public static final String create_table=
            "CREATE TABLE IF NOT EXISTS " + table_name + " ( " +
                    col_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                    col_userID + " VARCHAR(10)," +
                    col_lng + " FLOAT," +
                    col_lat + " FLOAT," +
                    col_dt   + " VARCHAR(20)" + " ) " ;


    public DbConnection(@Nullable Context context) {
        super(context, db_name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String user, float lng, float lat, String dt){
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_userID, user);
        values.put(col_lng, lng);
        values.put(col_lat,lat);
        values.put(col_dt, dt);
        long result = db.insert(table_name,null,values);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }


    public Cursor getColums(String col){
        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + col + " from " + table_name ,null);
        return res;
    }

    public Cursor searchData(String col, String input){
        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table_name + " where " + col + "='" + input + "'"  ,null);
        return res;
    }

    public Cursor searchData(String col1,String input1, String col2 , String input2){
        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table_name + " where " + col1 + "='" + input1 + "' AND " + col2 + "='" + input2 + "'"  ,null);
        return res;
    }

    public void drop_table(){
        SQLiteDatabase  db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " +  table_name);
    }
}
