package com.example.muirsuus.ui.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.muirsuus.DataBaseHelper;
import com.example.muirsuus.FirstActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.ui.lit.LitViewModel;

import java.io.IOException;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;
import static com.example.muirsuus.R.id.nav_host_fragment;

public class SettingsFragment extends Fragment {
    final float start_value = 0.7f; //начальное значение размера шрифта
    final float step = 0.15f; //шаг увеличения коэффициента
    int size_coef ; //выбранный коэффициент
    private Switch mySwitch;
    public Button update_db;





    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_settings,container,false);

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        /*final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);






        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            // create ContextThemeWrapper from the original Activity Context with the custom theme
            contextThemeWrapper.setTheme(R.style.NightTheme);
        }
        else contextThemeWrapper.setTheme(R.style.AppTheme);

        mySwitch = (Switch) root.findViewById(R.id.mySwitch);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }

        });


        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);*/


        final SharedPreferences settings = this.getActivity().getSharedPreferences("MyAppSett", Context.MODE_PRIVATE);


        SeekBar seekBar = (SeekBar) root.findViewById(R.id.seekBar); // находим элемент seekBar
        final TextView text_size = (TextView) root.findViewById(R.id.text_size);


        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        seekBar.setProgress(Math.round((config.fontScale - start_value) / step));



        if (seekBar != null) {

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    size_coef = progress; //выбранный коэффициент


                    //активируем параметры
                    SharedPreferences.Editor value_add = settings.edit();
                    //заносим новый коэффициент увеличения шрифта
                    value_add.putInt("size_coef", size_coef);
                    value_add.apply();

                    //устанавливаем размер шрифта в приложении
                    Resources res = getResources();
                    float  сoef = start_value + size_coef * step; //новый коэффициент увеличения шрифта
                    Configuration configuration = new Configuration(res.getConfiguration());
                    configuration.fontScale = сoef;
                    res.updateConfiguration(configuration, res.getDisplayMetrics());
                    text_size.setTextSize(сoef*14);//динамическое изменение текста, коэффициент подобран подбором

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
            });
        }
        // inflate the layout using the cloned inflater, not default inflater
        //return localInflater.inflate(R.layout.fragment_settings, (ViewGroup) root, false);// не ясно верен ли второй параметр
        return  root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        update_db = view.findViewById(R.id.update_db);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());


        update_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dataBaseHelper.updateDataBase();
                    Toast.makeText(getContext(),"База данных успешно обновлена",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"База данных не может быть обновлена",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
