package com.example.muirsuus;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.classes.MParcelable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class PointsActivity extends AppCompatActivity {
    final List<CardClass> SCHEMES = new ArrayList<CardClass>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private List<String> list_points = new ArrayList<>();
    private Army point_photo = new Army();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subsection_activity);




        //получаем лист из points
        MParcelable string_name =  (MParcelable) getIntent().getParcelableExtra(MParcelable.class.getCanonicalName());//получаем объект SHEMES, на который нажал пльзователь

        Log.d("mLog", "string_name = " + string_name.getList());


//---------------------------Проверяем обновления в базе данных---------------------------
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

//---------------------------Проверяем обновления в базе данных---------------------------


        list_points = string_name.getList();//сортируем лист ключей
        Collections.sort(list_points);
        for( String point : list_points){ // в recyclerview добавляем значения из словаря, который передали с помощь intent
            point_photo = dataBaseHelper.get_point_photo(point);//поиск в бд  названия фото
            SCHEMES.add(new CardClass(point_photo.get_photo_point(), point));//добавляем фото и title
        }

//----------------------------Создание и заполнение recyclerview---------------------------------------
        mRecyclerView = (RecyclerView) findViewById(R.id.subsection_recycler);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new TTHAdapter(SCHEMES);
//----------------------------Создание и заполнение recyclerview---------------------------------------

        adapter.SetOnItemClickListener(new TTHAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(PointsActivity.this, InformActivity.class);
                intent.putExtra("Build points", SCHEMES.get(position).getTitle());//передаём название нажатоо view
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }
}