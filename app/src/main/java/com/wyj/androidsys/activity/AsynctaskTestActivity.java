package com.wyj.androidsys.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wyj.androidsys.R;

import java.io.File;

public class AsynctaskTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask_test);

        MyTask myTask = new MyTask();
        myTask.execute("101");
    }


    public static class MyTask extends AsyncTask<String, Integer, File> {

        private MyTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected File doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
