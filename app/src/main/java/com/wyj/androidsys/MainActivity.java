package com.wyj.androidsys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.wyj.androidsys.activity.NotchActivity;
import com.wyj.androidsys.aidl.MessengerService;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLl;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        findViewById(R.id.btn_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity", "onClick");
                Intent intent = new Intent(mContext, SecondActivity.class);
                intent.putExtra("time", System.currentTimeMillis());
                mContext.startActivity(intent);
            }
        });

        findViewById(R.id.btn_notch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, NotchActivity.class));
            }
        });

        startService(new Intent(mContext, MessengerService.class));


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        long time = intent.getLongExtra("time", -1);
        Log.e("MainActivity", "  time:" + time);
    }
}
