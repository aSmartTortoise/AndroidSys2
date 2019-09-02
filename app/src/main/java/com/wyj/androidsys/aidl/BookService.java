package com.wyj.androidsys.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.wyj.androidsys.Book;
import com.wyj.androidsys.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {

    private List<Book> bookList;
    public BookService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        bookList = new ArrayList<>();
       return new BookBinder();
    }

    class BookBinder extends IBookManager.Stub {

        @Override
        public String getBookName() throws RemoteException {
            return "基督山伯爵";
        }

        @Override
        public String getBookAuthor() {
            return "亚历山大.仲马";
        }

        @Override
        public List<Book> addBook(Book book) {
            bookList.add(book);
            return bookList;
        }
    }
}
