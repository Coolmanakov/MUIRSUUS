package com.example.muirsuus;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.example.muirsuus.classes.MyObject;
import com.example.muirsuus.ui.layouts.DescriptionFragment;
import com.example.muirsuus.ui.layouts.HistoryFragment;
import com.example.muirsuus.ui.layouts.TthFragment;

public class SecondLevelOfNesting extends AppCompatActivity {
    Button btn_tth;
    Button btn_history;
    Button btn_description;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_khm);

        MyObject myObj = (MyObject) getIntent().getParcelableExtra(MyObject.class.getCanonicalName());

        btn_tth = (Button) findViewById(R.id.btn_tth);
        btn_description = (Button) findViewById(R.id.btn_description);
        btn_history = (Button) findViewById(R.id.btn_history);

       //final NavController navController = Navigation.findNavController(this, R.id.fragment_managed_by_buttons);


        btn_tth.setBackgroundResource(R.drawable.button_pressed);
        btn_description.setBackgroundResource(R.drawable.button_pressed);
        btn_history.setBackgroundResource(R.drawable.button_pressed);

        View.OnClickListener listener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                if(v == btn_tth){
                    btn_tth.setBackgroundResource(R.drawable.button_pressed);
                    fragment = new TthFragment();
                    //navController.navigate(R.id.historyFragment);

                }
                else
                    if(v == btn_description){
                        btn_description.setBackgroundResource(R.drawable.button_pressed);
                        fragment = new DescriptionFragment();
                        //navController.navigate(R.id.descriptionFragment);

                    }
                    else {
                        btn_history.setBackgroundResource(R.drawable.button_pressed);
                        //navController.navigate(R.id.historyFragment);
                        fragment = new HistoryFragment();

                    }
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_managed_by_buttons, fragment);
                    transaction.commit();
            }
        };
        btn_description.setOnClickListener(listener);
        btn_tth.setOnClickListener(listener);
        btn_history.setOnClickListener(listener);


    }
}

