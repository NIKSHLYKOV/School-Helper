package ru.nikshlykov.schoolhelper.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.nikshlykov.schoolhelper.db.LessonsRepository;
import ru.nikshlykov.schoolhelper.db.entities.Lesson;

public class HomeworkViewModel extends AndroidViewModel {

    private final LessonsRepository lessonsRepository;

    private LiveData<Lesson> lessonLiveData;

    public HomeworkViewModel(Application application) {
        super(application);
        lessonsRepository = LessonsRepository.getInstance(application);
        lessonLiveData = new MutableLiveData<>();
    }

    public void setLessonLiveData(long lessonId) {
        lessonLiveData = lessonsRepository.getLiveDataNoteById(lessonId);
    }

    public LiveData<Lesson> getLessonLiveData() {
        return lessonLiveData;
    }

    public void updateHomeWork(String homework) {
        Lesson lesson = this.lessonLiveData.getValue();
        lesson.homework = homework;
        lessonsRepository.update(lesson);
    }
}

