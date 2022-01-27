package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int second = 0;

    //Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            second = savedInstanceState.getInt("seconds");

            running = savedInstanceState.getBoolean("running");

            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    private void runTimer() {

        //Get the text view
        final TextView timeView = findViewById(R.id.time_view);

        //create Handler
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = second / 3600;
                int minutes = (second % 3600) / 60;
                int secs = second % 60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if(running){
                    second++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    public void onClickStart(View view) {

        running = true;
    }

    public void onClickStop(View view) {

        running = false;
    }

    public void onClickStopReset(View view) {
        running = false;
        second = 0;
    }
}