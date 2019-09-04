package com.wyj.androidsys.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.wyj.androidsys.Book;
import com.wyj.androidsys.IBookManager;
import com.wyj.androidsys.IOnNewBookArrivedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookService extends Service {

    public BookService() {

    }

    private CopyOnWriteArrayList<Book> mWriteArrayList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mArrivedListeners = new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    @Override
    public void onCreate() {
        super.onCreate();
        mWriteArrayList.add(new Book(0, "基督山伯爵", "亚历山大.仲马"));
        mWriteArrayList.add(new Book(1, "三个火枪手", "亚历山大.仲马"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BookBinder();
    }

    class BookBinder extends IBookManager.Stub {

        @Override
        public List<Book> getBookList() {
            return mWriteArrayList;
        }

        @Override
        public void addBook(Book book) {
            mWriteArrayList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) {
            mArrivedListeners.register(listener);
            int count = mArrivedListeners.getRegisteredCallbackCount();
            Log.e("BookService", "registerListener count:" + count);
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) {
            mArrivedListeners.unregister(listener);
            int count = mArrivedListeners.getRegisteredCallbackCount();
            Log.e("BookService", "unRegisterListener count:" + count);
        }
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mWriteArrayList.size() + 1;
                Book newBook = new Book(bookId, "new Book#" + bookId, "author#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        mWriteArrayList.add(newBook);
        int n = mArrivedListeners.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IOnNewBookArrivedListener broadcastItem = mArrivedListeners.getBroadcastItem(i);
            if (null != broadcastItem) {
                broadcastItem.onNewBookArrived(newBook);
            }
        }
        mArrivedListeners.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }
}
