package com.example.muirsuus.calculation.zhivuchest_mvvm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentZhivuchestBinding;


public class Zhivuchest extends Fragment {
    FragmentZhivuchestBinding binding;
    EditText count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_zhivuchest, container, false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ZhivuchestViewModel zhivuchestViewModel = new ZhivuchestViewModel();
        zhivuchestViewModel.setContext(getContext());
        binding.setZhivuchestViewModel(zhivuchestViewModel);


        binding.setLifecycleOwner(this);

    }
}