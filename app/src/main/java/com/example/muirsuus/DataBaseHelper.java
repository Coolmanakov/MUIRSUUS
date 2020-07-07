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
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "MURSY_YS.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 32;
    //-----------------names of columns in database------------------------------
    public static final String TABLE_NAME = "my_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PERSON_TITLE = "title";
    public static final String COLUMN_PERSON_TTH = "TTH";
    public static final String COLUMN_PERSON_SUBTITLE = "subtitle";
    public static final String COLUMN_PERSON_IMAGE = "photoid";
    public static final String COLUMN_PERSON_ALL_IMAGES = "allImages";
    public static final String COLUMN_PERSON_DESCRIPTION = "description";
    public static final String COLUMN_PERSON_GROUPNAME = "groupName";
    public static final String FILE_DIR ="AudioArmy";
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
            Log.d("TAG","database already exist");
        }else {
            this.getReadableDatabase();
        }
        try{
            copyDataBase();
        }catch (IOException e){
            e.printStackTrace();
            Log.d("TAG","error db");
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

        InputStream myInput = new FileInputStream(Environment.getExternalStorageDirectory()
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
    public Army exMainList(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE id = "+ id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        Army exampleMainList = new Army();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            exampleMainList.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_TITLE)));
            exampleMainList.setTTH(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_TTH)));
            exampleMainList.setSubtitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SUBTITLE)));
            exampleMainList.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
            exampleMainList.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_DESCRIPTION)));
            exampleMainList.setAllImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_ALL_IMAGES)));
            exampleMainList.setGroupName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_GROUPNAME)));

        }
        return exampleMainList;
    }
    public void addToFavoriteList(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLUMN_ID,id);
        db.insert(DataBaseHelper.TABLE_NAME_FAVORITE,null,contentValues);

        Log.d("mLog","add Id=" + id);
    }

    public List<Integer> createFavoriteList(){
        ArrayList<Integer> idList = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME_FAVORITE,null,null,null,null,null,null);
        if(cursor.moveToFirst()){

            do {
                int idIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_ID);
                Log.d("mLog","create Id=" + cursor.getInt(idIndex));
                idList.add(cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        }
        else
            Log.d("moving to next","without id" );

        cursor.close();
        return idList;

    }
    public void deleteTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DataBaseHelper.TABLE_NAME_FAVORITE,null,null);
        Log.d("mLog","Table has been deleted");
    }

    public  void deleteRow(int id){
        SQLiteDatabase mDataBase = this.getReadableDatabase();
        delCount = mDataBase.delete(DataBaseHelper.TABLE_NAME_FAVORITE, DataBaseHelper.COLUMN_ID + "=" + id, null);
        Log.d("mLog", "deleted rows count = " + delCount);
        mDataBase.close();
    }


}