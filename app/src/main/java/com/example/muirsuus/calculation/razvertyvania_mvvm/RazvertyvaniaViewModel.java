package com.example.muirsuus.calculation.razvertyvania_mvvm;

import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RazvertyvaniaViewModel extends BaseObservable {
    public ObservableField<Integer> quantity = new ObservableField<>();
    public ObservableField<List<Integer>> quantityEntries = new ObservableField<>();

/*
    @BindingAdapter(value = "entries")
    public void setEntries(List<Integer> entries){
        setEntries(entries);
    }
    @BindingAdapter(value = "onItemSelected")
    public  void setItemSelectedListener(AdapterView.OnItemSelectedListener itemSelectedListener){
        setItemSelectedListener(itemSelectedListener);
    }
    @BindingAdapter(value = "newValue")
    public  void  setNewValue(Integer newValue){
        setNewValue(newValue);
    }*/




}
