package com.wyj.secondapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wyj.androidsys.IBookManager;
import com.wyj.androidsys.Book;
import com.wyj.androidsys.IOnNewBookArrivedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IBookManager mIBookManager;
    private ServiceConnection mConnection;
    private Binder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(MainActivity.this.toString(), "binderDied");
            if (mIBookManager == null) return;
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBookManager = null;
        }
    };
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("MainActivity", msg.obj.toString());
        }
    };

    private IOnNewBookArrivedListener mListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            String threadName = Thread.currentThread().getName();
            Log.e("MainActivity", "threadName:" + threadName);
            Message message = mHandler.obtainMessage();
            message.obj = book;
            mHandler.sendMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.wyj.androidsys", "com.wyj.androidsys.aidl.BookService"));
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                boolean binderAlive = service.isBinderAlive();
                Log.e(MainActivity.this.toString(), "binderAlive:" + binderAlive);
                mIBookManager = IBookManager.Stub.asInterface(service);
                try {
                    service.linkToDeath(mDeathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    mIBookManager.registerListener(mListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        findViewById(R.id.tv_ipc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mIBookManager.addBook(new Book(2, "魔戒", "托尔金"));
                    List<Book> bookList = mIBookManager.getBookList();
                    Log.e("MainActivity", bookList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.tv_open_messenger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MessengerActivity.class));
            }
        });

        findViewById(R.id.tv_provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BookProviderActivity.class));
            }
        });

        findViewById(R.id.tv_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, TCPClientActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mIBookManager != null && mIBookManager.asBinder().isBinderAlive()) {
            try {
                mIBookManager.unRegisterListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (mConnection != null) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }
}
