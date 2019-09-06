package com.wyj.secondapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wyj.androidsys.Book;

public class BookProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_provider);
        final Uri bookUri = Uri.parse("content://com.wyj.androidsys.book.provider/book");
        Uri userUri = Uri.parse("content://com.wyj.androidsys.book.provider/user");

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", 6);
                values.put("name", "程序设计的艺术");
                getContentResolver().insert(bookUri, values);
                Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"id", "name"}, null, null, null);
                while (bookCursor.moveToNext()) {
                    Book book = new Book();
                    book.bookId = bookCursor.getInt(0);
                    book.bookName = bookCursor.getString(1);
                    Log.e("BookProvider", book.toString());
                }
                bookCursor.close();

            }
        });
    }
}
