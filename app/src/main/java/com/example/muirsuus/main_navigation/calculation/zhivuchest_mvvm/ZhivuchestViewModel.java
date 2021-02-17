package com.example.muirsuus.main_navigation.calculation.zhivuchest_mvvm;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.muirsuus.BR;

import java.util.ArrayList;
import java.util.List;

public class ZhivuchestViewModel extends BaseObservable {


    private final String LOG_TAG = "mLog" + ZhivuchestViewModel.class.getCanonicalName();
    private final MutableLiveData<Boolean> _isVisible = new MutableLiveData<>(false); // видна ли кнопка перерасчёт и доступна для изменения count
    private final MutableLiveData<String> _element_current_number = new MutableLiveData<>("1"); //номер элемента, который считаем (Integer) // текущий номер перемнной
    private final MutableLiveData<String> _element_previous_number = new MutableLiveData<>(""); //номер предыдущего элемента
    private final MutableLiveData<String> _result = new MutableLiveData<>(); // живучесть УС
    public String count = ""; // количество элементов, входящих в состав УС(ПУ)
    public String max_area = ""; // максимальная плщадь поражения боеприпасами
    public String opened_tracks = "";// количество выскрытых противником аппаратных
    public String area = "";// площадь цели
    public String all_tracks = "";// общее количество аппаратных
    public String koef_pvo = "";// эффективность действия группировки ПВО
    public String koef_rab = "";// эффективность действия группировки РЭБ
    public Context context;
    public LiveData<Boolean> isVisible = _isVisible;
    public LiveData<String> element_current_number = _element_current_number;
    public LiveData<String> element_previous_number = _element_previous_number;
    public LiveData<String> result = _result;
    private List<ZhivuchestViewModel> zhivuchestViewModelList = new ArrayList<>(); // список рассчитанных живучестей


    public ZhivuchestViewModel() {
    }

    public ZhivuchestViewModel(String area, String opened_tracks, String all_tracks, String koef_pvo, String koef_rab, String current_number, String previous_nubmer, String result) {
        this.opened_tracks = opened_tracks;
        this.area = area;
        this.all_tracks = all_tracks;
        this.koef_pvo = koef_pvo;
        this.koef_rab = koef_rab;
        this._element_current_number.setValue(current_number);
        this._element_previous_number.setValue(previous_nubmer);
        this._result.setValue(result);

    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Bindable
    public String getOpened_tracks() {
        return opened_tracks;
    }

    public void setOpened_tracks(String opened_tracks) {
        this.opened_tracks = opened_tracks;
        notifyPropertyChanged(BR.opened_tracks);
    }

    @Bindable
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
        notifyPropertyChanged(BR.area);
    }

    @Bindable
    public String getAll_tracks() {
        return all_tracks;
    }

    public void setAll_tracks(String all_tracks) {
        this.all_tracks = all_tracks;
        notifyPropertyChanged(BR.all_tracks);
    }

    @Bindable
    public String getKoef_pvo() {
        return koef_pvo;
    }

    public void setKoef_pvo(String koef_pvo) {
        this.koef_pvo = koef_pvo;
        notifyPropertyChanged(BR.koef_pvo);
    }

    @Bindable
    public String getKoef_rab() {
        return koef_rab;
    }

    public void setKoef_rab(String koef_rab) {
        this.koef_rab = koef_rab;
        notifyPropertyChanged(BR.koef_rab);
    }

    @Bindable
    public String getMax_area() {
        return max_area;
    }

    public void setMax_area(String max_area) {
        this.max_area = max_area;
        notifyPropertyChanged(BR.max_area);
    }

    @Bindable
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    /**
     * метод который рассчитывает живучесть Узла Связи
     * рассчиывается значение для данного узла, затем оно добавляется в список рассчитанных значений
     */
    public void calculate_data() {
        // проверка, все поля заполнены
        if (inputNotEmpty()) {
            if (Integer.parseInt(getCount()) >= Integer.parseInt(_element_current_number.getValue())) {
                double temp;
                // значение живучести
                temp = Math.floor((1 -
                        (ammunition_delivery_probability(Float.parseFloat(koef_pvo), Float.parseFloat(koef_rab))
                                * target_hit_probability(Float.parseFloat(area), Float.parseFloat(max_area))
                                * opening_probability(Float.parseFloat(opened_tracks), Float.parseFloat(all_tracks)))) * 100) / 100;

                _result.setValue(Double.toString(temp));

                //добавляем значение живучести в список
                ZhivuchestViewModel zhivuchestViewModel = new ZhivuchestViewModel(area, opened_tracks, all_tracks, koef_pvo, koef_rab, element_current_number.getValue(),
                        element_previous_number.getValue(), result.getValue());
                zhivuchestViewModelList.add(zhivuchestViewModel);

                Log.d(LOG_TAG, "Add new data to zhivuchestViewModelList = " + zhivuchestViewModelList);

                // менняем предыдущее значение на текущее
                _element_previous_number.setValue(_element_current_number.getValue());
                //увеличим текущее на 1
                _element_current_number.setValue(String.valueOf(Integer.parseInt(_element_previous_number.getValue()) + 1));

                _isVisible.setValue(true);
                // в поля добавляем пропуски
                setAll_tracks("");
                setOpened_tracks("");
                setArea("");
                setKoef_rab("");
                setKoef_pvo("");
            } else {
                //отдельно проверяем не равно ли текущее прыдыдущему, т.к. в том случае нужно поменять только предыдущее значение на текущее
                if (Integer.parseInt(_element_previous_number.getValue()) != Integer.parseInt(_element_current_number.getValue())) {
                    //если мы рассчитываем последний элемент УС
                    if (Integer.parseInt(getCount()) == Integer.parseInt(_element_current_number.getValue())) {
                        double temp;
                        // значение живучести
                        temp = Math.floor((1 -
                                (ammunition_delivery_probability(Float.parseFloat(koef_pvo), Float.parseFloat(koef_rab))
                                        * target_hit_probability(Float.parseFloat(area), Float.parseFloat(max_area))
                                        * opening_probability(Float.parseFloat(opened_tracks), Float.parseFloat(all_tracks)))) * 100) / 100;

                        _result.setValue(Double.toString(temp));

                        ZhivuchestViewModel zhivuchestViewModel = new ZhivuchestViewModel(area, opened_tracks, all_tracks, koef_pvo, koef_rab, element_current_number.getValue(),
                                element_previous_number.getValue(), result.getValue());
                        zhivuchestViewModelList.add(zhivuchestViewModel);
                        Log.d(LOG_TAG, "Calculate last element zhivuchestViewModelList = " + zhivuchestViewModelList);

                        _element_previous_number.setValue(_element_current_number.getValue());
                    }
                    _isVisible.setValue(true);
                }
            }
        } else {
            Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * метод, который выполняет перерасчёт предыдущего значения живучести УС
     */
    public void previous_data() {
        // если мы дошли до первого элемента, то для него текущее значение равно ""
        if (!_element_previous_number.getValue().equals("")) {
            //проверяем есть ли записи в списке значений живучести
            if (!zhivuchestViewModelList.isEmpty()) {
                //получаем последнее значение добавленное в список
                ZhivuchestViewModel zhivuchestViewModel = zhivuchestViewModelList.get(Integer.parseInt(element_previous_number.getValue()) - 1);

                // если мы изменяем  первое значение, создаём новый список
                if ((Integer.parseInt(element_previous_number.getValue()) - 1) == 0) {
                    zhivuchestViewModelList = new ArrayList<>();
                    Log.d(LOG_TAG, " Change first element: clean all list");
                }
                // не забываем удалить последнее значение, чтобы возможно было записать новые данные в список
                else {
                    zhivuchestViewModelList.remove(Integer.parseInt(element_previous_number.getValue()) - 1);
                    Log.d(LOG_TAG, "delete data with index " + (Integer.parseInt(element_previous_number.getValue()) - 1) + " zhivuchestViewModelList =  " + zhivuchestViewModelList);
                }

                setArea(zhivuchestViewModel.area);
                setOpened_tracks(zhivuchestViewModel.opened_tracks);
                setAll_tracks(zhivuchestViewModel.all_tracks);
                setKoef_pvo(zhivuchestViewModel.koef_pvo);
                setKoef_rab(zhivuchestViewModel.koef_rab);


                _element_current_number.setValue(zhivuchestViewModel.element_current_number.getValue());
                _element_previous_number.setValue(zhivuchestViewModel.element_previous_number.getValue());
                _result.setValue(zhivuchestViewModel.result.getValue());
            } else {
                Log.d(LOG_TAG, " ZhivuchestViewModelList is empty");
            }
        }
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

    // проверка, чтобы были заполнены все поля
    private boolean inputNotEmpty(){
        return !area.equals("") && !opened_tracks.equals("") && !all_tracks.equals("") && !koef_pvo.equals("") && !koef_rab.equals("") && !count.equals("");

    }
}
