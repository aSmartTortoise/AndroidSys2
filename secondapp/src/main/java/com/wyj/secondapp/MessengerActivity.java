package com.wyj.secondapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MessengerActivity extends AppCompatActivity {
    private ServiceConnection mConnection;
    private Messenger mMessengerServer;

    private Messenger mMessengerClient = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                Bundle bundle = msg.getData();
                if (bundle == null) return;
                String msgContent = bundle.getString("msgContent");
                Log.e("MessengerActivity", "msgContent:" + msgContent);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.wyj.androidsys", "com.wyj.androidsys.aidl.MessengerService"));
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                boolean binderAlive = service.isBinderAlive();
                Log.e(MessengerActivity.this.toString(), "binderAlive:" + binderAlive);
                mMessengerServer = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMessengerServer = null;
            }
        };
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        findViewById(R.id.tv_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("msgContent", "hello i am clinet");
                message.setData(bundle);
                message.replyTo = mMessengerClient;
                try {
                    if (mMessengerServer != null)
                    mMessengerServer.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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
