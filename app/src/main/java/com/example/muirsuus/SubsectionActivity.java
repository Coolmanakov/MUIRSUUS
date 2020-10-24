package com.example.muirsuus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.adapters.tth_mvvm.TTHAdapter;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.classes.MParcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SubsectionActivity extends AppCompatActivity  {

    final List<CardClass> SCHEMES = new ArrayList<CardClass>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private HashMap<String,List<String>> subsection_list;
    private MParcelable mParcelable;
    private List<String> list_points = new ArrayList<>();
    private Army subsection_photo = new Army();
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subsection_activity);



        Intent intent = getIntent();
        String string_name = intent.getStringExtra("Build_subsection");//получаем название view, на который нажал пльзователь
        Log.d("mLog", "string_name = " + string_name);

        setTitle(string_name);


//---------------------------Проверяем обновления в базе данных---------------------------
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


//---------------------------Проверяем обновления в базе данных---------------------------
        subsection_list = new HashMap<>();
        subsection_list = dataBaseHelper.get_objects_from_DB(string_name); // получаем словарь subsections-list of points из Базы данных и отображем его в recyclerview
        Log.d("mLog", "list subsections = " + subsection_list.toString());
//получаем лист ключей для сортировки
        for( String subsection : subsection_list.keySet()){ // в recyclerview добавляем ключи из словаря
            list_points.add(subsection);
        }
        Collections.sort(list_points);//сортируем лист ключей


        for( String subsection : list_points){ // в recyclerview добавляем ключи из словаря

            subsection_photo = dataBaseHelper.get_subsection_photo(subsection);
            SCHEMES.add(new CardClass(subsection_photo.get_photo_subsection(), subsection));
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

                mParcelable= new MParcelable(subsection_list.get(SCHEMES.get(position).getTitle()));
                Intent intent = new Intent(SubsectionActivity.this, PointsActivity.class);


                Log.d("mLog","list "+ mParcelable.getList());

                intent.putExtra("Build points", mParcelable );//передаём название нажатого view
                intent.putExtra("Name",SCHEMES.get(position).getTitle());
                intent.putExtra(MParcelable.class.getCanonicalName(),mParcelable);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

}










