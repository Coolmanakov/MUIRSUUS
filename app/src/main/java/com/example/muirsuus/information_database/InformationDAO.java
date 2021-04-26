package com.example.muirsuus.information_database;

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
    LiveData<SectionAndSubsection> getSectionAndSubsection(String section);

    @Transaction
    @Query("SELECT * FROM section WHERE section =:section")
    LiveData<SectionAndPresubsection> getSectionAndPresubsection(String section);

    @Transaction
    @Query("SELECT * FROM presubsection WHERE presubsection =:presubsection")
    LiveData<PresubectionAndSubsection> getPreSubsectionAndSubsection(String presubsection);

    @Transaction
    @Query("SELECT * FROM subsection WHERE subsection =:subsection")
    LiveData<SubsectionAndPoint> getSubsectionAndPoint(String subsection);

    @Transaction
    @Query("SELECT * FROM presubsection WHERE presubsection =:presubsection")
    LiveData<PreSubsectionAndPoint> getPreSubsectionAndPoint(String presubsection);

    @Transaction
    @Query("SELECT * FROM point WHERE point =:point")
    LiveData<PointAndInformation> getPointAndInformation(String point);

    @Transaction
    @Query("SELECT * FROM point WHERE point = :point")
    point getPoint(String point);

    @Transaction
    @Query("SELECT book_photo FROM books WHERE book_name = :bookName")
    String getBookPhoto(String bookName);

    @Transaction
    @Query("SELECT *  FROM information WHERE informId = :id")
    LiveData<information> getTableInfo(Integer id);


}
