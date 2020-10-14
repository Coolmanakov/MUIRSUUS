package com.example.muirsuus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
    ProgressBar startProgressBar;
    TextView continueText;
    Intent intent;


    Handler handler;

    int progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        progress = 0;

        startProgressBar = (ProgressBar) findViewById(R.id.start_progress_bar);

        intent = new Intent(MainActivity.this, FirstActivity.class);

        startProgressBar.setVisibility(ProgressBar.VISIBLE);
        startProgressBar.setMax(100);

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
                            startProgressBar.setProgress(progress);
                        }
                    });

                }
                ///startProgressBar.setVisibility(ProgressBar.INVISIBLE);

                startActivity(intent);
                MainActivity.this.finish();

            }
        }).start();


    }

}
