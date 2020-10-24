package com.example.muirsuus.calculation.razvertyvania_mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import java.util.List;

public class RazvertyvaniaViewModel extends BaseObservable {
    public ObservableField<Integer> quantity = new ObservableField<>();
    public ObservableField<List<Integer>> quantityEntries = new ObservableField<>();



    /*@BindingAdapter(value = "onItemSelected")
    public  void setItemSelectedListener(AdapterView.OnItemSelectedListener itemSelectedListener){
        Log.d("mLog","itemSelectedListener " );
    }
    @BindingAdapter(value = "newValue")
    public  void  setNewValue(Integer newValue){
        Log.d("mLog","newValue " + newValue);
    }*/


}
