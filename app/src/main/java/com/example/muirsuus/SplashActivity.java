package com.example.muirsuus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.muirsuus.databinding.ActivitySplashBinding;


public class SplashActivity extends AppCompatActivity {
    private static final MutableLiveData<String> _name = new MutableLiveData<>();
    private static final LiveData<String> name = _name;
    ActivitySplashBinding binding;
    int progress = 0;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        Intent intent = getIntent();
        _name.setValue(intent.getStringExtra("name"));

        binding.progress.setMax(100);
        binding.progress.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    progress = i;


                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.progress.setProgress(progress);
                            binding.progressResult.setTextSize(15);
                            if (progress <= 20) {
                                if (name.getValue() != null) {
                                    binding.progressResult.setText("Доброго времени суток " + name.getValue());
                                } else {
                                    binding.progressResult.setText("Добро пожаловать, будем знакомы!");
                                }
                            } else if (progress >= 20 && progress <= 50) {
                                binding.progressResult.setText("Загружаем персональные данные");
                            } else if (progress >= 50 && progress <= 80) {
                                binding.progressResult.setText("Подключаемся к сети");
                            } else binding.progressResult.setText("Готово, добро пожаловать!");
                        }
                    });
                }
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.putExtra("name", name.getValue());
                startActivity(i);
                finish();
            }
        }).start();

    }

}