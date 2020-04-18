package com.example.muirsuus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.muirsuus.adapters.TTHAdapter;

import java.util.ArrayList;
import java.util.List;

public class TTH extends AppCompatActivity  implements TTHAdapter.OnTthListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TTHAdapter adapter;
    private final List<CardClass> SCHEMES = new ArrayList<CardClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tth_activity);

        SCHEMES.add(new CardClass(R.drawable.ic_1,"ТТХ средств связи"));
        SCHEMES.add(new CardClass(R.drawable.ic_2,"ТТХ вооружения"));
        SCHEMES.add(new CardClass(R.drawable.ic_3,"ТТХ АВ и БТ техники"));
        SCHEMES.add(new CardClass(R.drawable.ic_4,"ТТХ средств РХБЗ"));
        SCHEMES.add(new CardClass(R.drawable.ic_1,"ТТХ инженерных средств"));
        SCHEMES.add(new CardClass(R.drawable.ic_2,"Оперативно-техническая служба"));
        SCHEMES.add(new CardClass(R.drawable.ic_3,"Сетевой инженер"));
        SCHEMES.add(new CardClass(R.drawable.ic_4,"Подготовка"));
        SCHEMES.add(new CardClass(R.drawable.ic_1,"Нормативно-правовая база"));

        mRecyclerView = (RecyclerView)findViewById(R.id.point_recycler);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);


        StartAdapter();


    }

    public void StartAdapter(){
        adapter = new TTHAdapter(SCHEMES,this);
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.scrollToPosition(1);
    }

    @Override
    public void onTthCLick(int position) {

        Intent intent = new Intent(TTH.this, MeansOfCommunication.class);
        intent.putExtra("Kek", SCHEMES.get(position));
        startActivity(intent);
    }
}

