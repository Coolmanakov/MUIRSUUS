package com.example.muirsuus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.classes.CardClass;

import java.util.ArrayList;
import java.util.List;

public class SectionActivity extends AppCompatActivity   {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private final List<CardClass> SCHEMES = new ArrayList<CardClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.subsection_activity);



        SCHEMES.add(new CardClass(R.drawable.ratsia,"Средства связи"));
        SCHEMES.add(new CardClass(R.drawable.kalash,"Вооружение"));
        SCHEMES.add(new CardClass(R.drawable.tank,"АВ и БТ техника"));
        SCHEMES.add(new CardClass(R.drawable.radiatsia,"Средства РХБЗ"));
        SCHEMES.add(new CardClass(R.drawable.lopata,"Инженерные средства"));
        SCHEMES.add(new CardClass(R.drawable.gaechny_klyuch,"Оперативно-техническая служба"));
        SCHEMES.add(new CardClass(R.drawable.kompyuter,"Сетевой инженер"));
        SCHEMES.add(new CardClass(R.drawable.palatka,"Подготовка"));
        SCHEMES.add(new CardClass(R.drawable.kniga,"Нормативно-правовая база"));

        mRecyclerView = (RecyclerView)findViewById(R.id.subsection_recycler);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new TTHAdapter(SCHEMES);


        /*adapter.SetOnItemClickListener(new TTHAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SCHEMES.get(position);
                Intent intent = new Intent(TTHActivity.this, FirstLevel0fNesting.class);
                intent.putExtra("Build_subsection", position);
                startActivity(intent);
            }
        });*/

        adapter.SetOnItemClickListener(new TTHAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SCHEMES.get(position);

                Intent intent = new Intent(SectionActivity.this, SubsectionActivity.class);
                intent.putExtra("Build_subsection", SCHEMES.get(position).getTitle());//передаём название нажатоо view
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);


    }



}

