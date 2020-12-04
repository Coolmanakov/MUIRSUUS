package com.example.muirsuus.registration;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = privateInfo.class, version = 2, exportSchema = true)
public abstract class RegistrationDB extends RoomDatabase {
    private static final String LOG_TAG = "mLog";
    private static RegistrationDB mInstance;

    public static RegistrationDB getInstance(Context context) {

        if (mInstance == null) {
            synchronized (new Object()) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(), RegistrationDB.class, "registration")
                        .fallbackToDestructiveMigration()
                        .build();
                Log.d(LOG_TAG, "RegistrationDB: creating a new instance of RegistrationDB");


            }
        }
        return mInstance;
    }

    public abstract RegistrationDAO registrationDAO();

}
