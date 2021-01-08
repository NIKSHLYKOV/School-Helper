package ru.nikshlykov.schoolhelper.db;

import android.content.Context;
import android.os.AsyncTask;

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

    public LiveData<List<Lesson>> getLiveDataLessons(long classId, int dayOfWeek) {
        return lessonDao.getLiveDataLessons(classId, dayOfWeek);
    }

    public LiveData<ru.nikshlykov.schoolhelper.db.entities.Lesson> getLiveDataNoteById(long lessonId) {
        return lessonDao.getLiveDataLessonById(lessonId);
    }

    public void update(ru.nikshlykov.schoolhelper.db.entities.Lesson lesson) {
        new UpdateLessonAsyncTask(lessonDao).execute(lesson);
    }
    private static class UpdateLessonAsyncTask extends AsyncTask<ru.nikshlykov.schoolhelper.db.entities.Lesson, Void, Void> {
        private LessonDao lessonDao;

        private UpdateLessonAsyncTask(LessonDao lessonDao) {
            this.lessonDao = lessonDao;
        }

        @Override
        protected Void doInBackground(ru.nikshlykov.schoolhelper.db.entities.Lesson... lessons) {
            lessonDao.update(lessons[0]);
            return null;
        }
    }
}
