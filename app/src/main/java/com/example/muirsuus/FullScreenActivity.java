package com.example.muirsuus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muirsuus.adapters.FullSizeAdapter;

public class FullScreenActivity extends AppCompatActivity {

    MyViewPager viewPager;
    String[] images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        viewPager = (MyViewPager)findViewById(R.id.viewPager);

        if(savedInstanceState == null){
            Intent i = getIntent();
            images = i.getStringArrayExtra("IMAGES");
            position = i.getIntExtra("POSITION",0);
        }

        Log.d("mLog", "get string array of images " + images);
        FullSizeAdapter fullSizeAdapter =  new FullSizeAdapter(this,images);

        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position,true);
    }
}