package com.example.projetfinal;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * A Room database using the Dao (Data access object) UserDao
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}