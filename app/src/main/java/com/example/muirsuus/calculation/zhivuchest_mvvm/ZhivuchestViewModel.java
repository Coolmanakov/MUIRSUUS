package com.example.muirsuus.calculation.zhivuchest_mvvm;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ZhivuchestViewModel extends ViewModel {


    private MutableLiveData<Double> _zhivuchest_element  = new MutableLiveData<Double>();
    public LiveData<Double> zhivuchest_element = _zhivuchest_element;
    public String opened_tracks = "";
    public String area = "";
    public  String all_tracks = "";
    public  String koef_pvo = "";
    public  String koef_rab = "";
    public String count = "";
    public String number = "Элемент № " ;
    public String max_area = "" ;
    private int number_int = 0;



    private List<ZhivuchestViewModel> list_observables = new ArrayList<>();
    private  int count_int = 0;



    public ZhivuchestViewModel(String area, String opened_tracks, String all_tracks, String koef_pvo, String koef_rab, String count, String number) {
        this.opened_tracks = opened_tracks;
        this.area = area;
        this.all_tracks = all_tracks;
        this.koef_pvo = koef_pvo;
        this.koef_rab = koef_rab;
        this.count = count;
        this.number = number;
    }

    public ZhivuchestViewModel( ){}


    public  void calculate_data(){
        ZhivuchestViewModel zhivuchestViewModel;

        if( inputNotEmpty() ) {
            double temp;



            zhivuchestViewModel = new ZhivuchestViewModel(area, opened_tracks, all_tracks, koef_pvo, koef_rab, count, number);
            list_observables.add(zhivuchestViewModel);
            temp = 1 -
                    (ammunition_delivery_probability(Float.parseFloat(koef_pvo), Float.parseFloat(koef_rab))
                            * target_hit_probability(Float.parseFloat(area), Float.parseFloat(max_area))
                            * opening_probability(Float.parseFloat(opened_tracks), Float.parseFloat(all_tracks)));

            _zhivuchest_element.setValue(temp);
        }

    }

    public ZhivuchestViewModel previous_data(){

        if(!count.equals("")){
            count_int = Integer.parseInt(count);
            //count_int = count_int - number;
        }
        return  list_observables.get(count_int);
    }

    //вероятность доставки боеприпаса (поражающего элемента) в район цели
    private float ammunition_delivery_probability( float pvo, float rub){

        return (1 - pvo) * ( 1 - rub);
    }
    //вероятность поражения цели
    private float target_hit_probability( float area, float max_area){
        float result = 0;
        if(max_area >= area){
            result = 1;
        }
        else  result = max_area/area;
        return result;
    }
    //вероятность вскрытия элемента
    private float opening_probability(float opened_tracks, float all_tracks){
        return opened_tracks/all_tracks;
    }

    private boolean inputNotEmpty(){
        return !area.equals("") && !opened_tracks.equals("") && !all_tracks.equals("") && !koef_pvo.equals("") && !koef_rab.equals("") && !count.equals("");

    }


}
