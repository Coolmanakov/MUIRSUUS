package com.example.muirsuus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Button btnHuge;
    ProgressBar startPogressBar;
    TextView continueText;
    Intent intent;

    Handler handler;

    int progress;
    boolean canSwipe;
    boolean isWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        progress = 0;
        canSwipe = false;
        isWorking = false;

        btnHuge = (Button) findViewById(R.id.huge_btn);
        startPogressBar = (ProgressBar) findViewById(R.id.start_progress_bar);
        continueText = (TextView) findViewById(R.id.continueText);

        intent = new Intent(MainActivity.this, FirstActivity.class);

        startPogressBar.setVisibility(ProgressBar.VISIBLE);
        startPogressBar.setMax(100);

        continueText.setVisibility(TextView.INVISIBLE);

        handler = new Handler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= 100; i++) {
                    progress = i;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            startPogressBar.setProgress(progress);
                        }
                    });

                }
                startPogressBar.setVisibility(ProgressBar.INVISIBLE);
                canSwipe = true;

                startActivity(intent);

                MainActivity.this.finish();

                isWorking = true;
            }
        }).start();
    }

}
