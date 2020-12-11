package com.example.muirsuus.calculation.SubscriberNetwork;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SubscriberNetworkFragment4 extends Fragment {

    private SubscriberNetworkViewModel model;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_subscriber_network_4,
                container,
                false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);

        TableLayout cableTable = view.findViewById(R.id.cable_table);
//        HashMap<String, ArrayList<String>> deviceTypeMap = new HashMap<>();

        model.getDeviceType().observe(getViewLifecycleOwner(), deviceType -> {
            for (Map.Entry<String, ArrayList<String>> deviceEntry : deviceType.entrySet()) {
                for (String device : deviceEntry.getValue()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1f);
                    TableRow officialInfoRow = new TableRow(getContext());
                    TextView officialTextView = new TextView(getContext());
                    TableRow deviceRoomInfoRow = new TableRow(getContext());
                    TextView deviceTextView = new TextView(getContext());
                    TableRow optionRow = new TableRow(getContext());
                    Spinner cableTypeSpinner = new Spinner(getContext());
                    EditText cableLarge = new EditText(getContext());

                    officialTextView.setLayoutParams(layoutParams);
                    officialTextView.setText(deviceEntry.getKey());
                    officialTextView.setTextSize(16);

                    deviceTextView.setLayoutParams(layoutParams);
                    deviceTextView.setText(device);
                    deviceTextView.setTextSize(16);

                    cableTypeSpinner.setLayoutParams(layoutParams);
                    cableTypeSpinner.setAdapter(new CommonSpinnerAdapter(
                            requireContext(),
                            Arrays.asList("П-274М", "П-269 4x2 + 2x4")));

                    cableLarge.setLayoutParams(layoutParams);
                    cableLarge.setHint("По умолчанию: 0");
                    cableLarge.setRawInputType(InputType.TYPE_CLASS_NUMBER);

                    officialInfoRow.addView(officialTextView);
                    deviceRoomInfoRow.addView(deviceTextView);
                    optionRow.addView(cableTypeSpinner);
                    optionRow.addView(cableLarge);
                    cableTable.addView(officialInfoRow);
                    cableTable.addView(deviceRoomInfoRow);
                    cableTable.addView(optionRow);
                }
            }
        });

        view.findViewById(R.id.transition_button_4).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.subscriber_network_4_to_5)
        );
    }
}
