package com.wyj.androidsys.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private Messenger mMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                Bundle bundle = msg.getData();
                if (bundle == null) return;
                String msgContent = bundle.getString("msgContent");
                Log.e("MessengerService", "msgContent:" + msgContent);
                Message replyMsg = Message.obtain();
                Bundle replyBundle = new Bundle();
                replyBundle.putString("msgContent", "hello client, i am server");
                replyMsg.setData(replyBundle);
                try {
                    msg.replyTo.send(replyMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }
    });

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


}
