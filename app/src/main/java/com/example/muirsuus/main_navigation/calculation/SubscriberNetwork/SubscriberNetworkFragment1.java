package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CheckableSpinnerAdapter;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;
import com.example.muirsuus.main_navigation.calculation.SubscriberNetwork.SubscriberNetworkRepository.SpinnerList;
import com.example.muirsuus.main_navigation.calculation.SubscriberNetwork.SubscriberNetworkRepository.SpinnerMap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SubscriberNetworkFragment1 extends Fragment {

    private SubscriberNetworkViewModel model;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_subscriber_network_1,
                container,
                false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);

        Spinner divisionTypeSpinner = view.findViewById(R.id.division_type_spinner);
        divisionTypeSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                SubscriberNetworkRepository.getList(SpinnerList.DIVISION_LIST),
                R.string.division_type
        ));
        divisionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPosition, long itemId) {
                Object selectedItem = parent.getSelectedItem();
                String newValue = "";
                if (selectedItem != null) {
                    newValue = selectedItem.toString();
                }
                model.setDivisionType(newValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                model.setDivisionType("");
            }
        });

        ArrayList<String> officialsTypeList = new ArrayList<>();
        model.getDivisionType().observe(getViewLifecycleOwner(), divisionType -> {

            List<String> controlPointList = new ArrayList<>();
            List<String> officialList = new ArrayList<>();
            @StringRes int divisionTypeSpinnerTitle = R.string.division_type_not_choosen;
            @StringRes int officialsTypeSpinnerTitle = R.string.division_type_not_choosen;

            if (!divisionType.isEmpty()) {
                controlPointList = SubscriberNetworkRepository.getList(SpinnerMap.CONTROL_POINT_MAP, divisionType);
                officialList = SubscriberNetworkRepository.getList(SpinnerMap.OFFICIAL_MAP, divisionType);
                divisionTypeSpinnerTitle = R.string.control_point_type;
                officialsTypeSpinnerTitle = R.string.officials_type;
            }

            CommonSpinnerAdapter controlPointTypeSpinnerAdapter = new CommonSpinnerAdapter(
                    requireContext(),
                    controlPointList,
                    divisionTypeSpinnerTitle
            );
            CheckableSpinnerAdapter officialsTypeSpinnerAdapter = new CheckableSpinnerAdapter(
                    requireContext(),
                    officialList,
                    officialsTypeList,
                    officialsTypeSpinnerTitle
            );

            Spinner controlPointTypeSpinner = view.findViewById(R.id.control_point_type_spinner);
            Spinner officialsTypeSpinner = view.findViewById(R.id.official_type_spinner);

            controlPointTypeSpinner.setAdapter(controlPointTypeSpinnerAdapter);
            officialsTypeSpinner.setAdapter(officialsTypeSpinnerAdapter);

            controlPointTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int itemPosition, long itemId) {
                    Object selectedItem = parent.getSelectedItem();
                    String newValue = "";
                    if (selectedItem != null) {
                        newValue = selectedItem.toString();
                    }
                    model.setControlPointType(newValue);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    model.setControlPointType("");
                }
            });


        });

        view.findViewById(R.id.transition_button_1).setOnClickListener(v -> {
                    model.setOfficialType(officialsTypeList);
                    Navigation.findNavController(view).navigate(R.id.subscriber_network_1_to_2);
                }
        );
    }
}
