package com.example.dustin0128.sqltest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dustin0128 on 2018/11/5.
 */
class mydb extends MainActivity {

    public SQLiteDatabase db = null;
    private final static String DATABASE_NAME = "db1.db";
    private final static String TABLE_NAME = "table01";
    private final static String _ID = "_id";
    private final static String NAME = "name";
    private final static String PRICE = "price";

    private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + _ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + PRICE
            + " TEXT)";

    private Context mCtx = null;

    public mydb(Context ctx) {
        this.mCtx = ctx;
    }

    //Create Or Open Table
    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);

        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {

        }
    }

    public void close() throws SQLException {
        db.close();
    }

    //查詢全部
    public Cursor getALL() {
        Cursor mCursor = db
                .query(TABLE_NAME, new String[] { _ID, NAME, PRICE }, null,
                        null, null, null, null);

        while (mCursor.moveToNext()){
            Log.d("mylog","getall："+ mCursor.getString(0));
            Log.d("mylog","getall："+ mCursor.getString(1));
            Log.d("mylog","getall："+ mCursor.getString(2));

        }

        return mCursor;
    }

    //單筆查詢
    public Cursor getsearchid(long rowid) throws SQLException {
        Cursor mCursor = db.query(TABLE_NAME,
                new String[] { _ID, NAME, PRICE }, _ID + "=" + rowid, null,
                null, null, null, null);

        if (mCursor != null && mCursor.getCount() > 0) {

            //查詢成功Cursor移動到下一筆
            mCursor.moveToNext();
        }

        return mCursor;
    }
    //新增資料
    //回傳long Type用來判斷新增是否成功和增加的筆數
    public long append(String name, String price) {
        ContentValues args = new ContentValues();
        args.put(NAME, name);
        args.put(PRICE, price);

        Log.d("mylog","add："+ name);
        Log.d("mylog","add："+ price);
        return db.insert(TABLE_NAME, null, args);

    }
    //刪除資料
    public boolean delete(long rowid) {
        Log.d("mylog","del："+ rowid);
        return db.delete(TABLE_NAME, _ID + "=" + rowid, null) > 0;

    }
    //更新資料
    //boolean回傳1代表更新成功
    public boolean updata(long rowid, String name, String price) {
        ContentValues args = new ContentValues();
        args.put(NAME, name);
        args.put(PRICE, price);

        Log.d("mylog","updata："+ rowid);
        Log.d("mylog","updata："+ name);
        Log.d("mylog","updata："+ price);
        return db.update(TABLE_NAME, args, _ID + "=" + rowid, null) > 0;

    }
}
