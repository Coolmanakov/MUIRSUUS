package com.example.muirsuus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.muirsuus.adapters.GalleryAdapter;
import com.example.muirsuus.adapters.ViewPagerAdapter;
import com.example.muirsuus.classes.Army;
import com.example.muirsuus.classes.DataBaseHelper;
import com.example.muirsuus.classes.IRecyclerViewClickListener;
import com.example.muirsuus.main_navigation.information_fragmnets.DescriptionFragment;
import com.example.muirsuus.main_navigation.information_fragmnets.TthFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.muirsuus.classes.IndependentMethods.get_list_images;

public class InformActivity extends AppCompatActivity  {
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
    FrameLayout frameLayout;
    NavHostFragment navHostFragment;
    private RecyclerView photo_recycler;
    private  GalleryAdapter galleryAdapter;

    private TabLayout tab_buttons;
    private ViewPager viewPager;

    private  LinearLayoutManager layoutManager;
    private List<String> photo_list = new ArrayList<>();
    ActionBar actionBar;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inform_layout);


        /*btn_tth = (Button) findViewById(R.id.btn_tth);
        btn_description = (Button) findViewById(R.id.btn_description);*/




        Intent intent = getIntent();
        string_name = intent.getStringExtra("Build points");//получаем название view, на который нажал пльзователь
        Log.d("mLog", "Get " + string_name + "to InformActivity");
        setTitle(string_name);


        viewPager = findViewById(R.id.text_pager);
        tab_buttons = findViewById(R.id.tab_buttons);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        viewPagerAdapter.addFragment(new DescriptionFragment(string_name),"Назначение");
        viewPagerAdapter.addFragment(new TthFragment(string_name), "TTX");
        viewPager.setAdapter(viewPagerAdapter);
        tab_buttons.setupWithViewPager(viewPager);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        photo_list = get_list_images(dataBaseHelper.get_list_photo(string_name));

        photo_recycler = findViewById(R.id.photo_recycler);
        photo_recycler.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photo_recycler.setLayoutManager(layoutManager);


        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(InformActivity.this, FullScreenActivity.class);
                i.putExtra("IMAGES",photo_list.toArray(new String[0]));
                Log.d("mLog", "photo List = " +photo_list.toArray(new String[0]));
                i.putExtra("POSITION",position);
                startActivity(i);
            }

        };
        galleryAdapter = new GalleryAdapter(photo_list, listener, this);
        photo_recycler.setAdapter(galleryAdapter);

/*
//--------------------изначально находимся в ТТХ----------------
        frameLayout = findViewById(R.id.fragment_managed_by_buttons);
        frameLayout.setOnTouchListener(this);
        fragment = new TthFragment(string_name);
        replace_fragment(fragment,R.id.fragment_managed_by_buttons,getSupportFragmentManager());
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

                replace_fragment(fragment,R.id.fragment_managed_by_buttons,getSupportFragmentManager());

            }
        };
        btn_description.setOnClickListener(listener);
        btn_tth.setOnClickListener(listener);
       // btn_history.setOnClickListener(listener);



        // Получаем объект ViewFlipper
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOnTouchListener(this);

        / Создаем View и добавляем их в уже готовый flipper
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layouts[] = new int[]{ R.layout.fragment_layout_tth,R.layout.fragment_layout_description};
        for (int layout : layouts)
            flipper.addView(inflater.inflate(layout, null));*/
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
                Toast.makeText(this, "Вы  поделились ", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

   /* @SuppressLint("ResourceType")
    public boolean onTouch(View view, MotionEvent event)
    {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float toPosition = event.getX();
                if (fromPosition > toPosition)
                {
                    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.animator.go_next_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.animator.go_next_out));
                    flipper.showNext();
                }
                else if (fromPosition < toPosition)
                {
                    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.animator.go_prev_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.animator.go_prev_out));
                    flipper.showPrevious();
                }
            default:
                break;
        }
        return true;
    }
    */
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
  /* public static  void replace_fragment(Fragment fragment, int nav_host_id, FragmentManager new_manager ){
       FragmentTransaction transaction = new_manager.beginTransaction();

       // анимация для фрагментов
       //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE); анимация, переходы между фрагментами
       transaction.replace(nav_host_id, fragment);
       transaction.commit();
   }*/




}

