package com.example.muirsuus.adapters.tth_mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TthViewModel extends ViewModel {


    private List<String> _description = new ArrayList<>();
    public MutableLiveData<String> description = new MutableLiveData<>();

    private MutableLiveData<String> _title = new MutableLiveData<>();
    public LiveData<String> title = _title;




    public TthViewModel(){}

    public TthViewModel(String title, String descr){
        _title.setValue(title);

        description.setValue(descr);

    }

    public LiveData<String> getTitle() {
        return title;
    }

    public void setTitle(LiveData<String> title) {
        this.title = title;
    }


    public MutableLiveData<String> get_title() {
        return _title;
    }

    public void set_title(MutableLiveData<String> _title) {
        this._title = _title;
    }

    public List<String> get_description() {
        return _description;
    }

    public void setDescription() {
        _description.add("Боевые и вспомогательные  бронированные военные машины , созданные на базе  БТТ или предназначенные для их обслуживания " +
                "(как на колёсном, так и на гусеничном ходу.");

        _description.add("Пистолеты, автоматы, снайперские винтовки, карабины и ружья");
        _description.add("Гранаты, гранатометы, мины, взрвчатые вещества и взрывателии");
        _description.add("Законы, указы, приказы, уставы и военная доктрина");
        _description.add("Комплекс организационных и технических мероприятий, направленных на обеспечение " +
                "своевременного и качественного обмена всеми видами информации по управлению войсками");

        _description.add("Огневая, медицинская, тактическая, инженерная, горная подготовки и топография");

        _description.add("Создание, настройка и обслуживание внутренних компьютерных сетей, а также " +
                "взаимодействие IT-инфраструктуры с внешними сетями");

        _description.add("Средства, обеспечивающие: быстрый сбор, обработку и отображение (выдачу) данных обстановки");
        _description.add("Комплекс различных видов вооружения и средств, обеспечивающих решение задач РХБ защиты");

    }
}
