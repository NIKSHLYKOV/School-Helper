package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LessonNumbers")
public class LessonNumber {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Time")
    public String time;
}
