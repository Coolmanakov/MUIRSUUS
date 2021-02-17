package com.example.muirsuus;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final MutableLiveData<String> _name = new MutableLiveData<>();
    public static final LiveData<String> name = _name;
    private NavController navController;
    private ActivityFirstBinding binding;
    private ActionBarDrawerToggle toggle;

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
        toggle = new ActionBarDrawerToggle(getParent(),binding.drawerLayout,binding.toolbar,
                                                                    R.string.open_drawer,R.string.close_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        binding.navView.setCheckedItem(R.id.nav_main);
        /*-------------Drawer Layout--------------------*/

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
            case R.id.nav_main:
                navController.navigateUp();
                break;
            case R.id.nav_calculation:
                navController.navigateUp();
                navController.navigate(R.id.action_navigation_home_to_nav_host_fragment);
                break;
            case R.id.nav_literature:
                navController.navigateUp();
                navController.navigate(R.id.action_navigation_home_to_litFragment);
                break;
            case R.id.nav_directory:
                navController.navigateUp();
                navController.navigate(R.id.action_navigation_home_to_sectionFragment);
                break;
            case R.id.nav_net:
                navController.navigateUp();
                navController.navigate(R.id.action_navigation_home_to_navigation_net2);
                break;
            case R.id.nav_settings:
                navController.navigateUp();
                navController.navigate(R.id.action_navigation_home_to_settingsFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                break;
        }

        return true;
    }
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
            toggle.setDrawerIndicatorEnabled(true);
        }
        binding.drawerLayout.setDrawerLockMode(drawerMode);
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
