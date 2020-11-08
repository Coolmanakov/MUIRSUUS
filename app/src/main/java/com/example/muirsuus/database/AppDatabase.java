package com.example.muirsuus.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Database(entities = {table_ex.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = "mLog";
    private static String DB_NAME = "databases/MURSY_YS.db";
    private static AppDatabase mInstance;
    private static String DB_PATH = "";

    //TODO method, which will write db file in getFilesDir()


    public static AppDatabase getInstance(Context context) throws IOException {
        DB_PATH = context.getFilesDir() + File.separator;
        Log.d(LOG_TAG, "DB_PATH = " + DB_PATH);
        if (mInstance == null) {
            synchronized (new Object()) {
                Log.d(LOG_TAG, "Creating a new database instance");
                create_db(context);
                mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME).
                        createFromFile(new File(context.getApplicationInfo().dataDir, DB_NAME)).build();
                Log.d("mLog", "" + new File(context.getApplicationInfo().dataDir, DB_NAME));

            }
        }
        return mInstance;
    }

    public static void create_db(Context context) {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH + DB_NAME);
            if (!file.exists()) {
                myInput = new FileInputStream(context.getApplicationInfo().dataDir + DB_NAME);

                myOutput = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);

                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }

        } catch (IOException ex) {

        }
    }

    public abstract InformationDAO informationDAO();
}
