package com.example.muirsuus.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface InformationDAO {
    @Query("SELECT * FROM section")
    LiveData<List<section>> getSections();

    @Transaction
    @Query("SELECT * FROM section WHERE section =:section")
    LiveData<List<SectionAndSubsection>> getSectionAndSubsection(String section);

    @Transaction
    @Query("SELECT * FROM subsection WHERE subsection =:subsection")
    LiveData<List<SubsectionAndPoint>> getSubsectionAndPoint(String subsection);

    @Transaction
    @Query("SELECT * FROM point WHERE point =:point")
    LiveData<List<PointAndInformation>> getPointAndInformation(String point);


}
