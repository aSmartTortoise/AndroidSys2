package com.wyj.androidsys.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
/*
 *  description：
 *  author:        wyj
 *  date:         2019/9/5 0005  下午 4:12
 *  corporation: 湖北空越泰学
 */
public class BookOpenHelper extends SQLiteOpenHelper {

    private static final String  DB_NAME = "book.db";
    public static final String BOOK_TABLE_NANE = "book";
    public static final String USER_TABLE_NANE = "user";
    private static final int DB_VERSION = 1;

    public BookOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlBook = "create table if not exists " + BOOK_TABLE_NANE + "(id integer primary key, name txt)";
        String sqlUser = "create table if not exists " + USER_TABLE_NANE + "(id integer primary key, name txt, gender integer)";
        db.execSQL(sqlBook);
        db.execSQL(sqlUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
