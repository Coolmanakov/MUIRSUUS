package com.example.muirsuus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.classes.MyObject;

import java.util.ArrayList;
import java.util.List;

public class FirstLevel0fNesting extends AppCompatActivity implements TTHAdapter.OnTthListener {

    final List<CardClass> SCHEMES = new ArrayList<CardClass>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private MyObject myObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.means_of_comm);

        RecyclerView recyclerView = findViewById(R.id.point_recycler);

        Intent intent = getIntent();
        int name = intent.getIntExtra("Kek", 0);

        switch (name){
            case 0:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"КШМ"));//0
                SCHEMES.add(new CardClass(R.drawable.ic_1,"КАС"));//1
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные КО"));//2
                SCHEMES.add(new CardClass(R.drawable.ic_1,"РРС"));//3
                SCHEMES.add(new CardClass(R.drawable.ic_1,"ТРС"));//4
                SCHEMES.add(new CardClass(R.drawable.ic_1,"ССС"));//5
                SCHEMES.add(new CardClass(R.drawable.ic_1,"РС"));//6
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные ТОС и АСУ"));//7
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные ФПС"));//8
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные управления"));//9
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные электропитания"));//10
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Кабели связи"));//11
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Другие средства связи"));//12
                myObject = new MyObject(0, 0);
                break;

            case 1:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Пистолеты"));//13
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Автоматы"));//14
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Гранотомёты"));//15
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Гранаты"));//16
                myObject = new MyObject(1, 0);
                break;

            case 2:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Автомобильная техника"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Бронированная техника"));
                myObject = new MyObject(2, 0);
                break;
            case 3:
                myObject = new MyObject(3, 0);
                break;

            case 4:
                myObject = new MyObject(4, 0);
                break;

            case 5:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Руководящая документация"));
                myObject = new MyObject(5, 0);
                break;

            case 6:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Инструменты для монтажа"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Монтажные работы"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Линии связи"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Сетевые протоколы"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Порты TCP и UDP"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Сетевое оборудование"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Электрика"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Электроника"));
                myObject = new MyObject(6, 0);
                break;
            case 7:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"ТСП"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Топография"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Огневая"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Медицинская"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Инженерная"));
                myObject = new MyObject(7, 0);
                break;
            case 8:
                myObject = new MyObject(8, 0);
                break;
                //something new
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.point_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        StartAdapter();
    }

    private void StartAdapter() {
        adapter = new TTHAdapter(SCHEMES, this);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onTthCLick(int position) {
        myObject.i = position;

        if (myObject.s  == 1){
            myObject.i+=13;
        }
        Intent intent = new Intent(FirstLevel0fNesting.this, SecondLevelOfNesting.class);
        intent.putExtra(MyObject.class.getCanonicalName(), myObject);
        startActivity(intent);

    }
}










