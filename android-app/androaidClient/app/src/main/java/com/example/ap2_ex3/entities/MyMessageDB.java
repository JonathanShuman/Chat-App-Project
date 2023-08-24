package com.example.ap2_ex3.entities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyMessage.class}, version = 1)

public abstract class MyMessageDB extends RoomDatabase {
    public abstract MyMessageDao myMessageDao();
}
