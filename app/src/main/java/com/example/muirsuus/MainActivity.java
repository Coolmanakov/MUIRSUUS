package com.example.muirsuus;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.muirsuus.databinding.ActivityFirstBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final MutableLiveData<String> _name = new MutableLiveData<>();
    public static final LiveData<String> name = _name;
    private NavController navController;
    private ActivityFirstBinding binding;
    private ActionBarDrawerToggle toggle;
    private final Context context;

    public MainActivity() {
        this.context = this;
    }

    public Context getContext() {
        return context;
    }

    public static String getName() {
        return name.getValue();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first);
        Intent intent = getIntent();
        _name.setValue(intent.getStringExtra("name"));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.sectionFragment, R.id.calculation_nav_fragment, R.id.navigation_home, R.id.navigation_net, R.id.litFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.main_fragment_container);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); //использую свой тулбар
        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController);

        /*-------------toolbar--------------------*/
        setSupportActionBar(binding.toolbar);

        /*-------------Drawer Layout--------------------*/
        binding.navView.bringToFront();
        toggle = new ActionBarDrawerToggle(getParent(), binding.drawerLayout, binding.toolbar,
                R.string.open_drawer, R.string.close_drawer);
        //binding.drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        //binding.navView.setNavigationItemSelectedListener(this);
        /*-------------Drawer Layout--------------------*/

        setupPermissions();
    }
//----------------------Drawer Layout back pressed-----------------
    @Override
    public void onBackPressed() {

        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START) ){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    //*-----------------Navigation Drawer---------------------*\
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId()){
            case R.id.authors:
                navController.navigateUp();
                navController.navigate(R.id.action_navigation_home_to_authorsFragment);
                break;

            case R.id.nav_pointed:
                navController.navigateUp();
                Bundle arg = new Bundle();
                arg.putString("userName", name.getValue());
                navController.navigate(R.id.action_navigation_home_to_favouriteFragment, arg);
                break;

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }
    //*-----------------Navigation Drawer---------------------*\
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }
    /*


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/
    //метод, который при вызове меняет hamburger icon на arrow
    public void resetActionBar(boolean childAction, int drawerMode)
    {
        if (childAction) {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigateUp();
                }
            });

        } else {
            getSupportActionBar().setSubtitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //toggle.setDrawerIndicatorEnabled(true);
        }
        //binding.drawerLayout.setDrawerLockMode(drawerMode);
    }

    private void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_REQUEST_CODE);
    }


    /*@Override
    public void onBackStackChanged() {
        displayHomeUpOrHamburger();
    }*/
    //возвращение по стреклочк назад
/*@Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();

    }*/
    //-------------------------------------нужно для того, чтобы подгружать картнки с БД--------------------------------
   /* private void requestPermissionWrite() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
//--------------------------------------- нужно для того, чтобы подгружать картнки с БД--------------------------------

*/
}
