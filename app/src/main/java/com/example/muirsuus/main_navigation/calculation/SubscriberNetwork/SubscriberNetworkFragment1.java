package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;
import com.example.muirsuus.adapters.CheckableSpinnerAdapter;
import com.example.muirsuus.classes.LiveDataList;
import com.example.muirsuus.classes.LiveDataMap;
import com.example.muirsuus.classes.OnlyOnItemSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriberNetworkFragment1 extends Fragment {
    SubscriberNetworkViewModel viewModel;
    Spinner divisionTypeSpinner;
    Spinner officialsTypeSpinner;
    View forwardButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriber_network_1, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);
        viewModel.initDatabase(getContext());

        forwardButton = view.findViewById(R.id.forward_button_1);

        // Setting Division Spinner
        divisionTypeSpinner = view.findViewById(R.id.division_type_spinner);
        divisionTypeSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                viewModel.getDivisionList(),
                "Категория командного пункта"));
        divisionTypeSpinner.setOnItemSelectedListener(new OnlyOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.setDivision((String) adapterView.getSelectedItem());
            }
        });

        // Setting Official Spinner
        officialsTypeSpinner = view.findViewById(R.id.official_type_spinner);
        viewModel.divisionType.observe(
                getViewLifecycleOwner(),
                divisionType -> {
                    if (divisionType != null) {
                        officialsTypeSpinner.setVisibility(View.VISIBLE);
                        officialsTypeSpinner.setAdapter(new CheckableSpinnerAdapter(
                                requireContext(),
                                viewModel.getOfficialListByDivision(),
                                viewModel.officialType,
                                "Категории должностных лиц"));
                    } else
                        officialsTypeSpinner.setVisibility(View.INVISIBLE);
                });

        // Setting Official -> Devices Recycler
        RecyclerView officialRecyclerView = view.findViewById(R.id.official_recycler_view);
        officialRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false));
        officialRecyclerView.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL));
        OfficialRecyclerAdapter officialRecyclerAdapter =
                new OfficialRecyclerAdapter(requireContext(), getViewLifecycleOwner());
        officialRecyclerView.setAdapter(officialRecyclerAdapter);
        viewModel.officialType.observe(
                getViewLifecycleOwner(),
                officialType -> {
                    if (officialType.isEmpty())
                        officialRecyclerView.setVisibility(View.INVISIBLE);
                    else
                        officialRecyclerView.setVisibility(View.VISIBLE);

                    LiveDataList<OfficialInfo> officialRecyclerList = viewModel.officialRecyclerList;
                    List<String> itemInfoTitles = officialRecyclerList.getValue().stream()
                            .map(OfficialInfo::getTitle)
                            .collect(Collectors.toList());

                    for (int i = 0; i < officialRecyclerList.size(); i++) {
                        String title = officialRecyclerList.get(i).title;
                        if (!officialType.contains(title))
                            officialRecyclerList.remove(i);
                    }

                    for (String official : officialType)
                        if (!itemInfoTitles.contains(official))
                            officialRecyclerList.add(new OfficialInfo(official));

                    officialRecyclerAdapter.notifyDataSetChanged();
                });
        viewModel.officialRecyclerList.observe(
                getViewLifecycleOwner(),
                list -> checkRecycler()
        );

        forwardButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.subscriber_network_1_forward));
        view.findViewById(R.id.backward_button_1).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.subscriber_network_1_backward));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (viewModel.divisionType.getValue() != null)
            divisionTypeSpinner.setSelection(
                    viewModel.getDivisionList().indexOf(viewModel.divisionType.getValue()) + 1);
    }

    private class OfficialRecyclerAdapter extends RecyclerView.Adapter<OfficialRecyclerAdapter.OfficialItemViewHolder> {
        Context context;
        LifecycleOwner lifecycleOwner;

        public OfficialRecyclerAdapter(Context context, LifecycleOwner lifecycleOwner) {
            this.context = context;
            this.lifecycleOwner = lifecycleOwner;
        }

        @NonNull
        @Override
        public OfficialItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OfficialItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.official_recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull OfficialItemViewHolder holder, int position) {
            OfficialInfo officialInfo = viewModel.officialRecyclerList.get(position);

            holder.officialName.setText(officialInfo.title);
            holder.setDeviceAdapter(context, position);
        }

        @Override
        public int getItemCount() {
            return viewModel.officialRecyclerList.size();
        }

        private class OfficialItemViewHolder extends RecyclerView.ViewHolder {

            final TextView officialName;
            final Spinner deviceSpinner;

            OfficialItemViewHolder(@NonNull View itemView) {
                super(itemView);

                officialName = itemView.findViewById(R.id.official_name);
                deviceSpinner = itemView.findViewById(R.id.device_spinner);
            }

            public void setDeviceAdapter(Context context, int position) {
                OfficialInfo officialInfo = viewModel.officialRecyclerList.get(position);

                deviceSpinner.setAdapter(new CheckableSpinnerAdapter(
                        context,
                        viewModel.getDeviceList(),
                        officialInfo.deviceList,
                        "Устройства"));
                officialInfo.deviceList.observe(
                        lifecycleOwner,
                        deviceList -> {
                            if (deviceList != null) {
                                LiveDataMap<String, List<String>> deviceMap = viewModel.deviceMap;

                                if (deviceList.isEmpty())
                                    deviceMap.remove(officialInfo.title);
                                else
                                    deviceMap.put(officialInfo.title, deviceList);
                            }

                            checkRecycler();
                        }
                );
            }
        }
    }

    public class OfficialInfo {
        public String title;
        public LiveDataList<String> deviceList;

        OfficialInfo(String title) {
            this.title = title;

            List<String> list = viewModel.deviceMap.get(title);

            if (list != null)
                deviceList = new LiveDataList<>(list);
            else
                deviceList = new LiveDataList<>();
        }

        public String getTitle() {
            return title;
        }
    }

    private void checkRecycler() {
        List<OfficialInfo> officialRecyclerList = viewModel.officialRecyclerList.getValue();

        if (officialRecyclerList == null) return;

        if (!officialRecyclerList.isEmpty()
                && officialRecyclerList
                .stream()
                .noneMatch(x -> x.deviceList.isEmpty()))
            forwardButton.setVisibility(View.VISIBLE);
        else
            forwardButton.setVisibility(View.INVISIBLE);
    }
}
