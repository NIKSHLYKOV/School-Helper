package ru.nikshlykov.schoolhelper.ui.models;

import androidx.room.ColumnInfo;

public class Lesson {

    @ColumnInfo(name = "Id")
    public long id;

    @ColumnInfo(name = "SubjectName")
    public String subjectName;

    @ColumnInfo(name = "LessonTime")
    public String lessonTime;
}
