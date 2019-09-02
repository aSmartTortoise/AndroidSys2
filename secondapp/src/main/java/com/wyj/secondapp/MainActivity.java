package com.wyj.secondapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wyj.androidsys.IBookManager;
import com.wyj.androidsys.Book;

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
            // TODO: 2019/8/30 0030

        }
    };
    private Context mContext;

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
                    String bookName = mIBookManager.getBookName();
                    Log.e("MainActivity", "bookName" + bookName);
                    List<Book> books = mIBookManager.addBook(new Book(0, "基督山伯爵"));
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null)
        unbindService(mConnection);
    }
}
