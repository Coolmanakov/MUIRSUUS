package com.example.muirsuus.calculation.zadachi_mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentZadachiBinding;

public class Zadachi extends Fragment {
    FragmentZadachiBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zhivuchest, container, false);

        return binding.getRoot();
    }
}
