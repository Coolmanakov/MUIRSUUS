package com.example.muirsuus.main_navigation.information_fragmnets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentLayoutDescriptionBinding;
import com.example.muirsuus.information_database.PointAndInformation;
import com.example.muirsuus.information_ui.ViewModelFactory;
import com.example.muirsuus.information_ui.information.InformationViewModel;

import java.util.ArrayList;
import java.util.List;


public class DescriptionFragment extends Fragment {
    TextView title;

    TextView description_text;


    private final List<String> photo_list = new ArrayList<>();
    String point;
    private FragmentLayoutDescriptionBinding binding;
    private ViewModelFactory viewModelFactory;

    //создание конструктора класса для получение нужного ID
    public DescriptionFragment(String point) {
        this.point = point;
    }

    public DescriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_layout_description, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.setLifecycleOwner(this);
        setUpViewModel();
    }

    private void setUpViewModel() {
        viewModelFactory = new ViewModelFactory(getContext());
        viewModelFactory.setPoint(point);
        InformationViewModel informationViewModel = new ViewModelProvider(this, viewModelFactory).get(InformationViewModel.class);
        informationViewModel.getInformation().observe(binding.getLifecycleOwner(), new Observer<PointAndInformation>() {
            @Override
            public void onChanged(PointAndInformation pointAndInformation) {
                if (pointAndInformation != null) {
                    binding.description.setText(pointAndInformation.information.getDescription());
                } else {
                    binding.description.setText("Назначение отсутсвует ");
                }
            }
        });
    }
}