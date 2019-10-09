package com.wyj.androidsys;

/**
 * Created by Administrator on 2019/9/24.
 */

public class Singleton {
    private volatile static Singleton instance;
    public static Singleton newInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
