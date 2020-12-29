package ru.nikshlykov.schoolhelper.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.daos.LessonDao;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;

public class LessonsRepository {

    private static LessonsRepository instance;

    private static final String LOG_TAG = LessonsRepository.class.getCanonicalName();

    private final LessonDao lessonDao;

    private LessonsRepository(Context context) {
        lessonDao = AppDatabase.getInstance(context).lessonDao();
    }

    public static LessonsRepository getInstance(Context context){
        if (instance == null){
            instance = new LessonsRepository(context);
        }
        return instance;
    }

    public LiveData<List<Lesson>> getLiveDataLessons(int dayOfWeek) {
        return lessonDao.getLiveDataLessons(dayOfWeek);
    }
}
