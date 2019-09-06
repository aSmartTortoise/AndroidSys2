package com.wyj.androidsys.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {
    private boolean mIsServiceDestroyed = false;
    private String[] mDefaultMsgs = new String[]{
            "您好啊，哈哈",
            "请问，您叫什么名字？",
            "今天武汉天气不错啊,shy",
            "你知道么，我是可以和多个人同时聊天的额，",
            "给你讲个笑话吧，据说爱笑的人，运气都不会太差，不知道真假，"
    };

    public TCPServerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TCPServer()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class TCPServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("establish tcp server failed, port 8688");
            }
            while (!mIsServiceDestroyed) {
                try {
                    final Socket clientSocket = serverSocket.accept();
                    System.out.println("accetp");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(clientSocket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket clientSocket) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream()))
                , true);
        out.println("欢迎来到聊天室来");
        while (!mIsServiceDestroyed) {
            String msg = in.readLine();
            if (msg == null) {
                break;
            }
            int index = new Random().nextInt(mDefaultMsgs.length);
            msg = mDefaultMsgs[index];
            out.println(msg);
            System.out.println("send:" + msg);
        }
        System.out.println("client quit...");
        in.close();
        out.close();
        clientSocket.close();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }
}
