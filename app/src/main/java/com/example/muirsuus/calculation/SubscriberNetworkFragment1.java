package com.example.muirsuus.calculation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CheckableSpinnerAdapter;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;
import com.example.muirsuus.calculation.SubscriberNetworkRepository.SpinnerList;
import com.example.muirsuus.calculation.SubscriberNetworkRepository.SpinnerMap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubscriberNetworkFragment1 extends Fragment {

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

        Spinner divisionTypeSpinner = view.findViewById(R.id.division_type_spinner);
        Spinner controlPointTypeSpinner = view.findViewById(R.id.control_point_type_spinner);
        Spinner officialsTypeSpinner = view.findViewById(R.id.official_type_spinner);

        divisionTypeSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                SubscriberNetworkRepository.getList(SpinnerList.DIVISION_LIST)
        ));

        // TODO: replace all listeners with a new one parameter in CommonSpinnerAdapter
        divisionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int itemPosition,
                    long itemId) {
                String newValue = parent.getSelectedItem().toString();

                if (!SubscriberNetworkRepository.mDivisionType.equals(newValue)) {
                    SubscriberNetworkRepository.mDivisionType = newValue;
                    SubscriberNetworkRepository.mControlPointType = "";
                    SubscriberNetworkRepository.mOfficialType = new ArrayList<>();

                    controlPointTypeSpinner.setAdapter(new CommonSpinnerAdapter(
                            requireContext(),
                            SubscriberNetworkRepository.getList(SpinnerMap.CONTROL_POINT_MAP)
                    ));
                    officialsTypeSpinner.setAdapter(new CheckableSpinnerAdapter(
                            requireContext(),
                            SubscriberNetworkRepository.getList(SpinnerMap.OFFICIAL_MAP),
                            SubscriberNetworkRepository.mOfficialType));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // DO NOTHING
            }
        });

        controlPointTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int itemPosition,
                    long itemId) {
                SubscriberNetworkRepository.mControlPointType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // DO NOTHING
            }
        });

        view.findViewById(R.id.transition_button_1).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.subscriber_network_1_to_2)
        );
    }
}
