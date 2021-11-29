package com.inwi.digitalworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress_bar_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_bar_splash = findViewById(R.id.progress_bar_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };


        Timer time = new Timer();
        time.schedule(task, 3000);


    }

    public class ShowCustomProgressBarAsyncTask extends AsyncTask<Void, Integer, Void> {
        int myProgress;

        @Override
        protected void onPostExecute(Void result) {
            progress_bar_splash.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            myProgress = 0;
            progress_bar_splash.setSecondaryProgress(0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (myProgress < 100) {
                myProgress++;
                publishProgress(myProgress);
                SystemClock.sleep(100);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress_bar_splash.setProgress(values[0]);
            progress_bar_splash.setSecondaryProgress(values[0] + 5);
        }
    }

}