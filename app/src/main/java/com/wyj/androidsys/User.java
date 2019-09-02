package com.wyj.androidsys;
/*
 *  description：
 *  author:        wyj
 *  date:         2019/8/29 0029  下午 1:28
 *  corporation: 湖北空越泰学
 */

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userId;
    private String userName;
    private int age;

    protected User(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        age = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeInt(age);
    }
}
