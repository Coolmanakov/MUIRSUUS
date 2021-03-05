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

public class FindCarFragmnet extends Fragment {

    private FindCarFragmnetViewModel mViewModel;

    public static FindCarFragmnet newInstance() {
        return new FindCarFragmnet();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.find_car_fragmnet_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FindCarFragmnetViewModel.class);
        // TODO: Use the ViewModel
    }

}