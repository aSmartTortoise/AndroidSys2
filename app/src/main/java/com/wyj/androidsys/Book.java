package com.wyj.androidsys;
/*
 *  description：
 *  author:        wyj
 *  date:         2019/8/29 0029  下午 1:46
 *  corporation: 湖北空越泰学
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Book implements Parcelable {
    public int bookId;
    public String bookName;
    public String author;

    public Book(int bookId, String bookName, String author) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + bookName + " - "+ author + "]";
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
        author = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeString(author);
    }
}
