package com.example.muirsuus.calculation.SubscriberNetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CheckableSpinnerAdapter;
import com.example.muirsuus.calculation.SubscriberNetwork.SubscriberNetworkRepository.SpinnerList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubscriberNetworkFragment2 extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_subscriber_network_2,
                container,
                false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TableLayout deviceTable = view.findViewById(R.id.device_table);

        for (String official: SubscriberNetworkRepository.mOfficialType) {
            TableRow officialRow = new TableRow(getContext());
            TextView officialTextView = new TextView(getContext());
            Spinner deviceSpinner = new Spinner(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1f);

            SubscriberNetworkRepository.mDeviceType.put(official, new ArrayList<>());

            officialTextView.setLayoutParams(layoutParams);
            officialTextView.setText(official);
            officialTextView.setTextSize(16);

            deviceSpinner.setLayoutParams(layoutParams);
            deviceSpinner.setAdapter(new CheckableSpinnerAdapter(
                    requireContext(),
                    SubscriberNetworkRepository.getList(SpinnerList.DEVICE_LIST),
                    SubscriberNetworkRepository.mDeviceType.get(official)));

            officialRow.addView(officialTextView);
            officialRow.addView(deviceSpinner);

            deviceTable.addView(officialRow);
        }

        view.findViewById(R.id.transition_button_2).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.subscriber_network_2_to_3)
        );
    }
}
