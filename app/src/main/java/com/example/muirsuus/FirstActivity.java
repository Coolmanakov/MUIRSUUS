package com.example.muirsuus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.muirsuus.ui.home.HomeFragment;
import com.example.muirsuus.ui.portfel.PortfelFragment;
import com.example.muirsuus.ui.settings.SettingsFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;
import static com.example.muirsuus.R.id.nav_host_fragment;

public class FirstActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private  Fragment fragment = null;
    private  int oldversion, newversion;
    private SQLiteDatabase db;
    SpaceNavigationView spaceNavigationView;
    Context context;

    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme);
        setTitle("Дом");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);




//---------------------------------------------------------------------------------------------------
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        newversion = dataBaseHelper.getVersion();
//---------------------------------------------------------------------------------------------------
        SharedPreferences myPreferences  = PreferenceManager.getDefaultSharedPreferences(FirstActivity.this);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor myEditor = myPreferences.edit();

        if(myPreferences.getInt("VERSION",0) != 0){ // проверка, что в preferences есть какая-то запись
            Log.d("mLog","FIRST ACTIVITY:   OLD VERSION     " + oldversion );
            oldversion = myPreferences.getInt("VERSION",0); // получаем номер старой версии
        }
        myEditor.putInt("VERSION",newversion); // меняем номер версии на новый
        myEditor.commit(); //сохраняем изменения
//---------------------------------------------------------------------------------------------------
        if(newversion > oldversion) { // если версия изменилась, значит перезапишем файл с БД
            try {
                Log.d("mLog","FIRST ACTIVITY:   NEW VERSION OF DATABASE" );
                dataBaseHelper.copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d("mLog", "FIRST ACTIVITY:   NO CHANGES IN DATABASE");
        }


//---------------------------------------------------------------------------------------------------
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!checkPermission()) {
                    requestPermissionWrite();
                }
            }
        }
//---------------------------------------------------------------------------------------------------------------------------------------------
        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_portfel,  R.id.navigation_lit, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/


        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_portfel));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_settings));



        context = this;
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                fragment = new HomeFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // анимация для фрагментов
                //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE); анимация, переходы между фрагментами
                transaction.replace(nav_host_fragment, fragment);
                transaction.commit();
                setTitle("Дом");
                Log.d("mLog", "FIRST ACTIVITY:   CLICK ON CENTRE BUTTON:    ДОМ");
                spaceNavigationView.setActiveSpaceItemColor(ContextCompat.getColor(context, R.color.colorGray));
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                changeFragment(itemIndex);

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                changeFragment(itemIndex);
            }
        });


    }
//-------------------------------------нужно для того, чтобы подгружать картнки с БД--------------------------------
    private void requestPermissionWrite(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(FirstActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(FirstActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
//--------------------------------------- нужно для того, чтобы подгружать картнки с БД--------------------------------

    public void changeFragment(int itemIndex){

        if (itemIndex == 0){
            setTitle("Портфель");
            fragment = new PortfelFragment();
            Log.d("mLog", "FIRST ACTIVITY:   CLICK ON LEFT BUTTON:    ПОРТФЕЛЬ");
        }
        else {
            fragment = new SettingsFragment();
            setTitle("Настройки");
            Log.d("mLog", "FIRST ACTIVITY:   CLICK ON RIGHT BUTTON:    НАСТРОЙКИ");

        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        // анимация для фрагментов
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE); //анимация, переходы между фрагментами
        transaction.replace(nav_host_fragment, fragment);
        transaction.commit();

    }



}
