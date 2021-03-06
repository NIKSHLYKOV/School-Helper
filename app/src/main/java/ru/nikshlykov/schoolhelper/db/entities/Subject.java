package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Subjects")
public class Subject {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Name")
    public String name;
}
