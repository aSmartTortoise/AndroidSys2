package com.wyj.okhttpanalyse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();
        String url = "http://app.ekaobang.com/" + "webschool/findStartAd.html";
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
