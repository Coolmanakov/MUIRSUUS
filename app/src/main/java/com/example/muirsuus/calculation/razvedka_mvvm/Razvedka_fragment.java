package com.example.muirsuus.calculation.razvedka_mvvm;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentReconnaissanceBinding;

import java.util.ArrayList;
import java.util.List;





public class Razvedka_fragment extends Fragment {

    List<Double> list_of_values = new ArrayList<>();
    private Button next;
    private Button back;
    private Button calculate;
    private EditText count_of_elements;
    private volatile int col = 0;//введенное число
    double[] values;
    private TableLayout tableLayout;
    private boolean mShrink;
    NavController navController;
    private FragmentReconnaissanceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reconnaissance, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RazvedkaViewModel razvedkaViewModel = new RazvedkaViewModel();

        binding.setRazvedData(razvedkaViewModel);
        binding.setLifecycleOwner(this);



    }
    /**
     * метод, который возвращает true если введено значение int
     * false если введено все что угодно, кроме int
     */
    /*private boolean isInt(String input){
        boolean isInt = false;
        for(int j = 0; j< input.length(); j++){

            if(input.charAt(j) > '0' && input.charAt(j) <= '9' ){
                isInt = true;
            }
            else isInt = false;
        }
        return isInt;}*/

}

