package com.example.muirsuus.main_navigation.calculation.toplivo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.R;

public class Calculate_toplivo extends Fragment {

    private CalculateToplivoViewModel mViewModel;

    public static Calculate_toplivo newInstance() {
        return new Calculate_toplivo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculate_toplivo_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalculateToplivoViewModel.class);
        // TODO: Use the ViewModel
    }

}