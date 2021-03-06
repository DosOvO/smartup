package de.unibremen.smartup.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import de.unibremen.smartup.model.Alarm;

@Dao
public interface AlarmDAO {

    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("SELECT * FROM alarms WHERE dbId = :dbId")
    Alarm findByDbId(int dbId);

    @Query("DELETE FROM alarms")
    void deleteAll();

    @Query("SELECT * FROM alarms")
    LiveData<List<Alarm>> getAllAlarms();
}
