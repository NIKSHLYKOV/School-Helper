package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Pupils",
        foreignKeys = @ForeignKey(
                entity = Class.class,
                parentColumns = "Id",
                childColumns = "ClassId",
                onDelete = CASCADE,
                onUpdate = CASCADE))
public class Pupil {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Name")
    public String name;

    @NonNull
    @ColumnInfo(name = "Surname")
    public String surname;

    @NonNull
    @ColumnInfo(name = "ClassId")
    public long classId;
}
