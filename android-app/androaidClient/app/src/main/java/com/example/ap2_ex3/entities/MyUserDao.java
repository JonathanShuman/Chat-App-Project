package com.example.ap2_ex3.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyUserDao {

    @Query("SELECT * FROM MyUser")
    List<MyUser> index();

    @Query("SELECT * FROM MyUser WHERE id = :id")
    MyUser get(int id);

    @Query("SELECT * FROM MyUser WHERE displayName = :dispName")
    MyUser getName(String dispName);

    @Query("SELECT * FROM MyUser WHERE displayName = :dispName")
    MyUser getPic(String dispName);

    @Query("DELETE FROM MyUser")
    void deleteAll();

    @Insert
    void insert(MyUser... myUsers);

    @Update
    void update(MyUser... myUsers);

    @Delete
    void delete(MyUser... myUsers);
}
