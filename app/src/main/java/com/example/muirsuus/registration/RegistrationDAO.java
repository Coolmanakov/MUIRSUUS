package com.example.muirsuus.registration;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface RegistrationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(privateInfo info);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewUser(privateInfo info);

    @Transaction
    @Query(" SELECT password FROM privateInfo WHERE name =:name")
    String getUsetPasword(String name);

    @Transaction
    @Query(" SELECT * FROM privateInfo")
    List<privateInfo> all();

    @Transaction
    @Query(" SELECT * FROM privateInfo WHERE name = :name")
    List<privateInfo> getUsersData(String name);

    @Transaction
    @Query(" SELECT password FROM privateInfo WHERE name = :name")
    String userPassword(String name);

    @Delete
    void delete(List<privateInfo> privateInfos);

    @Delete
    void deleteUser(privateInfo user);


}
