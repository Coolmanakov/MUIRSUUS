package com.example.muirsuus.main_navigation.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    final float start_value = 0.7f; //начальное значение размера шрифта
    final float step = 0.15f; //шаг увеличения коэффициента
    public static final String ONLINE_WEB_PAGES = "loadOnlinePage";
    private static final String LOG_TAG = "mLog " + SettingsFragment.class.getCanonicalName();
    public final String WEB_SIZE = "webSizeValue";
    private final float webSizeFloat = 50;
    int size_coef; //выбранный коэффициент
    private SharedPreferences webSizePrefernces;
    private SharedPreferences onlinePagePreferences;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        //set Home Up Btn and block the drawerLayout
        ((MainActivity) getActivity()).resetActionBar(true,
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getActivity().setTitle("Настройки");

        final SharedPreferences settings = this.getActivity().getSharedPreferences("MyAppSett", Context.MODE_PRIVATE);


        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        binding.seekBar.setProgress(Math.round((config.fontScale - start_value) / step));


        if (binding.seekBar != null) {

            binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    binding.textSize.setTextSize(сoef * 14);//динамическое изменение текста, коэффициент подобран подбором

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        return binding.getRoot();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*-----------------------------------------------------------------------------------------------*/
        //Сохранение настроек размера веб-страницы
        webSizePrefernces = getActivity().getSharedPreferences(WEB_SIZE, Context.MODE_PRIVATE);
        webSizePrefernces.edit();
        //устанавливаем сохраненноё значение на seekBar
        if (webSizePrefernces.contains(WEB_SIZE)) {
            binding.seekBarWeb.setProgress(Math.round(webSizePrefernces.getFloat(WEB_SIZE, 50) / 50));
            Log.d(LOG_TAG, "WEB_SIZE " + webSizePrefernces.getFloat(WEB_SIZE, 50) / 50);
        }


        binding.seekBarWeb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //при прокрутке seekBar меняем размеры веб-страницы
                SharedPreferences.Editor editor = webSizePrefernces.edit();
                editor.putFloat(WEB_SIZE, webSizeFloat * progress);
                Log.d(LOG_TAG, "WEB_SIZE new " + webSizeFloat * progress);
                editor.apply();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /*-----------------------------------------------------------------------------------------------*/
        //Обработка чек-бокса на загрузку веб-страниц(он/оф лайн)
        //получаем сохраненное значение для загрузки веб-страниц
        onlinePagePreferences = getActivity().getSharedPreferences(ONLINE_WEB_PAGES, Context.MODE_PRIVATE);
        if (onlinePagePreferences.contains(ONLINE_WEB_PAGES)) {
            binding.onlineCheckBox.setChecked(onlinePagePreferences.getBoolean(ONLINE_WEB_PAGES, false));
            Log.d(LOG_TAG, "Selected " + onlinePagePreferences.getBoolean(ONLINE_WEB_PAGES, false));
        }
        binding.onlineCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = onlinePagePreferences.edit();
                editor.putBoolean(ONLINE_WEB_PAGES, isChecked);
                Log.d(LOG_TAG, "isChecked " + isChecked);
                editor.apply();
            }
        });

    }
}
