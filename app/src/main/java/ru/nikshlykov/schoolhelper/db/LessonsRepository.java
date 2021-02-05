package ru.nikshlykov.schoolhelper.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.nikshlykov.schoolhelper.db.daos.LessonDao;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;
import ru.nikshlykov.schoolhelper.ui.models.SubjectsAndDayLessons;

public class LessonsRepository {

    private static LessonsRepository instance;

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

    public void update(List<ru.nikshlykov.schoolhelper.db.entities.Lesson> lessons) {
        new UpdateLessonsAsyncTask(lessonDao).execute(lessons);
    }
    private static class UpdateLessonsAsyncTask extends AsyncTask<List<ru.nikshlykov.schoolhelper.db.entities.Lesson>, Void, Void> {
        private LessonDao lessonDao;

        private UpdateLessonsAsyncTask(LessonDao lessonDao) {
            this.lessonDao = lessonDao;
        }

        @Override
        protected Void doInBackground(List<ru.nikshlykov.schoolhelper.db.entities.Lesson>... lessons) {
            lessonDao.update(lessons[0]);
            return null;
        }
    }

    public void getSubjectsAndDayLessons(OnSubjectsAndDayLessonsLoadedListener listener, long classId, int dayOfWeek) {
        GetSubjectsAndDayLessonsAsyncTask task = new GetSubjectsAndDayLessonsAsyncTask(lessonDao, listener, classId);
        task.execute(dayOfWeek);
    }
    public interface OnSubjectsAndDayLessonsLoadedListener {
        void onSubjectsAndDayLessonsLoaded(SubjectsAndDayLessons subjectsAndDayLessons);
    }
    private static class GetSubjectsAndDayLessonsAsyncTask extends AsyncTask<Integer, Void, SubjectsAndDayLessons> {
        private LessonDao lessonDao;
        private WeakReference<OnSubjectsAndDayLessonsLoadedListener> listener;
        private long classId;

        private GetSubjectsAndDayLessonsAsyncTask(LessonDao lessonDao, OnSubjectsAndDayLessonsLoadedListener listener, long classId) {
            this.lessonDao = lessonDao;
            this.listener = new WeakReference<>(listener);
            this.classId = classId;
        }

        @Override
        protected SubjectsAndDayLessons doInBackground(Integer... integers) {
            int dayOfWeek = integers[0];

            SubjectsAndDayLessons subjectsAndDayLessons = new SubjectsAndDayLessons();
            subjectsAndDayLessons.subjects = lessonDao.getLiveDataSubjects();
            subjectsAndDayLessons.dayLessons = lessonDao.getLessonsByDay(classId, dayOfWeek);

            return subjectsAndDayLessons;
        }

        @Override
        protected void onPostExecute(SubjectsAndDayLessons subjectsAndDayLessons) {
            super.onPostExecute(subjectsAndDayLessons);
            OnSubjectsAndDayLessonsLoadedListener listener = this.listener.get();
            if (listener != null) {
                listener.onSubjectsAndDayLessonsLoaded(subjectsAndDayLessons);
            }
        }
    }
}
