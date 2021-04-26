package com.example.muirsuus.main_navigation.information_fragmnets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.muirsuus.R;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.databinding.FragmentLayoutTthBinding;
import com.example.muirsuus.information_database.PointAndInformation;
import com.example.muirsuus.information_database.information;
import com.example.muirsuus.information_ui.ViewModelFactory;
import com.example.muirsuus.information_ui.information.InformationViewModel;


public class TthFragment extends Fragment {
    private final Army tth = new Army();
    private String point;
    private ViewModelFactory viewModelFactory;
    private FragmentLayoutTthBinding binding;

    public TthFragment(String point) {
        this.point = point;
    }

    public TthFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_layout_tth, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                    information info = pointAndInformation.information;


                    if (info.getTth() != null) {
                        binding.tth.setText(info.getTth());
                    }

                    //Для тех point, у которых нет данных для таблицы, скрываем таблицу
                    if (info.getSpendWorkDay() != null) {
                        binding.spendWorkDay.setText(info.getSpendWorkDay());

                        if (info.getSpendWorkNight() != null) {
                            binding.spendWorkNight.setText(info.getSpendWorkNight());
                        }
                        if (info.getWorkers() != null) {
                            binding.workers.setText(info.getWorkers());
                        }
                        if (info.getTimeOpenAndConect() != null) {
                            binding.timeOpenAndConect.setText(info.getTimeOpenAndConect());
                        }
                        if (info.getKabels() != null) {
                            binding.kabels.setText(info.getKabels());
                        }
                        if (info.getApparatura() != null) {
                            binding.apparatura.setText(info.getApparatura());
                        }
                    } else binding.tableLayout.setVisibility(View.GONE);
                } else {
                    binding.tth.setText("ТТХ отсутсвуют ");
                }

            }
        });
    }


}
