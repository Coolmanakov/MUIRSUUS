package com.example.muirsuus;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.muirsuus.classes.Army;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "MURSY_YS.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 34;
    public static final String FILE_DIR ="AudioArmy";
    //-----------------names of columns in database------------------------------
    public static final String TABLE_NAME = "table_ex";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FAVORITE_ID = "id";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_SUBSECTION= "subsection";
    public static final String COLUMN_TTH = "tth";
    public static final String COLUMN_PHOTO_ID = "photo_ids";
    public static final String COLUMN_PERSON_DESCRIPTION = "description";
    public static final String COLUMN_POINT= "point";
    public static final String COLUMN_PHOTO_SUBSECTION= "subsection_photo";
    public static final String COLUMN_PHOTO_POINT= "point_photo";


    public  static final String COLUMN_PERSON_IS_FAVORITE="IsFavorite";
    //----------------------------------------------------------------------
    public static final String TABLE_NAME_FAVORITE = "favorite";


    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;
    int delCount = 0;

    public DataBaseHelper(Context context) { //конструктор класса DataBaseHelperБ родитель - SQLiteDatabase
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.mContext = context;
    }

    public void checkAndCopyDatabase(){
        boolean dbExist = checkDataBase();
        if (dbExist){
            Log.d("mLog","database already exist");
        }else {
            this.getReadableDatabase();
        }
        try{
            copyDataBase();
        }catch (IOException e){
            e.printStackTrace();
            Log.d("mLog","error db");
        }
    }

    public void updateDataBase() throws IOException { // проверяем обновлена ли бд
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {  // создаём файл, в котором будет храниться бд
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void copyDataBase() throws IOException{

        InputStream myInput = new FileInputStream(Environment.getExternalStorageDirectory()  //убрать метод  getExternalStorageDirectory() в api 29 его нет
                + File.separator + FILE_DIR
                + File.separator + DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
    //получаем описание из БД
    public Army getDescriptionFromDB(String point){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")Cursor cursor = db.rawQuery(" SELECT " + COLUMN_PERSON_DESCRIPTION  +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_POINT + " LIKE ?  ", new String[]   {point});
        Army description = new Army();
        if(cursor != null && cursor.moveToFirst()){
            do{
                description.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_DESCRIPTION)));
                Log.d("mLog", "find Description");
            }while (cursor.moveToNext());
        }
        else{
            Log.d("mLog", "Cursor finding description null");
        }

        cursor.close();
        return description;
    }

    public Army getTTHFromDB(String point){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")Cursor cursor = db.rawQuery(" SELECT " + COLUMN_TTH  +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_POINT + " LIKE ?  ", new String[]   {point});
        Army tth = new Army();
        if(cursor != null && cursor.moveToFirst()){
            do{
                tth.setTTH(cursor.getString(cursor.getColumnIndex(COLUMN_TTH)));
                Log.d("mLog", "find tth");
            }while (cursor.moveToNext());
        }
        else{
            Log.d("mLog", "Cursor finding tth null");
        }

        cursor.close();
        return tth;
    }


    // метод, в котором реализовано добавление в список "Избранное"
    public void addToFavoriteList(String text_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLUMN_FAVORITE_ID, text_id);
        db.insert(DataBaseHelper.TABLE_NAME_FAVORITE,null,contentValues);

        Log.d("mLog","add  text Id=" + text_id);
    }
// метод, в котором реализовано создание списка избранного
    public ArrayList<String> createFavoriteList(){
        ArrayList<String> idList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")Cursor cursor = db.rawQuery(" SELECT " + COLUMN_FAVORITE_ID + " FROM " + TABLE_NAME_FAVORITE,null);
        if(cursor != null && cursor.moveToFirst()){
            do {
                idList.add(cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_ID)));
            } while (cursor.moveToNext());
        }
        else
            Log.d("mLog","without  favorite id" );
        cursor.close();
        return idList;

    }
    // метод, очищающий таблицу от  данных
    public void deleteTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DataBaseHelper.TABLE_NAME_FAVORITE,null,null);
        Log.d("mLog","Table has been deleted");
    }
// метод, удаляющий строчку из "Избранного" по id
    public  void deleteRow(int id){
        SQLiteDatabase mDataBase = this.getReadableDatabase();
        delCount = mDataBase.delete(DataBaseHelper.TABLE_NAME_FAVORITE, DataBaseHelper.COLUMN_ID + " = " + id, null);
        Log.d("mLog", "deleted rows count = " + delCount);
        mDataBase.close();
    }
//метод получения названий картинок из БД для subsection
    public Army get_subsection_photo(String subsection){
        Army photo = new Army();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")Cursor cursor = db.rawQuery(" SELECT " + COLUMN_PHOTO_SUBSECTION +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_SUBSECTION + " LIKE ?  ", new String[]   {subsection});
        if(cursor != null && cursor.moveToFirst()){
            do {
                if(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_SUBSECTION)) != null){
                photo.set_photo_subsection(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_SUBSECTION)));
                }

            } while (cursor.moveToNext());
        }
        else
            Log.d("mLog","did not find photo for subsection" );
        cursor.close();
        return photo;
    }
//метод получения названий картинок из БД для subsection
    public Army get_point_photo(String point){
        Army photo = new Army();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")Cursor cursor = db.rawQuery(" SELECT " + COLUMN_PHOTO_POINT +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_POINT + " LIKE ?  ", new String[]   {point});
        if(cursor != null && cursor.moveToFirst()){
            do {
                if(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_POINT)) != null){
                    photo.set_photo_point(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_POINT)));
                }

            } while (cursor.moveToNext());
        }
        else
            Log.d("mLog","did not find photo for point" );
        cursor.close();
        return photo;
    }



// словарь  из названий   элементов subsection-point
    public HashMap get_objects_from_DB(String section){
        Army army_DBobject = new Army(section,"","");

        HashMap<String, List<String>> map_of_DBobjects = new HashMap<>();

        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")Cursor cursor = db.rawQuery(" SELECT " + COLUMN_SUBSECTION +"," + COLUMN_POINT +" FROM " + TABLE_NAME + " WHERE " + COLUMN_SECTION + " LIKE ?  ", new String[]   {section});


        if(cursor != null && cursor.moveToFirst()){
            do{
                army_DBobject = new Army(section,"","");
                army_DBobject.setSubsection(cursor.getString(cursor.getColumnIndex(COLUMN_SUBSECTION)));
                army_DBobject.setPoint(cursor.getString(cursor.getColumnIndex(COLUMN_POINT)));

                if(map_of_DBobjects.containsKey(army_DBobject.getSubsection())){ //если существует ключ в словаре просто добавим в лист значение по ключу
                    map_of_DBobjects.get(army_DBobject.getSubsection()).add(army_DBobject.getPoint()); //словарь.ключ().добавить(значение)
                }
                else{
                    list = new ArrayList<>(); //если нет такого ключа, создадим новый список и засунем в него значение и всё поместим в словарь
                    list.add(army_DBobject.getPoint());
                    map_of_DBobjects.put(army_DBobject.getSubsection(), list);
                }


                Log.d("mLog", "map =" + map_of_DBobjects.values());
            }while (cursor.moveToNext());
        }
        else{
            Log.d("mLog", "cursor null or not moving to first");
        }
        cursor.close();
        return map_of_DBobjects;
    }





}