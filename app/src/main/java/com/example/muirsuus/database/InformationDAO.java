package com.example.muirsuus.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InformationDAO {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(table_ex information);


    @Query("SELECT section FROM table_ex ")
    LiveData<List<String>> getSections();

    @Query("SELECT subsection FROM table_ex WHERE section = :section")
    LiveData<List<String>> getSubsections(String section);

    @Query("SELECT point FROM table_ex WHERE subsection = :subsection")
    LiveData<List<String>> getPoints(String subsection);

    @Query(" SELECT description FROM  table_ex  WHERE  point = :point")
    LiveData<List<String>> getDescriptionFromDB(String point);

    @Query(" SELECT tth  FROM  table_ex  WHERE  point = :point")
    LiveData<List<String>> getTTHFromDB(String point);


    @Delete
    void deleteFavourite(table_ex informationDB);


}
