package com.wyj.secondapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClientActivity extends AppCompatActivity {

    public static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    public static final int MESSAGE_SOCKET_CONNECTED = 2;

    private TextView mTvMsg;
    private EditText mEtMsg;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;

    @SuppressLint("HandlerLeadk")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    mTvMsg.setText(mTvMsg.getText() + (String)msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mBtnSend.setEnabled(true);
                    break;
            }
        }
    };
    private Button mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        initView(savedInstanceState);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.wyj.androidsys", "com.wyj.androidsys.service.TCPServerService"));
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }).start();
    }

    private void initView(Bundle savedInstanceState) {
        mTvMsg = findViewById(R.id.tv_msg);
        mEtMsg = findViewById(R.id.et_msg);
        mBtnSend = findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = mEtMsg.getText().toString().trim();
                if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mPrintWriter.println(msg);
                        }
                    }).start();

                    mEtMsg.setText("");
                    String time = formatDateTime(System.currentTimeMillis());
                    String showedMsg = "self:" + time + ":" + msg + "\n";
                    mTvMsg.setText(mTvMsg.getText() + showedMsg);
                }
            }
        });
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()))
                        , true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!TCPClientActivity.this.isFinishing()) {
                    String msg = in.readLine();
                    System.out.println("receive:" + msg);
                    if (null != msg) {
                        String time = formatDateTime(System.currentTimeMillis());
                        String showedMsg = "server " + time + ":" + msg + "\n";
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                    }
                }
                System.out.println("quit...");
                mPrintWriter.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
