package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.main_navigation.calculation.SubscriberNetwork.SubscriberNetworkRepository.SpinnerList;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SubscriberNetworkFragment3 extends Fragment {

    private SubscriberNetworkViewModel model;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_subscriber_network_3,
                container,
                false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);
        HashMap<String, Integer> deviceRoomTypeMap = new HashMap<>();

        TableLayout deviceTable = view.findViewById(R.id.device_room_table);

        for (String deviceRoom : SubscriberNetworkRepository.getList(SpinnerList.DEVICE_ROOM_LIST)) {
            TableRow deviceRoomRow = new TableRow(getContext());
            TextView deviceRoomTextView = new TextView(getContext());
            EditText countEditText = new EditText(getContext());

            deviceRoomTypeMap.put(deviceRoom, 0);

            deviceRoomTextView.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1f));
            deviceRoomTextView.setText(deviceRoom);
            deviceRoomTextView.setTextSize(16);

            deviceRoomTextView.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1f));
            countEditText.setHint("По умолчанию: 0");
            countEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

            deviceRoomRow.addView(deviceRoomTextView);
            deviceRoomRow.addView(countEditText);

            deviceTable.addView(deviceRoomRow);
        }

        view.findViewById(R.id.transition_button_3).setOnClickListener(v -> {
                    model.setDeviceRoomType(deviceRoomTypeMap);
                    Navigation.findNavController(view).navigate(R.id.subscriber_network_3_to_4);
                }
        );
    }
}
