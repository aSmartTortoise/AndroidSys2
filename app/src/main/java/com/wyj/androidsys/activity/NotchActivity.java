package com.wyj.androidsys.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wyj.androidsys.R;
import com.wyj.androidsys.utils.StatusBarUtils;

public class NotchActivity extends AppCompatActivity {

    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notch);
        StatusBarUtils.transparencyBar(this);
//         //设置页面全屏显示
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
//       //设置页面延伸到刘海区显示
//        window.setAttributes(lp);
        mIv = findViewById(R.id.iv);
        if (Build.VERSION.SDK_INT >= 28) {
            mIv.post(new Runnable() {
                @Override
                public void run() {
                    DisplayCutout displayCutout = mIv.getRootWindowInsets().getDisplayCutout();
                    if (displayCutout == null) return;
                    int safeInsetTop = displayCutout.getSafeInsetTop();
                    int safeInsetLeft = displayCutout.getSafeInsetLeft();
                    int safeInsetRight = displayCutout.getSafeInsetRight();
                    int safeInsetBottom = displayCutout.getSafeInsetBottom();
                    Log.e("NotchActivity", "left:" + safeInsetLeft + " top:" + safeInsetTop + " right:" + safeInsetRight + " bottom:" + safeInsetBottom);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIv.getLayoutParams();
                    params.topMargin = safeInsetTop;
                    mIv.setLayoutParams(params);
                }
            });
        }
    }
}
