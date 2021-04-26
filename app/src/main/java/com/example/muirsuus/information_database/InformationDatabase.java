package com.example.muirsuus.information_database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.muirsuus.main_navigation.lit.books;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Database(entities = {section.class, subsection.class, point.class, information.class, presubsection.class, books.class}, version = 5, exportSchema = false)
public abstract class InformationDatabase extends RoomDatabase {
    private static final String LOG_TAG = "mLog";
    private static final String DB_NAME = "annotated_db2.db";
    private static InformationDatabase mInstance;
    private static String DB_PATH = "";


    public static InformationDatabase getInstance(Context context) {
        DB_PATH = context.getFilesDir() + File.separator;
        if (mInstance == null) {
            synchronized (new Object()) {
                create_db(context);

                mInstance = Room.databaseBuilder(context.getApplicationContext(), InformationDatabase.class, InformationDatabase.DB_NAME)
                        .createFromAsset(DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

                Log.d(LOG_TAG, "InformationDatabase: creating a new instance of InformationDatabase");


            }
        }
        return mInstance;
    }

    /*
        method, which is responsible for copying DB
            from assets folder into getFilesDir()
     */
    public static void create_db(Context context) {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH + DB_NAME);
            //if (!file.exists()) {
            myInput = context.getAssets().open(DB_NAME);

            if (myInput != null) {
                Log.d(LOG_TAG, "InformationDatabase: making copy of DB from " + DB_NAME);
            } else {
                Log.d(LOG_TAG, "InformationDatabase: myInput = null");
            }

            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            Log.d(LOG_TAG, "InformationDatabase: making copy of DB to " + DB_PATH + DB_NAME);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);

            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
            //}


        } catch (IOException ex) {

        }
    }

    public abstract InformationDAO informationDAO();
}