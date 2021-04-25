package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.muirsuus.R;
import com.example.muirsuus.adapters.CommonSpinnerAdapter;
import com.example.muirsuus.classes.OnlyOnItemSelectedListener;
import com.google.android.material.snackbar.Snackbar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

public class SubscriberNetworkFragment3 extends Fragment {

    private SubscriberNetworkViewModel viewModel;
    private static final int LAYING_SPEED = 1;
    private static final int PERSONNEL_NUMBER = 2;
    private TextView estimatedTime;
    private Button endButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriber_network_3, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SubscriberNetworkViewModel.class);

        estimatedTime = view.findViewById(R.id.estimated_time);
        endButton = view.findViewById(R.id.end_button_3);
        EditText layingSpeed = view.findViewById(R.id.laying_speed);
        EditText personnelNumber = view.findViewById(R.id.personnel_number);
        Spinner reliefSpinner = view.findViewById(R.id.relief);
        Spinner terrainSpinner = view.findViewById(R.id.terrain);
        Spinner temperatureSpinner = view.findViewById(R.id.temperature);
        Spinner snowSpinner = view.findViewById(R.id.snow);
        Spinner windSpinner = view.findViewById(R.id.wind);

        layingSpeed.addTextChangedListener(new EditTextListener(LAYING_SPEED));
        personnelNumber.addTextChangedListener(new EditTextListener(PERSONNEL_NUMBER));

        personnelNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                personnelNumber.clearFocus();
            }
            return false;
        });

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

        if (viewModel.layingSpeed.getValue() != null)
            layingSpeed.setText(formatDoubleToString(viewModel.layingSpeed.getValue()));

        if (viewModel.personnelNumber.getValue() != null)
            personnelNumber.setText(formatIntToString(viewModel.personnelNumber.getValue()));

        if (viewModel.relief.getValue() != null)
            reliefSpinner.setSelection(reliefList.indexOf(viewModel.relief.getValue()) + 1);

        if (viewModel.terrain.getValue() != null)
            terrainSpinner.setSelection(terrainList.indexOf(viewModel.terrain.getValue()) + 1);

        if (viewModel.temperature.getValue() != null)
            temperatureSpinner.setSelection(temperatureList.indexOf(viewModel.temperature.getValue()) + 1);

        if (viewModel.snow.getValue() != null)
            snowSpinner.setSelection(snowList.indexOf(viewModel.snow.getValue()) + 1);

        if (viewModel.wind.getValue() != null)
            windSpinner.setSelection(windList.indexOf(viewModel.wind.getValue()) + 1);

        view.findViewById(R.id.save_button_3).setOnClickListener(v -> {
            layingSpeed.clearFocus();
            personnelNumber.clearFocus();

            View saveReportView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_save_report, null);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

            dialogBuilder.setView(saveReportView);

            dialogBuilder
                    .setCancelable(false)
                    .setTitle("Укажите название файла")
                    .setPositiveButton("Сохранить", (dialog, id) -> {

                        ActivityCompat.requestPermissions(
                                requireActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PackageManager.PERMISSION_GRANTED);

                        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

                        HSSFSheet deviceListSheet = hssfWorkbook.createSheet();
                        hssfWorkbook.setSheetName(hssfWorkbook.getSheetIndex(deviceListSheet), "Список устройств");

                        deviceListSheet.setColumnWidth(0, 8000);
                        deviceListSheet.setColumnWidth(1, 8000);
                        deviceListSheet.setColumnWidth(2, 4000);
                        deviceListSheet.setColumnWidth(3, 4000);
                        deviceListSheet.setColumnWidth(4, 4000);
                        deviceListSheet.setColumnWidth(5, 4000);

                        HSSFRow titleRow = deviceListSheet.createRow(0);
                        titleRow.createCell(0).setCellValue("Должностное лицо");
                        titleRow.createCell(1).setCellValue("Подразделение");
                        titleRow.createCell(2).setCellValue("Устройство");
                        titleRow.createCell(3).setCellValue("Аппаратная");
                        titleRow.createCell(4).setCellValue("№ Аппаратной");
                        titleRow.createCell(5).setCellValue("Длина кабеля");

                        int i = 1;
                        for (SubscriberNetworkFragment2.DeviceRoomInfo deviceRoom : viewModel.deviceRoomList.getValue()) {
                            for (SubscriberNetworkFragment2.DeviceInfo device : deviceRoom.connectedDevices) {
                                String official = device.official;

                                HSSFRow deviceRow = deviceListSheet.createRow(i++);
                                deviceRow.createCell(0).setCellValue(official);
                                deviceRow.createCell(1).setCellValue(viewModel.getDivisionByOfficial(official));
                                deviceRow.createCell(2).setCellValue(device.title);
                                deviceRow.createCell(3).setCellValue(deviceRoom.name);
                                deviceRow.createCell(4).setCellValue(Integer.parseInt(deviceRoom.index));
                                deviceRow.createCell(5).setCellValue(device.cableLength);
                            }
                        }

                        HSSFSheet calculationSheet = hssfWorkbook.createSheet();
                        hssfWorkbook.setSheetName(hssfWorkbook.getSheetIndex(calculationSheet), "Результаты вычислений");

                        calculationSheet.setColumnWidth(0, 8000);
                        calculationSheet.setColumnWidth(1, 12000);

                        HSSFRow layingSpeedRow = calculationSheet.createRow(0);
                        layingSpeedRow.createCell(0).setCellValue("Скорость прокладки, м/мин");
                        layingSpeedRow.createCell(1).setCellValue(layingSpeed.getText().toString());

                        HSSFRow personnelNumberRow = calculationSheet.createRow(1);
                        personnelNumberRow.createCell(0).setCellValue("Количество личного состава");
                        personnelNumberRow.createCell(1).setCellValue(personnelNumber.getText().toString());

                        HSSFRow reliefRow = calculationSheet.createRow(2);
                        reliefRow.createCell(0).setCellValue("Рельеф местности");
                        reliefRow.createCell(1).setCellValue(viewModel.relief.getValue());

                        HSSFRow terrainRow = calculationSheet.createRow(3);
                        terrainRow.createCell(0).setCellValue("Характер местности");
                        terrainRow.createCell(1).setCellValue(viewModel.terrain.getValue());

                        HSSFRow temperatureRow = calculationSheet.createRow(4);
                        temperatureRow.createCell(0).setCellValue("Температура воздуха");
                        temperatureRow.createCell(1).setCellValue(viewModel.temperature.getValue());

                        HSSFRow snowRow = calculationSheet.createRow(5);
                        snowRow.createCell(0).setCellValue("Наличие снега");
                        snowRow.createCell(1).setCellValue(viewModel.snow.getValue());

                        HSSFRow windRow = calculationSheet.createRow(6);
                        windRow.createCell(0).setCellValue("Скорость ветра");
                        windRow.createCell(1).setCellValue(viewModel.wind.getValue());

                        String fileName = ((EditText) saveReportView
                                .findViewById(R.id.file_name))
                                .getText()
                                .toString();

                        if (fileName.equals("")) fileName = "MUIRSUUS_" + UUID.randomUUID().toString();

                        File filePath;

                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                            filePath = new File(Environment.getExternalStorageDirectory() + "/" + fileName + ".xls");
                        } else {
                            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + fileName + ".xls");
                        }

                        try {
                            if (!filePath.exists()) filePath.createNewFile();
                            FileOutputStream outputStream = new FileOutputStream(filePath);
                            hssfWorkbook.write(outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Snackbar.make(view, "Файл сохранен в " + filePath.getParent(), Snackbar.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Snackbar.make(view, e.toString(), Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Отмена", (dialog, id) -> dialog.dismiss());

            dialogBuilder.create().show();
        });

        view.findViewById(R.id.backward_button_3).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.subscriber_network_3_backward));

        endButton.setOnClickListener(v -> {
            viewModel.clear();
            Navigation.findNavController(view).navigate(R.id.subscriber_network_3_end);
        });
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
                    Double layingSpeed;
                    try {
                        layingSpeed = Double.parseDouble(str);
                    } catch (NumberFormatException e) {
                        layingSpeed = null;
                    }
                    viewModel.layingSpeed.setValue(layingSpeed);
                    break;
                case PERSONNEL_NUMBER:
                    Integer personnelNumber;
                    try {
                        personnelNumber = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        personnelNumber = null;
                    }
                    viewModel.personnelNumber.setValue(personnelNumber);
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
        endButton.setVisibility(View.VISIBLE);
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

    private String formatIntToString(int i) {
        return String.format(Locale.ENGLISH, "%d", i);
    }

    private String formatDoubleToString(double i) {
        return String.format(Locale.ENGLISH, "%.2f", i);
    }
}
