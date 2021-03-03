package com.example.muirsuus.calculation.SubscriberNetwork;

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
import com.example.muirsuus.classes.LiveDataList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubscriberNetworkFragment2 extends Fragment {
    SubscriberNetworkViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriber_network_2, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);

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
                list -> deviceRecyclerAdapter.notifyDataSetChanged()
        );

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

        view.findViewById(R.id.transition_button_2).setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(R.id.subscriber_network_2_to_3);
                }
        );
    }

    private class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceItemViewHolder> {
        Context context;
        LifecycleOwner lifecycleOwner;

        public DeviceRecyclerAdapter(Context context, LifecycleOwner lifecycleOwner) {
            this.context = context;
            this.lifecycleOwner = lifecycleOwner;
        }

        @NonNull
        @Override
        public DeviceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DeviceItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_recycler_item, parent, false)) {{
                setIsRecyclable(false);
            }};
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceItemViewHolder holder, int position) {
            DeviceInfo deviceInfo = viewModel.deviceRecyclerList.get(position);
            String title = deviceInfo.title;
            holder.deviceName.setText(title);

            if (deviceInfo.isHeader) {
                holder.layout.setBackgroundResource(R.color.highlight);

                holder.cableLength.setVisibility(View.GONE);
                holder.deviceRoomSpinner.setVisibility(View.GONE);
                holder.deviceRoomIndex.setVisibility(View.GONE);
            } else {
                holder.editTextListener.setEditText(position);

                if (deviceInfo.cableLength != null && deviceInfo.cableLength != 0)
                    holder.cableLength.setText(formatIntToString(deviceInfo.cableLength));

                List<String> deviceRooms =
                        SubscriberNetworkRepository.deviceRoomMap.entrySet().stream()
                                .filter(x -> x.getValue().containsKey(title))
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList());

                holder.deviceRoomSpinner.setAdapter(new CommonSpinnerAdapter(
                        context,
                        deviceRooms,
                        "Аппаратная"
                ));

                holder.deviceRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int var3, long var4) {
                        if (adapterView.getSelectedItem() == null) return;

                        deviceInfo.deviceRoom = adapterView.getSelectedItem().toString();

                        List<String> deviceRoomIndices = createIndicesList(deviceInfo);

                        CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(
                                context,
                                deviceRoomIndices,
                                "Номер аппаратной");
                        holder.deviceRoomIndex.setAdapter(adapter);

                        holder.deviceRoomIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int var3, long var4) {
                                if (holder.deviceRoomIndex.getSelectedItem() == null) return;

                                String[] selectedIndex = holder.deviceRoomIndex.getSelectedItem()
                                        .toString()
                                        .split(" ");

                                if (deviceInfo.deviceRoomInfo != null)
                                    deviceInfo.deviceRoomInfo.disconnectDevice(deviceInfo);

                                if (selectedIndex.length != 1) {
                                    int newIndex = findNewIndex(deviceInfo.deviceRoom);

                                    deviceInfo.deviceRoomInfo = new DeviceRoomInfo(
                                            deviceInfo.deviceRoom,
                                            selectedIndex[0]);
                                    viewModel.deviceRoomList.add(
                                            newIndex - 1,
                                            deviceInfo.deviceRoomInfo);

                                    deviceRoomIndices.clear();
                                    deviceRoomIndices.addAll(createIndicesList(deviceInfo));
                                    adapter.notifyDataSetChanged();

                                    holder.deviceRoomIndex.setSelection(
                                            deviceRoomIndices.indexOf(selectedIndex[0]) + 1);
                                } else {
                                    deviceInfo.deviceRoomInfo = viewModel.deviceRoomList.getValue().stream()
                                            .filter(x ->
                                                    x.name.equals(deviceInfo.deviceRoom)
                                                            && x.index.equals(selectedIndex[0]))
                                            .findFirst()
                                            .orElse(null);
                                }

                                if (deviceInfo.deviceRoomInfo != null)
                                    deviceInfo.deviceRoomInfo.connectDevice(deviceInfo);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                //DO NOTHING
                            }
                        });

                        viewModel.deviceRoomList.observe(
                                getViewLifecycleOwner(),
                                deviceRoomList -> {
                                    int selectedPosition = holder.deviceRoomIndex.getSelectedItemPosition();
                                    String selectedIndex = (String) holder.deviceRoomIndex.getSelectedItem();
                                    if (selectedIndex == null) return;
                                    deviceRoomIndices.clear();
                                    deviceRoomIndices.addAll(createIndicesList(deviceInfo));
                                    adapter.notifyDataSetChanged();
                                    if (selectedPosition != 0)
                                        selectedPosition = deviceRoomIndices.indexOf(selectedIndex);
                                    holder.deviceRoomIndex.setSelection(selectedPosition + 1);
                                });

                        if (deviceInfo.deviceRoomInfo != null)
                            holder.deviceRoomIndex.setSelection(
                                    deviceRoomIndices.indexOf(deviceInfo.deviceRoomInfo.index) + 1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //DO NOTHING
                    }
                });

                if (deviceInfo.deviceRoom != null)
                    holder.deviceRoomSpinner.setSelection(
                            deviceRooms.indexOf(deviceInfo.deviceRoom) + 1);
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
        DeviceRoomInfo deviceRoomInfo;

        DeviceInfo(String title, boolean isHeader) {
            this.title = title;
            this.isHeader = isHeader;
        }
    }

    public class DeviceRoomInfo {
        String name;
        String index;
        Map<String, Integer> availableDevices;
        Map<String, Integer> usedDevices;

        DeviceRoomInfo(String name, String index) {
            this.name = name;
            this.index = index;
            availableDevices = SubscriberNetworkRepository.deviceRoomMap.get(name);
            usedDevices = availableDevices.keySet().stream().collect(Collectors.toMap(e -> e, e -> 0));
        }

        public void connectDevice(DeviceInfo deviceInfo) {
            int connectedDevices = usedDevices.get(deviceInfo.title);
            usedDevices.put(deviceInfo.title, connectedDevices + 1);
            if (usedDevices.entrySet().stream()
                    .anyMatch(x -> x.getValue() >= availableDevices.get(x.getKey())))
                viewModel.deviceRoomList.setValue(viewModel.deviceRoomList.getValue());
            deviceInfo.deviceRoomInfo = this;
        }

        public void disconnectDevice(DeviceInfo deviceInfo) {
            int connectedDevices = usedDevices.get(deviceInfo.title);
            usedDevices.put(deviceInfo.title, Integer.max(0, connectedDevices - 1));
            if (deviceInfo.deviceRoomInfo.usedDevices.entrySet().stream()
                    .allMatch(x -> x.getValue() <= 0))
                viewModel.deviceRoomList.remove(deviceInfo.deviceRoomInfo);
            deviceInfo.deviceRoomInfo = null;
        }
    }

    private String formatIntToString(int i) {
        return String.format(Locale.ENGLISH, "%d", i);
    }

    private int findNewIndex(String deviceRoom) {
        int newIndex = 1;

        List<String> indices = viewModel.deviceRoomList.getValue().stream()
                .filter(x -> x.name.equals(deviceRoom))
                .map(x -> x.index)
                .collect(Collectors.toList());

        for (String index : indices) {
            if (newIndex < Integer.parseInt(index))
                return newIndex;
            else
                newIndex++;
        }

        return newIndex;
    }

    private List<DeviceRoomInfo> getSuitableDeviceRooms(DeviceInfo deviceInfo) {
        return viewModel.deviceRoomList.getValue().stream()
                .filter(x -> {
                    Integer a = x.availableDevices.get(deviceInfo.title);
                    return a != null
                            && (a > x.usedDevices.get(deviceInfo.title)
                            || x.equals(deviceInfo.deviceRoomInfo));
                })
                .collect(Collectors.toList());
    }

    private List<String> createIndicesList(DeviceInfo deviceInfo) {
        List<String> indices = getSuitableDeviceRooms(deviceInfo).stream()
                .map(x -> x.index)
                .sorted(Comparator.comparingInt(Integer::parseInt))
                .collect(Collectors.toList());

        int newIndex = 1;
        for (String index : viewModel.deviceRoomList.getValue().stream()
                .filter(x -> x.availableDevices.containsKey(deviceInfo.title))
                .map(x -> x.index)
                .collect(Collectors.toList()))
            if (newIndex < Integer.parseInt(index))
                break;
            else
                newIndex++;

            int i = 0;
            while (i < indices.size()) {
                if (Integer.parseInt(indices.get(i++)) < newIndex) break;
            }
        indices.add(i, newIndex + " (Новая сеть)");

        return indices;
    }
}