package com.example.muirsuus.calculation.razvedka_mvvm;

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

public class RazvedkaViewModel extends BaseObservable {

    private final MutableLiveData<String> _result = new MutableLiveData<>(); // разведзащищенность УС
    private final MutableLiveData<String> _element_current_number = new MutableLiveData<>("1"); // текущий номер перемнной
    private final MutableLiveData<String> _element_previous_number = new MutableLiveData<>(""); //номер предыдущего элемента
    private final MutableLiveData<Boolean> _isVisible = new MutableLiveData<>(false); // видна ли кнопка перерасчёт и доступна для изменения count
    private final String LOG_TAG = "mLog" + RazvedkaViewModel.class.getCanonicalName();
    public LiveData<String> result = _result;
    public String count_opened_tracks = ""; // количество вскрытых противником аппаратных
    public String element = ""; // количество вскрытых противником жлементов
    public String all_tracks = ""; // общее количество аппаратных
    public LiveData<String> element_previous_number = _element_previous_number;
    public Context context;
    public LiveData<Boolean> isVisible = _isVisible;
    public LiveData<String> element_current_number = _element_current_number;
    private List<RazvedkaViewModel> razvedkaViewModelList = new ArrayList<>();  // список рассчитанных разведзащищенностей


    RazvedkaViewModel() {
    }

    RazvedkaViewModel(String count_opened_tracks, String all_tracks, String _element_current_number, String _element_previous_number, String result) {
        this.count_opened_tracks = count_opened_tracks;
        this.all_tracks = all_tracks;
        this._element_current_number.setValue(_element_current_number);
        this._element_previous_number.setValue(_element_previous_number);
        this._result.setValue(result);
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

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * метод который рассчитывает разведзащищенность Узла Связи
     * рассчиывается значение для данного узла, затем оно добавляется в список рассчитанных значений
     */
    public void calculate() {
        // проверка, все поля заполнены
        if (inputNotEmpty()) {
            if (Integer.parseInt(getElement()) > Integer.parseInt(_element_current_number.getValue())) {

                // значение живучести
                double temp = Math.floor((1 - Math.exp(-1 * (Double.parseDouble(count_opened_tracks) / (Double.parseDouble(all_tracks))))) * 100) / 100;
                _result.setValue(Double.toString(temp));

                //добавляем значение живучести в список
                RazvedkaViewModel razvedkaViewModel = new RazvedkaViewModel(count_opened_tracks, all_tracks, element_current_number.getValue(), element_previous_number.getValue(), result.getValue());
                razvedkaViewModelList.add(razvedkaViewModel);

                Log.d(LOG_TAG, "Add new data to zhivuchestViewModelList = " + razvedkaViewModelList);

                // менняем предыдущее значение на текущее
                _element_previous_number.setValue(_element_current_number.getValue());
                //увеличим текущее на 1
                _element_current_number.setValue(String.valueOf(Integer.parseInt(_element_previous_number.getValue()) + 1));

                _isVisible.setValue(true);

                // в поля добавляем пропуски
                setCount_opened_tracks("");
                setAll_tracks("");
            } else {
                //отдельно проверяем не равно ли текущее прыдыдущему, т.к. в том случае нужно поменять только предыдущее значение на текущее
                if (Integer.parseInt(_element_current_number.getValue()) != Integer.parseInt(_element_previous_number.getValue())) {
                    //если мы рассчитываем последний элемент УС
                    if (Integer.parseInt(getElement()) == Integer.parseInt(_element_current_number.getValue())) {
                        double temp = Math.floor((1 - Math.exp(-1 * (Double.parseDouble(count_opened_tracks) / (Double.parseDouble(all_tracks))))) * 100) / 100;
                        _result.setValue(Double.toString(temp));

                        RazvedkaViewModel razvedkaViewModel = new RazvedkaViewModel(count_opened_tracks, all_tracks, element_current_number.getValue(), element_previous_number.getValue(), result.getValue());
                        razvedkaViewModelList.add(razvedkaViewModel);

                        _element_previous_number.setValue(_element_current_number.getValue());
                        Log.d(LOG_TAG, "Calculate last element razvedkaViewModelList = " + razvedkaViewModelList);
                    }
                    _isVisible.setValue(true);
                }

            }
        } else {
            Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * метод, который выполняет перерасчёт предыдущего значения разведзащищенности  УС
     */
    public void previous_data() {
        // если мы дошли до первого элемента, то для него текущее значение равно ""
        if (!_element_previous_number.getValue().equals("")) {
            //проверяем есть ли записи в списке значений разведзащщенности
            if (!razvedkaViewModelList.isEmpty()) {
                //получаем последнее значение добавленное в список
                RazvedkaViewModel razvedkaViewModel = razvedkaViewModelList.get(Integer.parseInt(element_previous_number.getValue()) - 1);
                // если мы изменяем  первое значение, создаём новый список
                if ((Integer.parseInt(element_previous_number.getValue()) - 1) == 0) {
                    razvedkaViewModelList = new ArrayList<>();
                    Log.d(LOG_TAG, " Change first element: clean all list");
                }
                // не забываем удалить последнее значение, чтобы возможно было записать новые данные в список
                else {
                    razvedkaViewModelList.remove(Integer.parseInt(element_previous_number.getValue()) - 1);
                    Log.d(LOG_TAG, "delete data with index " + (Integer.parseInt(element_previous_number.getValue()) - 1) + " " + razvedkaViewModelList);
                }

                setAll_tracks(razvedkaViewModel.all_tracks);
                setCount_opened_tracks(razvedkaViewModel.count_opened_tracks);

                _element_current_number.setValue(razvedkaViewModel.element_current_number.getValue());
                _element_previous_number.setValue(razvedkaViewModel.element_previous_number.getValue());
                _result.setValue(razvedkaViewModel.result.getValue());

            }
        } else {
            Log.d(LOG_TAG, "RazvedkaViewModelList is empty");
        }
    }

    // проверка, чтобы были заполнены все поля
    private boolean inputNotEmpty() {
        return !element.equals("") && !all_tracks.equals("") && !count_opened_tracks.equals("");
    }
}