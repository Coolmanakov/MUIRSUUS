package com.example.muirsuus.calculation.SubscriberNetwork;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.muirsuus.classes.OnlyOnItemSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class SubscriberNetworkFragment3 extends Fragment {

    private SubscriberNetworkViewModel viewModel;
    private static final int LAYING_SPEED = 1;
    private static final int PERSONNEL_NUMBER = 2;
    private TextView estimatedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriber_network_3, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);

        estimatedTime = view.findViewById(R.id.estimated_time);
        EditText layingSpeed = view.findViewById(R.id.laying_speed);
        EditText personnelNumber = view.findViewById(R.id.personnel_number);
        Spinner reliefSpinner = view.findViewById(R.id.relief);
        Spinner terrainSpinner = view.findViewById(R.id.terrain);
        Spinner temperatureSpinner = view.findViewById(R.id.temperature);
        Spinner snowSpinner = view.findViewById(R.id.snow);
        Spinner windSpinner = view.findViewById(R.id.wind);

        layingSpeed.addTextChangedListener(new EditTextListener(LAYING_SPEED));
        personnelNumber.addTextChangedListener(new EditTextListener(PERSONNEL_NUMBER));

        reliefSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                reliefList,
                "Рельеф местности"
        ));
        terrainSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                terrainList,
                "Характер местности"
        ));
        temperatureSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                temperatureList,
                "Температура воздуха"
        ));
        snowSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                snowList,
                "Наличие снега"
        ));
        windSpinner.setAdapter(new CommonSpinnerAdapter(
                requireContext(),
                windList,
                "Скорость ветра"
        ));

        reliefSpinner.setOnItemSelectedListener(new OnlyOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.relief.setValue((String) reliefSpinner.getSelectedItem());
            }
        });
        terrainSpinner.setOnItemSelectedListener(new OnlyOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.terrain.setValue((String) terrainSpinner.getSelectedItem());
            }
        });
        temperatureSpinner.setOnItemSelectedListener(new OnlyOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.temperature.setValue((String) temperatureSpinner.getSelectedItem());
            }
        });
        snowSpinner.setOnItemSelectedListener(new OnlyOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.snow.setValue((String) snowSpinner.getSelectedItem());
            }
        });
        windSpinner.setOnItemSelectedListener(new OnlyOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.wind.setValue((String) windSpinner.getSelectedItem());
            }
        });

        viewModel.layingSpeed.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
        viewModel.personnelNumber.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
        viewModel.relief.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
        viewModel.terrain.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
        viewModel.temperature.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
        viewModel.snow.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
        viewModel.wind.observe(
                getViewLifecycleOwner(),
                x -> estimateTime()
        );
    }

    private class EditTextListener implements TextWatcher {
        int mode;

        public EditTextListener(int mode) {
            this.mode = mode;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String str = charSequence.toString();
            switch (mode) {
                case LAYING_SPEED:
                    viewModel.layingSpeed.setValue(Double.parseDouble(str));
                    break;
                case PERSONNEL_NUMBER:
                    viewModel.personnelNumber.setValue(Integer.parseInt(str));
                    break;
            }
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

    private void estimateTime() {
        if (viewModel.layingSpeed.getValue() == null
                || viewModel.personnelNumber.getValue() == null
                || viewModel.relief.getValue() == null
                || viewModel.terrain.getValue() == null
                || viewModel.temperature.getValue() == null
                || viewModel.snow.getValue() == null
                || viewModel.wind.getValue() == null)
            return;

        Double layingSpeed = viewModel.layingSpeed.getValue();
        int personnelNumber = viewModel.personnelNumber.getValue();
        int relief = reliefList.indexOf(viewModel.relief.getValue());
        int terrain = terrainList.indexOf(viewModel.terrain.getValue());
        int temperature = temperatureList.indexOf(viewModel.temperature.getValue());
        int snow = snowList.indexOf(viewModel.snow.getValue());
        int wind = windList.indexOf(viewModel.wind.getValue());
        double k1 = 0;

        if (temperature == 3 || snow == 3)
            switch (terrain) {
                case 0:
                    k1 += 0.25;
                    break;
                case 1:
                case 2:
                    k1 += 0.3;
                    break;
                case 3:
                    k1 += 0.5;
                    break;
            }
        else if (temperature == 2 || snow == 2)
            switch (terrain) {
                case 0:
                    k1 += 0.2;
                    break;
                case 1:
                case 2:
                    k1 += 0.25;
                    break;
                case 3:
                    k1 += 0.35;
                    break;
            }
        else if (temperature == 1)
            switch (terrain) {
                case 1:
                    k1 += 0.1;
                    break;
                case 2:
                    k1 += 0.2;
                    break;
                case 3:
                    k1 += 0.3;
                    break;
            }
        else if (temperature == 0)
            switch (terrain) {
                case 0:
                    k1 += 0.1;
                    break;
                case 1:
                    k1 += 0.2;
                    break;
                case 2:
                    k1 += 0.3;
                    break;
                case 3:
                    k1 += 0.35;
                    break;
            }

        if (snow == 1)
            switch (terrain) {
                case 0:
                    k1 += 0.1;
                    break;
                case 1:
                case 2:
                    k1 += 0.2;
                    break;
                case 3:
                    k1 += 0.35;
                    break;
            }

        if (wind == 1)
            switch (terrain) {
                case 0:
                case 1:
                    k1 += 0.2;
                    break;
                case 2:
                case 3:
                    k1 += 0.25;
                    break;
            }
        else if (wind == 2)
            switch (terrain) {
                case 0:
                case 1:
                    k1 += 0.3;
                    break;
                case 2:
                case 3:
                    k1 += 0.35;
                    break;
            }

        double _k2 = 0;
        switch (relief) {
            case 0:
                _k2 = 0.05;
                break;
            case 1:
                _k2 = 0.1;
                break;
            case 2:
                _k2 = 0.15;
                break;
        }

        double k2 = _k2;

        double l = viewModel.deviceRecyclerList.getValue().stream()
                .filter(x -> !x.isHeader)
                .mapToDouble(x -> x.cableLength + x.cableLength * k2)
                .sum();

        double t1 = l
                / layingSpeed
                / Math.sqrt(personnelNumber)
                * (1 + k1);

        long t2 = viewModel.deviceRecyclerList.getValue().stream()
                .filter(x -> !x.isHeader)
                .count()
                / personnelNumber;

        estimatedTime.setText(String.format(Locale.ENGLISH, "%,.2f мин", t1 + t2));
    }

    private final static List<String> reliefList = new ArrayList<String>() {{
        add("Равнинная или среднепересеченная");
        add("Холмистая или сильнопересеченная");
        add("Горная или труднодоступная");
    }};

    private final static List<String> terrainList = new ArrayList<String>() {{
        add("Равнинная и среднепересеченная");
        add("Лесисто-болотистая");
        add("Пустынно-песчаная");
        add("Гористая");
    }};

    private final static List<String> temperatureList = new ArrayList<String>() {{
        add(">+35°С");
        add("-7 – +35°С");
        add("-20 – -7°С");
        add("<-20°С");
    }};

    private final static List<String> snowList = new ArrayList<String>() {{
        add("Нет");
        add("<30см");
        add("30 – 80см");
        add(">80см");
    }};

    private final static List<String> windList = new ArrayList<String>() {{
        add("Нет");
        add("10 – 20м/с");
        add(">20м/с");
    }};
}
