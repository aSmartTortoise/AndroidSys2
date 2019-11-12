package com.wyj.androidsys.activity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wyj.androidsys.R;

public class HandlerTestActivity extends AppCompatActivity {

    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    private Handler workHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        /**
         * 1. sendMessage
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                mHandler.sendMessage(msg);
            }
        }).start();
        /**
         * 2. post message
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        workHandler.sendMessage(Message.obtain());

    }
}
