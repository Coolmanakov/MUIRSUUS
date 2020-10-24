package com.example.muirsuus.calculation.razvedka_mvvm;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.muirsuus.BR;

import java.util.ArrayList;
import java.util.List;

public class RazvedkaViewModel extends BaseObservable {


    private MutableLiveData<String> _result = new MutableLiveData<>();
    public LiveData<String> result = _result;

    private MutableLiveData<String> _element_current_number = new MutableLiveData<>("1"); //номер элемента, который считаем (Integer)
    public LiveData<String> element_current_number = _element_current_number; //номер элемента, который считаем (String)

    private MutableLiveData<String> _element_previous_number = new MutableLiveData<>("");
    public LiveData<String> element_previous_number = _element_previous_number;

    /*private MutableLiveData<String> _elements = new MutableLiveData<>();// количество элементов, которое необходимо рассчитать (Integer)
    public LiveData<String> elements = _elements;// количество элементов, которое необходимо рассчитать (String)*/


    /*private MutableLiveData<String> _count_opened_tracks = new MutableLiveData<>();
    public LiveData<String> count_opened_tracks = _count_opened_tracks;


    private MutableLiveData<String> _all_tracks = new MutableLiveData<>();
    public LiveData<String> all_tracks = _all_tracks;*/
    public String count_opened_tracks = "";
    public String element = "";
    public String all_tracks = "";

    private MutableLiveData<Boolean> _isVisible = new MutableLiveData<>(false);
    public  LiveData<Boolean> isVisible = _isVisible;
    private List<RazvedkaViewModel> razvedkaViewModelList = new ArrayList<>();


    RazvedkaViewModel() {
    }
    RazvedkaViewModel(String count_opened_tracks,   String all_tracks, String _element_current_number, String _element_previous_number, String result){
        this.count_opened_tracks = count_opened_tracks;
        this.all_tracks = all_tracks;
        this._element_current_number.setValue(_element_current_number);
        this._element_previous_number.setValue(_element_previous_number);
        this._result.setValue(result);
    }


    public void calculate() {
        if( inputNotEmpty()){
        //если количество элементов. которое нужно рассчитать больше текущего номера элемента
            if (Integer.parseInt(getElement()) > Integer.parseInt(_element_current_number.getValue())) {


                double temp = Math.floor((1 - Math.exp(-1 * (Double.parseDouble(count_opened_tracks) / (Double.parseDouble(all_tracks)) )))* 100)/100;
                _result.setValue(Double.toString(temp));
                RazvedkaViewModel razvedkaViewModel = new RazvedkaViewModel(count_opened_tracks,all_tracks, element_current_number.getValue(), element_previous_number.getValue(), result.getValue());
                razvedkaViewModelList.add(razvedkaViewModel);

                //присвоим предыдущему значению текущее

                _element_previous_number.setValue(_element_current_number.getValue());
                //увеличим текущее на 1
                _element_current_number.setValue(String.valueOf(Integer.parseInt(_element_previous_number.getValue()) +1));
                _isVisible.setValue(true);

                setCount_opened_tracks("");

                setAll_tracks("");
            }
            else{
                if(Integer.parseInt(getElement()) == Integer.parseInt(_element_current_number.getValue())){
                    _element_previous_number.setValue(_element_current_number.getValue());
                }
            }
        }
    }

    private boolean inputNotEmpty() {
        Log.d("mLog","input not Empty" );
        return !element.equals("") && !all_tracks.equals("") && !count_opened_tracks.equals("");
    }

    public  void previous_data(){
        if(!_element_previous_number.getValue().equals("")) {
            if (!razvedkaViewModelList.isEmpty()) {
                RazvedkaViewModel razvedkaViewModel = razvedkaViewModelList.get(Integer.parseInt(element_previous_number.getValue()) - 1);
                setAll_tracks(razvedkaViewModel.all_tracks);
                setCount_opened_tracks(razvedkaViewModel.count_opened_tracks);
                _element_current_number.setValue(razvedkaViewModel.element_current_number.getValue());
                _element_previous_number.setValue(razvedkaViewModel.element_previous_number.getValue());
                _result.setValue(razvedkaViewModel.result.getValue());
            }
        }
    }

    @Bindable
    public String getCount_opened_tracks() {
        return count_opened_tracks;

    }

    public void setCount_opened_tracks(String count_opened_tracks) {
        this.count_opened_tracks = count_opened_tracks;
        notifyPropertyChanged(BR.count_opened_tracks);
    }

    @Bindable
    public String getElement() {
        return element;

    }

    public void setElement(String element) {
        this.element = element;
        notifyPropertyChanged(BR.element);
    }

    @Bindable
    public String getAll_tracks() {
        return all_tracks;

    }

    public void setAll_tracks(String all_tracks) {
        this.all_tracks = all_tracks;
        notifyPropertyChanged(BR.all_tracks);
    }

}
