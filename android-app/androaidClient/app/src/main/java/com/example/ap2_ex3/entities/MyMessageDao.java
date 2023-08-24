package com.example.ap2_ex3.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface MyMessageDao {

    @Query("SELECT * FROM MyMessage")
    List<MyMessage> index();

    //get id and return contact.
    @Query("SELECT * FROM MyMessage WHERE id = :id")
    MyMessage get(int id);

    @Query("DELETE FROM MyMessage")
    void deleteAll();

    @Insert
    void insert(MyMessage... myMessages);

    @Update
    void update(MyMessage... myMessages);

    @Delete
    void delete(MyMessage... myMessages);


}
