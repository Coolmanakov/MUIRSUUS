package com.example.muirsuus.main_navigation.calculation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.muirsuus.R;
import com.example.muirsuus.main_navigation.calculation.SubscriberNetwork.SubscriberNetworkViewModel;

public class CalculationActivity extends AppCompatActivity {

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        navController = Navigation.findNavController(this, R.id.calc_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

        SubscriberNetworkViewModel subscriberNetworkViewModel = new ViewModelProvider(this).get(SubscriberNetworkViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}
