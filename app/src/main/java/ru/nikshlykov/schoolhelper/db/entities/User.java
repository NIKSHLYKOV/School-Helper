package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Users",
        foreignKeys = @ForeignKey(
                entity = Pupil.class,
                parentColumns = "Id",
                childColumns = "PupilId",
                onDelete = CASCADE,
                onUpdate = CASCADE))
public class User {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Nickname")
    public String nickname;

    @NonNull
    @ColumnInfo(name = "Password")
    public String password;

    @NonNull
    @ColumnInfo(name = "PupilId")
    public long pupilId;
}
