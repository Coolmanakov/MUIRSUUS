package com.example.muirsuus.calculation.razvertyvania_mvvm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentVremyaRazvertyvaniyaBinding;

import java.math.BigDecimal;


public class Vremya_Razvertyvaniya extends Fragment {
    private Spinner type_of_line;
    private Spinner type_of_chanel;
    private Spinner ground;
    private Spinner stations;
    AdapterView adapterView;
    private Button calculate;
    private EditText count_radio_stations;
    private EditText temperature;
    private EditText snow;
    private EditText wind;
    private EditText t_marsh;
    private  int count_of_transit_stations = 0;
    private int koef_razvertyvaniay = 0;
    private  int temperature_int = 0;
    private  int snow_int = 0;
    private  int wind_int = 0;
    private int t_marsh_int = 0;
    private BigDecimal time_result = new BigDecimal(0);

    private FragmentVremyaRazvertyvaniyaBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vremya_razvertyvaniya, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                int ground_type = binding.ground.getSelectedItemPosition();
                int type_line = binding.typeLines.getSelectedItemPosition();
                int type_chanel = binding.typeChanel.getSelectedItemPosition();
                int station_razvertyvania = binding.timeRazvertyvania.getSelectedItemPosition();
                BigDecimal koef = new BigDecimal(0);

                String count_of_transit_stations_string = binding.countRadioStations.getText().toString();
                String temperature_string = binding.temperature.getText().toString();
                String snow_string = binding.snow.getText().toString();
                String wind_string = binding.wind.getText().toString();
                String t_marsh_string = binding.timeMarsh.getText().toString();

                koef = get_PGW_koef(temperature_string,snow_string,wind_string,ground_type);
                Log.d("mLog","koef = " +koef);
                koef = koef.add(BigDecimal.valueOf(1));

                station_razvertyvania = get_time_razvetyvania(station_razvertyvania);
                Log.d("mLog","Время развертывания = " +station_razvertyvania);


                if( isInt(count_of_transit_stations_string) ){
                    count_of_transit_stations = Integer.parseInt(count_of_transit_stations_string);
                }

                type_line =  get_time_for_setting_connection(type_line);
                type_line = type_line * (1 + count_of_transit_stations);
                Log.d("mLog","Время настройки и регулировки каналов  = " +type_line);

                type_chanel = get_time_regulate_stations(type_chanel);
                type_chanel = type_chanel * (1 + count_of_transit_stations);
                Log.d("mLog","Время сдачи каналов связи на аппаратуру уплотнения = " +type_chanel);


                if( isInt(t_marsh_string) ){
                    t_marsh_int = Integer.parseInt(t_marsh_string);
                }
                Log.d("mLog","время марша радиорелейной станции = " +t_marsh_int);
                if(t_marsh_int != 0 && type_line != 0 && type_chanel != 0 && station_razvertyvania != 0 && !koef.toString().equals("0") ) {

                    time_result = BigDecimal.valueOf(t_marsh_int + type_line + type_chanel + station_razvertyvania);
                    time_result = time_result.multiply(koef);
                    Log.d("mLog", "time = " + time_result);

                    binding.resultText.setVisibility(View.VISIBLE);
                    binding.resultValue.setVisibility(View.VISIBLE);
                    binding.resultValue.setText(time_result.toString() + " мин.");
                } else Log.d("mLog", "time = " + 0);

            }
        });
    }







    private boolean isInt(String input){
        boolean isInt = false;
        for(int j = 0; j< input.length(); j++){

            isInt = input.charAt(j) > '0' && input.charAt(j) <= '9';
        }
        return isInt;
    }

    // коэффициент, учитывающий физико-географические и погодные условия
    public BigDecimal get_PGW_koef(String temperature_string, String snow_string, String wind_string, int ground_type ){
        BigDecimal koef_razvertyvaniay = new BigDecimal(0);


        if( isInt(temperature_string) ){
            temperature_int = Integer.parseInt(temperature_string);

            if(temperature_int >= -7 && temperature_int <= 35 ){ // температура от -7 до 35
                Log.d("mLog", "Vremya_Razvertyvania temperature from -7 to 35 ");

                koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(ground_type/10));
                Log.d("mLog" , "koef_razvertyvaniay + " + ground_type/10);
            }
            else if(temperature_int > 35){ // температура от выше 35
                Log.d("mLog", "Vremya_Razvertyvania temperature above 35 ");

                if(ground_type == 0){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.1));
                    Log.d("mLog" , " koef_razvertyvaniay + " + 0.1);
                }
                else if(ground_type == 1){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.2));
                    Log.d("mLog" , " koef_razvertyvaniay + " + 0.2);
                }
                else {
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.3));
                    Log.d("mLog" , " koef_razvertyvaniay + " + 0.3);
                }
            }

        }



        if( isInt(snow_string) ){
            snow_int = Integer.parseInt(snow_string);

            if(snow_int < 0){
                snow_int = 0;
            }
            if(snow_int != 0 && snow_int <= 30 ) {
                Log.d("mLog", "  snow lower than 30 cm");
                if (ground_type == 0) {
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.1));
                    Log.d("mLog", " koef_razvertyvaniay + " + 0.1);
                }
                else if(ground_type == 3){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.35));
                    Log.d("mLog", " koef_razvertyvaniay + " + 0.35);
                }
                else {
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.2));
                    Log.d("mLog", " koef_razvertyvaniay + " + 0.2);}
            }
            else if( (temperature_int >= -20 && temperature_int <= -8) ||  snow_int <= 80 ){ //температура от -20 до -8 или снег 31-80 см
                Log.d("mLog", " Vremya_Razvertyvania temperarure between -20 and -8 or snow high between 31 and 80 см");

                if(ground_type == 0){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.2));
                    Log.d("mLog", " koef_razvertyvaniay + " + 0.2);
                }
                else  if( ground_type == 3) {
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.35));
                    Log.d("mLog", " koef_razvertyvaniay + " + 0.35);
                }
                else {
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.25));
                    Log.d("mLog", " koef_razvertyvaniay + " + 0.25);
                }


            }
            if(snow_int > 80 || temperature_int < -20){
                Log.d("mLog", "Vremya_Razvertyvania temperature less -20 or snow high more 80");
                if(ground_type == 0){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.25));
                    Log.d("mLog", "koef_razvertyvaniay + " + 0.25);
                }
                else  if( ground_type == 3){
                    koef_razvertyvaniay= koef_razvertyvaniay.add(BigDecimal.valueOf(0.4));
                    Log.d("mLog", "koef_razvertyvaniay + " + 0.4);
                }
                else koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.3));
                Log.d("mLog", "koef_razvertyvaniay + " + 0.3);
            }
        }
        if( isInt(wind_string) ){
            wind_int = Integer.parseInt(wind_string);
            if(wind_int >= 10 && wind_int <= 20){
                Log.d("mLog","Vremya_Razvertyvania  Wind is between 10 and 20 м/с");
                if(ground_type == 0){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.2));
                    Log.d("mLog","koef_razvertyvaniay + " + 0.2);
                }
                else  if( ground_type == 3 || ground_type == 2){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.25));
                    Log.d("mLog","koef_razvertyvaniay + " + 0.25);
                }
                else {
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.3));
                    Log.d("mLog","koef_razvertyvaniay + " + 0.3);
                }

            }
            else if (wind_int >20){
                Log.d("mLog","Vremya_Razvertyvania Wind is more 20 м/с");
                if(ground_type == 0 || ground_type ==1 ){
                    koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.3));
                    Log.d("mLog","koef_razvertyvaniay + " + 0.3);
                }
                else koef_razvertyvaniay = koef_razvertyvaniay.add(BigDecimal.valueOf(0.35));
            }
            else Log.d("mLog", "Vremya_Razvertyvania unknown value of wind");
        }
        return koef_razvertyvaniay;
    }
    //время настройки и регулировки канала связи между промежуточными и оконечными радиорелейными станциями
    private  int get_time_for_setting_connection(int type_line){
            BigDecimal time;
            if(type_line == 0){
                time = BigDecimal.valueOf(6);
            }
            else  if(type_line == 1){
                time = BigDecimal.valueOf(3);

            }
            else time = BigDecimal.valueOf(4);

            return  time.intValue();
    }
    //время сдачи канала связи на аппаратуру уплотнения и их регулировка между промежуточными или оконечными радиорелейными станциями
    private  int get_time_regulate_stations(int type_line){
        BigDecimal time;
        if(type_line == 2){
            time = BigDecimal.valueOf(1.40);
        }
        else  time = BigDecimal.valueOf(2);

        return  time.intValue();
    }
    // метод возвращает время развертывания станции. в strings есть массив, в котором хранится время развертывания каждой станнции
    //время однозначно соответвует станции в массиве stations
    private int get_time_razvetyvania(int type_of_station){
        int time;
        String[] time_for_stations = getResources().getStringArray(R.array.time_for_stations);
        time = Integer.parseInt(time_for_stations[type_of_station]);

        return time;
    }

}
