package com.example.muirsuus;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.ui.fragments.DescriptionFragment;
import com.example.muirsuus.ui.fragments.TthFragment;
public class InformActivity extends AppCompatActivity {
    Button btn_tth;
    Button btn_history;
    Button btn_description;
    Army DataBaseInfo;
    Boolean isFavorite = false;
    boolean isNotCliсked = true;
    private DataBaseHelper mDBHelper;

    private static final int PERMISSION_REQUEST_CODE = 100;

    private SQLiteDatabase mDb;
    private Fragment fragment;
    private String string_name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inform_layout);


        btn_tth = (Button) findViewById(R.id.btn_tth);
        btn_description = (Button) findViewById(R.id.btn_description);




        Intent intent = getIntent();
        string_name = intent.getStringExtra("Build points");//получаем название view, на который нажал пльзователь
        Log.d("mLog", "Get " + string_name + "to InformActivity");



//--------------------изначально находимся в ТТХ----------------
        fragment = new TthFragment(string_name);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        // анимация для фрагментов
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE); анимация, переходы между фрагментами
        transaction.replace(R.id.fragment_managed_by_buttons, fragment);
        transaction.commit();
//--------------------изначально находимся в ТТХ----------------
        View.OnClickListener listener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(v == btn_tth){
                    btn_tth.setBackgroundResource(R.drawable.button_pressed);
                    btn_description.setBackgroundResource(R.drawable.button_inf_style);
                    fragment = new TthFragment(string_name);
                    Log.d("mLog","Make TTH Fragment with " +string_name);
                }
                else {
                    btn_description.setBackgroundResource(R.drawable.button_pressed);
                    btn_tth.setBackgroundResource(R.drawable.button_inf_style);
                    fragment = new DescriptionFragment(string_name);//передаём нужное ID во фрагмент
                    Log.d("mLog","Make Description Fragment with " +string_name);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // анимация для фрагментов
                //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE); анимация, переходы между фрагментами
                transaction.replace(R.id.fragment_managed_by_buttons, fragment);
                transaction.commit();


            }
        };
        btn_description.setOnClickListener(listener);
        btn_tth.setOnClickListener(listener);
       // btn_history.setOnClickListener(listener);



    }
//-------------------СОЗДАНИЕ TOP BAR MENU-------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.favorite:
                if(isNotCliсked) {
                    isNotCliсked = false;
                    mDBHelper = new DataBaseHelper(this);
                    Intent intent = getIntent();
                    string_name = intent.getStringExtra("Build points");//получаем название view, на который нажал пльзователь
                    mDBHelper.addToFavoriteList(string_name);

                    Toast.makeText(this,"Добавлено в закладки",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Вы уже добавили в закладки!",Toast.LENGTH_SHORT).show();
                }
                return true;

            case  R.id.share:
                Toast.makeText(this,"Вы  поделились ",Toast.LENGTH_SHORT).show();;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
//-------------------СОЗДАНИЕ TOP BAR MENU---------------------------------------------------------------------------------------------

   /* //--------------------------------Доступ к Галерее------------------------------------------------------------------------------------------
    private void requestPermissionWrite(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(SecondLevelOfNesting.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SecondLevelOfNesting.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SecondLevelOfNesting.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------*/ //без них тоже работает, нужны для разрегения к галерее





}

