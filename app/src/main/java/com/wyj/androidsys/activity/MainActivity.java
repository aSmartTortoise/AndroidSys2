package com.wyj.androidsys.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import com.wyj.androidsys.R;
import com.wyj.androidsys.aidl.MessengerService;
import com.wyj.androidsys.utils.ScreenUtils;

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
        /**
         * 获取设备最小宽度
         */
        float smallestWidth = getSmallestWidth(mContext);
        Log.e("MainActivity", smallestWidth + "");

        SparseArray<String> array = new SparseArray<>();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        long time = intent.getLongExtra("time", -1);
        Log.e("MainActivity", "  time:" + time);
    }

    private float getSmallestWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = ScreenUtils.getScreenWidth(context);
        int screenHeight = ScreenUtils.getScreenHeight(context);
        float density = dm.density;
        float widtchDp = screenWidth / density;
        float heightDp = screenHeight / density;
        if (widtchDp < heightDp) {
            return widtchDp;
        }else {
            return heightDp;
        }
    }
}
