package com.example.muirsuus.calculation;

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
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SubscriberNetworkFragment4 extends Fragment {
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

        TableLayout cableTable = view.findViewById(R.id.cable_table);

        for (Map.Entry<String, ArrayList<String>> deviceEntry : SubscriberNetworkRepository.mDeviceType.entrySet()) {
            for (String device : deviceEntry.getValue()) {
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1f);
                TextView officialTextView = new TextView(getContext());
                TableRow infoRow = new TableRow(getContext());
                TableRow optionRow = new TableRow(getContext());
                TextView deviceTextView = new TextView(getContext());
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

                infoRow.addView(officialTextView);
                infoRow.addView(deviceTextView);
                optionRow.addView(cableTypeSpinner);
                optionRow.addView(cableLarge);
                cableTable.addView(infoRow);
                cableTable.addView(optionRow);
            }
        }

        view.findViewById(R.id.transition_button_4).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.subscriber_network_4_to_5)
        );
    }
}
