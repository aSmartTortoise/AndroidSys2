package com.wyj.androidsys;
/*
 *  description：
 *  author:        wyj
 *  date:         2019/8/27 0027  下午 6:32
 *  corporation: 湖北空越泰学
 */

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyApplication", "onCreate");
    }
}
