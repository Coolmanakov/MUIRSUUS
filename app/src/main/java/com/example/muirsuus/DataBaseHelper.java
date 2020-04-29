/*package com.example.muirsuus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;

import android.util.Log;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db.sqlite";//name of DB
    private static final int DATABASE_VERSION = 2 ; // version of db
    public static final String TABLE_NAME = "mytable"; // name of the table
    public static final String COLUMN_ID = "id"; // column id
    public static final String COLUMN_PERSON_TITLE = "title";     //Block of personal columns
    public static final String COLUMN_PERSON_SUBTITLE = "subtitle";
    public static final String COLUMN_PERSON_IMAGE = "photoid";
    public static final String COLUMN_PERSON_ALL_IMAGES = "allImages";
    public static final String COLUMN_PERSON_DESCRIPTION = "description";
    public static final String COLUMN_PERSON_GROUPNAME = "groupName";
    private final Context myContext;
    private SQLiteDatabase myDatabase;


    String DBPATH; // way to BD
    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     * @param context
     */

  //  public DataBaseHelper(Context context) {
  //     super(context, DATABASE_NAME , null, DATABASE_VERSION);
   //     DBPATH = context.getApplicationInfo().dataDir + "/databases/";
  //      this.myContext = context;
   // }

    /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     * */

  /* public void checkAndCopyDatabase(){
        boolean dbExist = checkDatabase();
        if (dbExist){
            //ничего не делать - база уже есть
            Log.d("TAG","database already exist");
        }else {
            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();
        }
        try{
            copyDataBase();
        }catch (IOException e){
            e.printStackTrace();
            Log.d("TAG","error db");
        }
    }

    public void openDatabase(){
        String myPath = DBPATH + DATABASE_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     * @return true если существует, false если не существует
     *

    public boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DBPATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){
            //база еще не существует
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    public synchronized void close(){
        if(myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     * *

    public void copyDataBase() throws IOException{
        //Открываем локальную БД как входящий поток
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        //Путь ко вновь созданной БД
        String outFileName = DBPATH + DATABASE_NAME;

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public Cursor QueryData(String query){
        return  myDatabase.rawQuery(query,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    /*Query records, give options to filter results**
    public List<Army> mainList(String groupName) {
        String query = "";
        if (groupName != null){
            query = "SELECT * FROM " + TABLE_NAME + " WHERE groupName = " + "'"+groupName+"'";
        }else {
            query = "SELECT * FROM " + TABLE_NAME;
        }

        List<Army> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        Army mainList;

        if (cursor.moveToFirst()) {
            do {
                mainList = new Army();

                mainList.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                mainList.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_TITLE)));
                mainList.setSubtitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SUBTITLE)));
                mainList.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
                mainList.setGroupName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_GROUPNAME)));
                personLinkedList.add(mainList);
            } while (cursor.moveToNext());
        }
        return personLinkedList;
    }

    public Army exMainList(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE id="+ id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        Army exampleMainList = new Army();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            exampleMainList.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_TITLE)));
            exampleMainList.setSubtitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SUBTITLE)));
            exampleMainList.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
            exampleMainList.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_DESCRIPTION)));
            exampleMainList.setAllImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_ALL_IMAGES)));
        }
        return exampleMainList;
    }

}*/