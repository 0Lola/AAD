package com.example.zxa01.fundamentals.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.zxa01.fundamentals.entity.Phone;

import java.util.List;

@Dao
public interface PhoneDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertPhone(Phone phone);

    @Update
    public void updatePhone(Phone phone);

    @Delete
    public void deletePhone(Phone phone);

    @Query("SELECT * FROM PHONE")
    public List<Phone> getAllPhone();

    @Query("SELECT * FROM PHONE WHERE PHONE.id = :id")
    public Phone getNewPhone(long id);

}
