package ru.nikshlykov.schoolhelper.db.note;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Note{

    public Note(@NonNull String name, String text) {
        this.id = 0L;
        this.name = name;
        this.text = text;
    }

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Text")
    public String text;
}
