package com.wyj.androidsys.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.wyj.androidsys.Book;
import com.wyj.androidsys.IBookManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {

    public BookService() {

    }

    private CopyOnWriteArrayList<Book> mWriteArrayList = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mWriteArrayList.add(new Book(0, "基督山伯爵", "亚历山大.仲马"));
        mWriteArrayList.add(new Book(1, "三个火枪手", "亚历山大.仲马"));
    }

    @Override
    public IBinder onBind(Intent intent) {
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
            mWriteArrayList.add(book);
            return mWriteArrayList;
        }
    }
}
