package com.example.muirsuus.restApi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChainRequest implements ChainResponse {

    public static final String TOKEN = "token";
    public static final String MAIN_URL = "main_url";
    private final String ONLINE_URL = "http://91.210.170.162:80";
    private final String OFFLINE_URL = "http://192.168.20.100:80";
    private final String[] chainURLs = new String[]{OFFLINE_URL, ONLINE_URL};
    private final Context context;
    private final String name;
    private final String password;
    public MutableLiveData<String> _responseName = new MutableLiveData<>();//имя полученное из запроса
    public LiveData<String> responseName = _responseName;
    private int currentRequestPosition = 0;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private SharedPreferences savedToken;
    private SharedPreferences savedUrl;


    public ChainRequest(Context context, String name, String password) {
        this.context = context;
        this.name = name;
        this.password = password;

    }

    @Override
    public void nextRequest() {
        ClientServerWorker worker = new ClientServerWorker(chainURLs[currentRequestPosition]);
        jsonPlaceHolderApi = worker.getInstance();

        Post post = new Post(name, password);
        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            //Get Response from Server,
            // but it can be not successful

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    //Something went wrong response.code()
                    _responseName.setValue("Error Response Code");
                    return;
                }
                String token = response.body().getToken();
                //всё хорошо пускаем пользователя
                if (token != null) {
                    getClientByToken("token " + token);
                }

                //сохраняем url,
                // по которому можно получить данные
                // из облака или локального сервака

                savedUrl = context.getSharedPreferences(MAIN_URL, Context.MODE_PRIVATE);
                SharedPreferences.Editor urlEditor = savedUrl.edit();
                urlEditor.putString(MAIN_URL, chainURLs[currentRequestPosition]);
                urlEditor.apply();

            }

            //Something went wrong with communication with the Server
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("mLog", t.getMessage());
                changeUrl();
            }
        });
    }


    private int changeUrl() {
        currentRequestPosition += 1;
        if (chainURLs.length != currentRequestPosition) {
            nextRequest();
        } else {
            Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            _responseName.setValue("No Internet Connection");
        }
        return currentRequestPosition;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getClientByToken(String token) {
        Call<User> me = jsonPlaceHolderApi.clientInfo(token);


        me.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    //Something went wrong response.code()
                    Log.e("mLog", "response.code() " + response.code());
                    return;
                }
                _responseName.setValue(response.body().getFirst_name());

                //сохраняем токен пользователя
                savedToken = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
                SharedPreferences.Editor tokenEditor = savedToken.edit();
                tokenEditor.putString(TOKEN, token);
                tokenEditor.apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("mLog", " exception " + t.getLocalizedMessage());

            }
        });
    }
}
