package com.example.zxa01.fundamentals.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.zxa01.fundamentals.DAO.PhoneDAO;
import com.example.zxa01.fundamentals.entity.Phone;


@Database(entities = {Phone.class}, version = 1)

public abstract class ConnectDatabase extends RoomDatabase {
    public static final String DBNAME = "PHONE";
    private static ConnectDatabase INSTANCE;

    public abstract PhoneDAO phoneDao();

    public static ConnectDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room
                    .databaseBuilder(context,
                            ConnectDatabase.class,
                            ConnectDatabase.DBNAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destoryInstance(){
        if (INSTANCE != null)
            INSTANCE.close();
        INSTANCE = null;
    }

}