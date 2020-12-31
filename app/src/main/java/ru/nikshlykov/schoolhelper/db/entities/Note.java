package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Notes",
        foreignKeys = {@ForeignKey(
                entity = User.class,
                parentColumns = "Id",
                childColumns = "UserId",
                onDelete = CASCADE,
                onUpdate = CASCADE)})
public class Note{

    public Note(@NonNull String name, @NonNull String text, long userId) {
        this.id = 0L;
        this.name = name;
        this.text = text;
        this.userId = userId;
    }

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Name")
    public String name;

    @NonNull
    @ColumnInfo(name = "Text")
    public String text;

    @NonNull
    @ColumnInfo(name = "UserId")
    public long userId;
}
