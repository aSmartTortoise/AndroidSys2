package com.wyj.androidsys.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wyj.androidsys.db.BookOpenHelper;

import java.time.temporal.ValueRange;

/*
 *  description：
 *  author:        wyj
 *  date:         2019/9/4 0004  上午 11:24
 *  corporation: 湖北空越泰学
 */
public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.wyj.androidsys.book.provider";
    public static final Uri BOOK_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher uriMather = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMather.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        uriMather.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private SQLiteDatabase mDb;
    private Context mContext;

    @Override
    public boolean onCreate() {
        Log.e("BookProvider", "onCreate:" + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData() {
        BookOpenHelper bookOpenHelper = new BookOpenHelper(getContext());
        mDb = bookOpenHelper.getReadableDatabase();
        mDb.execSQL("delete from " + BookOpenHelper.BOOK_TABLE_NANE);
        mDb.execSQL("delete from " + BookOpenHelper.USER_TABLE_NANE);
        mDb.execSQL("insert into book values(3,'Android')");
        mDb.execSQL("insert into book values(4,'ios')");
        mDb.execSQL("insert into book values(5,'Html5')");
        mDb.execSQL("insert into user values(1,'Jack', 1)");
        mDb.execSQL("insert into user values(2,'Lucy', 0)");
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        int uriCode = uriMather.match(uri);
        switch (uriCode) {
            case BOOK_URI_CODE:
                tableName = BookOpenHelper.BOOK_TABLE_NANE;
                break;
            case USER_URI_CODE:
                tableName = BookOpenHelper.USER_TABLE_NANE;
                break;
        }
        return tableName;
    }

    @NonNull
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e("BookProvider", "query:" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport URI:" + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, null,null, sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport URI:" + uri);
        }
        mDb.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport URI:" + uri);
        }
        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0) mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport URI:" + uri);
        }
        int row = mDb.update(tableName, values, selection, selectionArgs);
        if (row > 0) mContext.getContentResolver().notifyChange(uri, null);
        return row;
    }
}
