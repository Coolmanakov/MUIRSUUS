package com.example.muirsuus.main_navigation.favourite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.muirsuus.adapters.TTHAdapter;
import com.example.muirsuus.information_database.AppDatabase;
import com.example.muirsuus.information_database.point;
import com.example.muirsuus.registration.RegistrationDB;
import com.example.muirsuus.registration.privateInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@SuppressLint("StaticFieldLeak")
public class FavouriteViewModel extends ViewModel {
    private static final String LOG_TAG = "mLog " + TTHAdapter.class.getCanonicalName();
    private final MutableLiveData<List<String>> _listFavourites = new MutableLiveData<>();
    private final RegistrationDB registrationDB;
    private final AppDatabase appDatabase;
    private final LifecycleOwner lifecycleOwner;
    public LiveData<List<String>> listFavourites = _listFavourites; //храним список понравившихся point
    public Boolean alreadyFavourite = false; //для тех элементов, которые уже в Закладках
    private List<point> points = new ArrayList<>(); // список point с картинками и описанием
    private String userName = "";


    public FavouriteViewModel(Context context, String userName, LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
        this.userName = userName;
        registrationDB = RegistrationDB.getInstance(context);
        appDatabase = AppDatabase.getInstance(context);


    }

    public LiveData<List<String>> getInformation() {
        return listFavourites;
    }

    public List<point> getPoints()  {
        GetFavouriteTask favouriteTask = new GetFavouriteTask();
        favouriteTask.execute();
        try {
            points = favouriteTask.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return  points;
    }




    //метод, который добавляет и удаляет объекты из FavouriteList,
    // который берем из базы  после чего обновляем базу
    public void addToFavourite(String itemName, Boolean favourite) {
        new AsyncTask<Void, List<String>, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> oldList = registrationDB.registrationDAO().getFavourites(userName);
                publishProgress(oldList);
                return  oldList;
            }

            @Override
            protected void onProgressUpdate(List<String>... values) {
                super.onProgressUpdate(values);
                Log.d(LOG_TAG, "oldList " + values[0]);
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                super.onPostExecute(strings);

                if(strings == null){
                    strings = new ArrayList<>();
                }
                if (favourite) {
                    //добавляем понравившийся item
                    strings.add(itemName);
                    Log.d(LOG_TAG, "Add " + itemName + " to favourite List for user " + userName);
                } else {
                    if(!strings.isEmpty()) {
                        String[] words = strings.get(0).split(",");
                        strings = Arrays.stream(words).collect(Collectors.toList());
                        strings.remove(itemName);
                        Log.d(LOG_TAG, "Delete " + itemName + " from favouriteList for user " + userName);
                    }
                }

                _listFavourites.setValue(strings);
                updateUserFavList(userName, listFavourites.getValue());

            }
        }.execute();
    }
//метод, который обновляет список favourite в базе
    private void updateUserFavList(String userName, List<String> newList){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                // Запрашиваем запрос данных пользователя
                privateInfo user = registrationDB.registrationDAO().getUsersData(userName);
                //обновляем данные в БД
                user.setListFavourite(newList);
                Log.d(LOG_TAG, "Favourite list of user " + userName + " = " + user.getListFavourite());
                registrationDB.registrationDAO().updateUser(user);
                Log.d(LOG_TAG, "Favourite list of user " + userName + " successfully updated new list = " + newList);
                return null;
            }
        }.execute();
    }
     class GetFavouriteTask extends AsyncTask<Void, List<point>, List<point>>{
        @Override
        protected List<point> doInBackground(Void... voids) {
            List<String> oldList = registrationDB.registrationDAO().getFavourites(userName);
            List<point> points = new ArrayList<>();
            if(oldList != null && !oldList.isEmpty()){
                String[] words = oldList.get(0).split(",");
                for(int i = 0; i < words.length; i++){
                    point point = appDatabase.informationDAO().getPoint(words[i]);
                    if(point != null) {
                        points.add(point);
                    }

                }
            }
            return points;
        }

        @Override
        protected void onPostExecute(List<point> _points) {
            super.onPostExecute(_points);
            points = _points;
            Log.d(LOG_TAG, "Favourite points list " + points);
        }
    }

}
