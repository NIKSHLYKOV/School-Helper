package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Users",
        foreignKeys = @ForeignKey(
                entity = Pupil.class,
                parentColumns = "Id",
                childColumns = "PupilId",
                onDelete = CASCADE,
                onUpdate = CASCADE))
public class User {

    public User(long id, @NotNull String nickname, @NotNull String password, long pupilId){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.pupilId = pupilId;
    }

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

    @NonNull
    @ColumnInfo(name = "ClassId")
    public long classId;
}
