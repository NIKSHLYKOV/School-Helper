package ru.nikshlykov.schoolhelper.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Lessons",
        foreignKeys = {@ForeignKey(entity = Subject.class, parentColumns = "Id",  childColumns = "SubjectId",  onDelete = CASCADE,  onUpdate = CASCADE),
                @ForeignKey(entity = Class.class, parentColumns = "Id",  childColumns = "ClassId",  onDelete = CASCADE,  onUpdate = CASCADE),
                @ForeignKey(entity = DayOfWeek.class, parentColumns = "Id",  childColumns = "DayOfWeekId",  onDelete = CASCADE,  onUpdate = CASCADE),
                @ForeignKey(entity = LessonNumber.class, parentColumns = "Id",  childColumns = "LessonNumberId",  onDelete = CASCADE,  onUpdate = CASCADE)})
public class Lesson {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Id")
    public long id;

    @NonNull
    @ColumnInfo(name = "SubjectId")
    public long subjectId;

    @NonNull
    @ColumnInfo(name = "ClassId")
    public long classId;

    @NonNull
    @ColumnInfo(name = "DayOfWeekId")
    public long dayOfWeekId;

    @NonNull
    @ColumnInfo(name = "LessonNumberId")
    public long lessonNumberId;

    @ColumnInfo(name = "Homework")
    public String homework;
}
