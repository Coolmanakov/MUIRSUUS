package com.example.muirsuus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.classes.CardClass;
import com.example.muirsuus.classes.MyObject;

import java.util.ArrayList;
import java.util.List;

public class FirstLevel0fNesting extends AppCompatActivity  {

    final List<CardClass> SCHEMES = new ArrayList<CardClass>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private MyObject myObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        RecyclerView recyclerView = findViewById(R.id.point_recycler);

        Intent intent = getIntent();
        int name = intent.getIntExtra("Kek", 0);

        switch (name) {
            case 0:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "КШМ"));//0
                SCHEMES.add(new CardClass(R.drawable.ic_1, "КАС"));//1
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Аппаратные КО"));//2
                SCHEMES.add(new CardClass(R.drawable.ic_1, "РРС"));//3
                SCHEMES.add(new CardClass(R.drawable.ic_1, "ТРС"));//4
                SCHEMES.add(new CardClass(R.drawable.ic_1, "ССС"));//5
                SCHEMES.add(new CardClass(R.drawable.ic_1, "РС"));//6
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Аппаратные ТОС и АСУ"));//7
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Аппаратные ФПС"));//8
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Аппаратные управления"));//9
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Аппаратные электропитания"));//10
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Кабели связи"));//11
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Другие средства связи"));//12
                myObject = new MyObject(0, 0);
                break;

            case 1:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Пистолеты"));//13
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Автоматы"));//14
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Гранотомёты"));//15
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Гранаты"));//16
                myObject = new MyObject(1, 0);
                break;

            case 2:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Автомобильная техника"));//17
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Бронированная техника"));//18
                myObject = new MyObject(2, 0);
                break;
            case 3:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Современные средства массового поражения"));//19
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Средства индивидуальной защиты"));//20
                myObject = new MyObject(3, 0);
                break;

            case 4:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Инженерная разведка"));//21
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Инженерные заграждения"));//22
                myObject = new MyObject(4, 0);
                break;

            case 5:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Руководящая документация"));//23
                myObject = new MyObject(5, 0);
                break;

            case 6:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Инструменты для монтажа"));//24
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Монтажные работы"));//25
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Линии связи"));//26
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Сетевые протоколы"));//27
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Порты TCP и UDP"));//28
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Сетевое оборудование"));//29
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Электрика"));//30
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Электроника"));//31
                myObject = new MyObject(6, 0);
                break;
            case 7:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "ТСП"));//32
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Топография"));//33
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Огневая"));//34
                SCHEMES.add(new CardClass(R.drawable.ic_1, "Медицинская"));//35
                myObject = new MyObject(7, 0);
                break;
            case 8:
                SCHEMES.add(new CardClass(R.drawable.ic_1, "МО"));//36
                SCHEMES.add(new CardClass(R.drawable.ic_1, "ФСБ"));//37
                myObject = new MyObject(8, 0);
                break;
            //something new
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.point_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);


        adapter = new TTHAdapter(SCHEMES);


        adapter.SetOnItemClickListener(new TTHAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                myObject.i = position;

                if (myObject.s  == 1){
                    myObject.i+=13;
                }
                if (myObject.s  == 2){
                    myObject.i+=17;
                }
                if (myObject.s  == 3){
                    myObject.i+=19;
                }
                if (myObject.s  == 4){
                    myObject.i+=21;
                }
                if (myObject.s  == 5){
                    myObject.i+=23;
                }
                if (myObject.s  == 6){
                    myObject.i+=24;
                }
                if (myObject.s  == 7){
                    myObject.i+=32;
                }
                if (myObject.s  == 8){
                    myObject.i+=36;
                }

                Intent intent = new Intent(FirstLevel0fNesting.this, SecondLevelOfNesting.class);
                intent.putExtra(MyObject.class.getCanonicalName(), myObject);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

}










