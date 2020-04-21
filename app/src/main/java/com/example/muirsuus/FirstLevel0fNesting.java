package com.example.muirsuus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.adapters.TTHAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstLevel0fNesting extends AppCompatActivity implements TTHAdapter.OnTthListener {

    final List<CardClass> SCHEMES = new ArrayList<CardClass>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.means_of_comm);

        RecyclerView recyclerView = findViewById(R.id.point_recycler);

        Intent intent = getIntent();


        int name = intent.getIntExtra("Kek", 0);

        switch (name){
            case 0:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"КШМ"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"КАС"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные КО"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"РРС"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"ТРС"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"ССС"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"РС"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные ТОС и АСУ"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные ФПС"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные управления"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Аппаратные электропитания"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Кабели связи"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Другие средства связи"));
                break;

            case 1:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Пистолеты"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Автоматы"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Гранотомёты"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Гранаты"));
                break;

            case 2:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Автомобильная техника"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Бронированная техника"));
                break;
            case 3:
                break;

            case 4:
                break;

            case 5:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Руководящая документация"));
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
                break;
            case 7:
                SCHEMES.add(new CardClass(R.drawable.ic_1,"ТСП"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Топография"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Огневая"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Медицинская"));
                SCHEMES.add(new CardClass(R.drawable.ic_1,"Инженерная"));
                break;
            case 8:
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
        Intent intent = new Intent(FirstLevel0fNesting.this, SecondLevelOfNesting.class);
        intent.putExtra("Kek", SCHEMES.get(position));
        startActivity(intent);

    }
}










