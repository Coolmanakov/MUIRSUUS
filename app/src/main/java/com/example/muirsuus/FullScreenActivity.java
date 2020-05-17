package com.example.muirsuus;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.muirsuus.adapters.FullSizeAdapter;
import com.example.muirsuus.classes.HackyViewPager;

public class FullScreenActivity extends AppCompatActivity {

    HackyViewPager viewPager;
    String[] images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        if(savedInstanceState==null){
            Intent i = getIntent();
            images = i.getStringArrayExtra("IMAGES");
            position = i.getIntExtra("POSITION",0);
        }

        viewPager = (HackyViewPager)findViewById(R.id.viewPager);

        FullSizeAdapter fullSizeAdapter =  new FullSizeAdapter(this,images);
        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position,true);
    }
}