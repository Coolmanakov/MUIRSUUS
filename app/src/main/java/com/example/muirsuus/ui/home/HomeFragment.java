package com.example.muirsuus.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.CardClass;
import com.example.muirsuus.R;
import com.example.muirsuus.InfBlock;
import com.example.muirsuus.adapters.StartAdapter;
import com.example.muirsuus.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StartAdapter adapter;
    private Button inf_btn, calc_btn, manage_btn;
    final public List<CardClass> SCHEMES = new ArrayList<CardClass>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, mRecyclerView , false);
        SCHEMES.add(new CardClass(R.drawable.ic_1,"Средства связи"));
        SCHEMES.add(new CardClass(R.drawable.ic_2,"Аппаратные"));
        SCHEMES.add(new CardClass(R.drawable.ic_3,"Документы ОТС"));
        SCHEMES.add(new CardClass(R.drawable.ic_4,"История"));



        mRecyclerView = (RecyclerView)root.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //--------------------------------------------
        //------------------------------------
        //title = (TextView)findViewById(R.id.tvStartActivity);
        //------------------------------------
        adapter = new StartAdapter(SCHEMES);
        mRecyclerView.setAdapter(adapter);

        manage_btn = (Button)root.findViewById(R.id.manage_btn);
        View.OnClickListener onMBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                startActivity(intent);
            }
        };

        manage_btn.setOnClickListener(onMBtn);

        inf_btn = (Button)root.findViewById(R.id.inf_btn);
        View.OnClickListener onInfBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InfBlock.class);
                startActivity(intent);
            }
        };
        inf_btn.setOnClickListener(onInfBtn);
        return root;
    }
}
