package com.example.muirsuus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.example.muirsuus.classes.MyObject;
import com.example.muirsuus.ui.layouts.DescriptionFragment;
import com.example.muirsuus.ui.layouts.HistoryFragment;
import com.example.muirsuus.ui.layouts.TthFragment;

public class SecondLevelOfNesting extends AppCompatActivity {
    Button btn_tth;
    Button btn_history;
    Button btn_description;
    DataBaseHelper mDBHelper;
    private static final int PERMISSION_REQUEST_CODE = 100;
    NavController navController;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_khm);
//---------------------------------------------------------------------------------------------------
        /*String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!checkPermission()) {
                    requestPermissionWrite();
                }
            }
        }

        mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
//---------------------------------------------------------------------------------------------------------------------------------------------*/

        final MyObject myObj = (MyObject) getIntent().getParcelableExtra(MyObject.class.getCanonicalName());

        btn_tth = (Button) findViewById(R.id.btn_tth);
        btn_description = (Button) findViewById(R.id.btn_description);
        btn_history = (Button) findViewById(R.id.btn_history);

       //final NavController navController = Navigation.findNavController(this, R.id.fragment_managed_by_buttons);


        btn_tth.setBackgroundResource(R.drawable.button_const);
        btn_description.setBackgroundResource(R.drawable.button_const);
        btn_history.setBackgroundResource(R.drawable.button_const);

        View.OnClickListener listener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                if(v == btn_tth){
                    btn_tth.setBackgroundResource(R.drawable.button_pressed);
                    btn_description.setBackgroundResource(R.drawable.button_const);
                    btn_history.setBackgroundResource(R.drawable.button_const);
                    fragment = new TthFragment();

                }
                else
                    if(v == btn_description){
                        btn_description.setBackgroundResource(R.drawable.button_pressed);
                        btn_tth.setBackgroundResource(R.drawable.button_const);
                        btn_history.setBackgroundResource(R.drawable.button_const);
                        fragment = new DescriptionFragment();

                    }
                    else {
                        btn_history.setBackgroundResource(R.drawable.button_pressed);
                        btn_tth.setBackgroundResource(R.drawable.button_const);
                        btn_description.setBackgroundResource(R.drawable.button_const);
                        fragment = new HistoryFragment();

                    }
                    if(myObj.s == 0 && myObj.i == 0){
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_managed_by_buttons, fragment);
                    transaction.commit();
                    }
            }
        };
        btn_description.setOnClickListener(listener);
        btn_tth.setOnClickListener(listener);
        btn_history.setOnClickListener(listener);


    }

//--------------------------------------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------------------------------------------------------
}

