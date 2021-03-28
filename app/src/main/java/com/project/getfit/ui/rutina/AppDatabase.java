package com.project.getfit.ui.rutina;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Rutina.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RutinaDao rutinaDao();
}
