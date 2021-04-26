package com.example.muirsuus.main_navigation.lit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.information_database.InformationDatabase;

import java.util.ArrayList;
import java.util.List;

/*
Найденные файлы нужно сохранять в модель, чтобы затем передать в список.
Для этого создадим класс LitViewModel, в котором будут определены 2 поля (имя файла и путь),
а также сеттеры и геттеры для изменения полей.
 */
@SuppressLint("StaticFieldLeak")
public class LitViewModel extends ViewModel {
    private final MutableLiveData<List<String>> _bookPhotos = new MutableLiveData<>();
    private final LiveData<List<String>> bookPhotos = _bookPhotos;//здесь храним все фотки из БД
    private final InformationDatabase informationDatabase;
    private final List<String> bookNameList = new ArrayList<>(); //получаем список книг, к которым нужно найти картинку
    private Context context;

    public LitViewModel(Context context, List<String> bookNameList) {
        informationDatabase = InformationDatabase.getInstance(context);
        // из исходного списка удаляем ".pdf"
        for (int i = 0; i < bookNameList.size(); i++) {

            String pdfName = bookNameList.get(i);
            StringBuffer buffer = new StringBuffer(pdfName);
            buffer.delete(pdfName.length() - 4, pdfName.length()); //удаляем ".pdf" из исходной строки
            String name = buffer.substring(0); //получаем конечное название книги

            this.bookNameList.add(name);
        }

    }

    private void getPhotosFromDB() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> tmp = new ArrayList<>();
                for (int i = 0; i < bookNameList.size(); i++) {
                    String bookPhoto = informationDatabase.informationDAO().getBookPhoto(bookNameList.get(i));
                    tmp.add(bookPhoto); //заполняем список фотографий книг по имени книги
                }
                return tmp;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                super.onPostExecute(strings);
                _bookPhotos.setValue(strings);
            }
        }.execute();
    }

    public LiveData<List<String>> getPhotos() {
        getPhotosFromDB();
        return bookPhotos;
    }


}