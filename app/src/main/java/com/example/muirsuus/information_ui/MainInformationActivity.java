package com.example.muirsuus.information_ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ActivityMainInformationBinding;

public class MainInformationActivity extends AppCompatActivity {
    ActivityMainInformationBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_information);

        navController = Navigation.findNavController(this, R.id.information);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}