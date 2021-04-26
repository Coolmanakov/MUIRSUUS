package com.example.muirsuus.main_navigation.calculation.length_of_kabel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.R;

public class DigitalChannelCM extends Fragment {

    private DigitalChannelCMViewModel mViewModel;

    public static DigitalChannelCM newInstance() {
        return new DigitalChannelCM();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.digital_channel_cm, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DigitalChannelCMViewModel.class);
        // TODO: Use the ViewModel
    }

}