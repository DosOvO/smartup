package de.unibremen.smartup.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "alarms")
public class Alarm implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int dbId;
    private int hour;
    private int minute;
    private boolean active;

    @Ignore
    public Alarm() {}

    public Alarm(int hour, int minute, boolean active) {
        this.hour = hour;
        this.minute = minute;
        this.active = active;
    }

}
