package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubscriberNetworkFragment2 extends Fragment {
    SubscriberNetworkViewModel viewModel;
    List<DeviceRoomInfo> deviceRoomList = new LinkedList<>();
    View forwardButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriber_network_2, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);

        forwardButton = view.findViewById(R.id.forward_button_2);

        // Setting Recyclers View
        RecyclerView deviceRecyclerView = view.findViewById(R.id.device_recycler_view);
        deviceRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false));
        deviceRecyclerView.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL));
        DeviceRecyclerAdapter deviceRecyclerAdapter =
                new DeviceRecyclerAdapter(requireContext(), getViewLifecycleOwner());
        deviceRecyclerView.setAdapter(deviceRecyclerAdapter);
        viewModel.deviceRecyclerList.observe(
                getViewLifecycleOwner(),
                deviceRecyclerList -> deviceRecyclerAdapter.notifyDataSetChanged()
        );

        // Updating Recyclers list
        viewModel.deviceMap.observe(
                getViewLifecycleOwner(),
                deviceMap -> {
                    LinkedList<DeviceInfo> deviceRecyclerList = new LinkedList<>();
                    List<String> officials = new LinkedList<>();
                    int i = 0;

                    while (i < viewModel.deviceRecyclerList.size()) {
                        DeviceInfo item = viewModel.deviceRecyclerList.get(i);

                        if (!item.isHeader) continue;

                        officials.add(item.title);

                        if (deviceMap.containsKey(item.title)) {
                            deviceRecyclerList.add(item);
                            List<String> devices = new LinkedList<String>() {{
                                List<String> list = deviceMap
                                        .get(officials.get(officials.size() - 1));
                                if (list != null) addAll(list);
                            }};
                            item = viewModel.deviceRecyclerList.get(++i);

                            while (!item.isHeader) {
                                if (devices.remove(item.title))
                                    deviceRecyclerList.add(item);

                                if (++i >= viewModel.deviceRecyclerList.size()) break;
                                item = viewModel.deviceRecyclerList.get(i);
                            }

                            for (String device : devices) {
                                deviceRecyclerList.add(new DeviceInfo(device, false));
                            }
                        } else
                            while (!item.isHeader) i++;
                    }

                    for (Map.Entry<String, List<String>> entry : deviceMap.entrySet()) {
                        String official = entry.getKey();

                        if (officials.contains(official)) continue;

                        deviceRecyclerList.add(new DeviceInfo(official, true));
                        for (String device : entry.getValue())
                            deviceRecyclerList.add(new DeviceInfo(device, false));
                    }

                    viewModel.deviceRecyclerList.setValue(deviceRecyclerList);
                }
        );
        viewModel.deviceRecyclerList.observe(
                getViewLifecycleOwner(),
                list -> deviceRecyclerAdapter.notifyDataSetChanged()
        );

        forwardButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.subscriber_network_2_forward));
        view.findViewById(R.id.backward_button_2).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.subscriber_network_2_backward));
    }

    private class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceItemViewHolder> {
        Context context;

        public DeviceRecyclerAdapter(Context context, LifecycleOwner lifecycleOwner) {
            this.context = context;
        }

        @NonNull
        @Override
        public DeviceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DeviceItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceItemViewHolder holder, int position) {
            DeviceInfo deviceInfo = viewModel.deviceRecyclerList.get(position);
            holder.deviceName.setText(deviceInfo.title);

            if (deviceInfo.isHeader) {
                holder.layout.setBackgroundResource(R.color.highlight);
                holder.cableLength.setVisibility(View.GONE);
                holder.deviceRoomSpinner.setVisibility(View.GONE);
                holder.deviceRoomIndex.setVisibility(View.GONE);
            } else {
                holder.layout.setBackgroundResource(R.color.white);
                holder.cableLength.setVisibility(View.VISIBLE);
                holder.deviceRoomSpinner.setVisibility(View.VISIBLE);
                holder.deviceRoomIndex.setVisibility(View.VISIBLE);

                holder.editTextListener.setEditText(position);
                if (deviceInfo.cableLength != null && deviceInfo.cableLength != 0)
                    holder.cableLength.setText(formatIntToString(deviceInfo.cableLength));

                List<String> deviceRooms = viewModel.getDeviceRoomListWithDevice(deviceInfo.title);
                holder.deviceRoomSpinner.setAdapter(new CommonSpinnerAdapter(
                        context,
                        deviceRooms,
                        "Аппаратная"
                ));
                if (deviceInfo.deviceRoom != null)
                    holder.deviceRoomSpinner.setSelection(
                            deviceRooms.indexOf(deviceInfo.deviceRoom) + 1);
                holder.deviceRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int var3, long var4) {
                        if (adapterView.getSelectedItem() == null) {
                            holder.deviceRoomIndex.setVisibility(View.INVISIBLE);
                            return;
                        }

                        AtomicBoolean onBind = new AtomicBoolean(false);

                        holder.deviceRoomIndex.setVisibility(View.VISIBLE);
                        deviceInfo.deviceRoom = adapterView.getSelectedItem().toString();
                        deviceInfo.updateAvailableDeviceRooms();
                        CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(
                                context,
                                deviceInfo.availableDeviceRooms,
                                "Номер аппаратной");
                        holder.deviceRoomIndex.setAdapter(adapter);
                        holder.deviceRoomIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int var3, long var4) {
                                if (onBind.get()) return;
                                if (holder.deviceRoomIndex.getSelectedItem() == null) return;

                                String[] selectedIndex = holder.deviceRoomIndex.getSelectedItem()
                                        .toString()
                                        .split(" ");

                                if (selectedIndex.length == 3)
                                    deviceRoomList.add(new DeviceRoomInfo(deviceInfo.deviceRoom, selectedIndex[0]));

                                Stream<DeviceRoomInfo> aaa = deviceRoomList.stream()
                                        .filter(x -> {
                                            Log.d("SN_2", x.name.equals(deviceInfo.deviceRoom) + "");
                                            Log.d("SN_2", x.index.equals(selectedIndex[0]) + "");
                                            return x.name.equals(deviceInfo.deviceRoom) && x.index.equals(selectedIndex[0]);
                                        });
                                DeviceRoomInfo deviceRoom = deviceRoomList.stream()
                                        .filter(x -> x.name.equals(deviceInfo.deviceRoom)
                                                && x.index.equals(selectedIndex[0]))
                                        .findAny()
                                        .orElse(null);

                                if (deviceRoom == null
                                        || deviceInfo.deviceRoomInfo != null
                                        && deviceInfo.deviceRoomInfo.equals(deviceRoom))
                                    return;

                                if (deviceInfo.deviceRoomInfo != null)
                                    deviceInfo.deviceRoomInfo.disconnectDevice(deviceInfo);
                                deviceRoom.connectDevice(deviceInfo);
                                updateDeviceRoomList();

                                checkRecycler();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                //DO NOTHING
                            }
                        });

                        viewModel.deviceRoomList.observe(
                                getViewLifecycleOwner(),
                                deviceRoomInfoList -> {
                                    onBind.set(true);
                                    deviceInfo.updateAvailableDeviceRooms();
                                    adapter.notifyDataSetChanged();
                                    if (deviceInfo.deviceRoomInfo != null)
                                        holder.deviceRoomIndex.setSelection(
                                                deviceInfo.availableDeviceRooms
                                                        .indexOf(deviceInfo.deviceRoomInfo.index) + 1);
                                    onBind.set(false);
                                }
                        );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //DO NOTHING
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return viewModel.deviceRecyclerList.size();
        }

        private class DeviceItemViewHolder extends RecyclerView.ViewHolder {
            public EditTextListener editTextListener;

            final View layout;
            final TextView deviceName;
            final Spinner deviceRoomSpinner;
            final Spinner deviceRoomIndex;
            final EditText cableLength;

            DeviceItemViewHolder(@NonNull View itemView) {
                super(itemView);

                layout = itemView.findViewById(R.id.device_recycler_item_layout);
                deviceName = itemView.findViewById(R.id.device_name);
                deviceRoomSpinner = itemView.findViewById(R.id.device_room_spinner);
                deviceRoomIndex = itemView.findViewById(R.id.device_room_index);
                cableLength = itemView.findViewById(R.id.cable_length);

                editTextListener = new EditTextListener();
                cableLength.addTextChangedListener(editTextListener);
            }
        }

        private class EditTextListener implements TextWatcher {
            private int position;

            public void setEditText(int position) {
                this.position = position;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                DeviceInfo itemInfo = viewModel.deviceRecyclerList.get(position);
                itemInfo.cableLength = Integer.parseInt(charSequence.toString());
                checkRecycler();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // DO NOTHING
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // DO NOTHING
            }
        }
    }

    public class DeviceInfo {
        boolean isHeader;
        String title;
        Integer cableLength;
        String deviceRoom;
        List<String> availableDeviceRooms = new LinkedList<>();
        DeviceRoomInfo deviceRoomInfo;

        DeviceInfo(String title, boolean isHeader) {
            this.title = title;
            this.isHeader = isHeader;
        }

        public void updateAvailableDeviceRooms() {
            if (deviceRoom == null) return;
            availableDeviceRooms.clear();
            List<DeviceRoomInfo> deviceRooms =
                    viewModel.deviceRoomList.getValue().stream()
                            .filter(x -> x.name.equals(deviceRoom))
                            .collect(Collectors.toList());
            for (DeviceRoomInfo d : deviceRooms)
                if (d.availableDevices.get(title) > d.usedDevices.get(title)
                        || d.connectedDevices.contains(this))
                    availableDeviceRooms.add(d.index);

            Integer newIndex = 1;
            List<String> indices = viewModel.deviceRoomList.getValue().stream()
                    .filter(x -> x.name.equals(deviceRoom))
                    .map(x -> x.index)
                    .collect(Collectors.toList());
            while (indices.contains(newIndex.toString()))
                newIndex++;
            availableDeviceRooms.add(newIndex + " (Новая сеть)");
        }
    }

    public class DeviceRoomInfo {
        String name;
        String index;

        List<DeviceInfo> connectedDevices = new LinkedList<>();
        Map<String, Integer> availableDevices;
        Map<String, Integer> usedDevices;

        DeviceRoomInfo(String name, String index) {
            this.name = name;
            this.index = index;
            availableDevices = viewModel.getDeviceEntryByDeviceRoom(name);
            usedDevices =
                    availableDevices.keySet().stream().collect(Collectors.toMap(e -> e, e -> 0));
        }

        public void connectDevice(DeviceInfo deviceInfo) {
            connectedDevices.add(deviceInfo);
            int connectedDevices = usedDevices.get(deviceInfo.title);
            usedDevices.put(deviceInfo.title, connectedDevices + 1);
            deviceInfo.deviceRoomInfo = this;
        }

        public void disconnectDevice(DeviceInfo deviceInfo) {
            connectedDevices.remove(deviceInfo);
            int connectedDevices = usedDevices.get(deviceInfo.title);
            usedDevices.put(deviceInfo.title, connectedDevices - 1);
            deviceInfo.deviceRoomInfo = null;
        }
    }

    private void updateDeviceRoomList() {
        viewModel.deviceRoomList.setValue(deviceRoomList.stream()
                .filter(x -> x.usedDevices.entrySet().stream()
                        .anyMatch(y -> y.getValue() > 0))
                .collect(Collectors.toList()));
        deviceRoomList = viewModel.deviceRoomList.getValue();
    }

    private void checkRecycler() {
        List<DeviceInfo> deviceRecyclerList = viewModel.deviceRecyclerList.getValue();

        if (deviceRecyclerList == null) return;

        if (!deviceRecyclerList.isEmpty()
                && deviceRecyclerList
                .stream()
                .filter(x -> !x.isHeader)
                .noneMatch(
                        x -> x.deviceRoom == null
                                || x.cableLength == null
                                || x.deviceRoomInfo == null))
            forwardButton.setVisibility(View.VISIBLE);
        else
            forwardButton.setVisibility(View.INVISIBLE);
    }

    private String formatIntToString(int i) {
        return String.format(Locale.ENGLISH, "%d", i);
    }
}