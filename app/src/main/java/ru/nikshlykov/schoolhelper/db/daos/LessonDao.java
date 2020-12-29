package ru.nikshlykov.schoolhelper.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.nikshlykov.schoolhelper.ui.models.Lesson;

@Dao
public interface LessonDao {

    @Query("SELECT\n" +
            "l.Id Id, \n" +
            "s.Name SubjectName, \n" +
            "ln.Time LessonTime\n" +
            "FROM Lessons l\n" +
            "INNER JOIN Subjects s ON s.Id == l.SubjectId\n" +
            "INNER JOIN Classes c ON c.Id == l.ClassId\n" +
            "INNER JOIN DaysOfWeek dow ON dow.Id == l.DayOfWeekId\n" +
            "INNER JOIN LessonNumbers ln ON ln.Id == l.LessonNumberId\n" +
            "WHERE c.Id == 7 AND dow.Id == :dayOfWeek")
    LiveData<List<Lesson>> getLiveDataLessons(int dayOfWeek);
}
