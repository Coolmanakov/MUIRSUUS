package com.example.muirsuus.ui.fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.muirsuus.R;

public class HistoryFragment extends Fragment {
    int a;
    //создание конструктора класса для получение нужного ID
    public HistoryFragment(int a){
        this.a = a;
    }
    public HistoryFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_portfel,container,false);

        return root;
    }
}
