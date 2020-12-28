package ru.nikshlykov.schoolhelper.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.LessonsRepository;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;

public class ScheduleViewModel extends AndroidViewModel {

    private LiveData<List<Lesson>> lessonsLiveData;

    private static LessonsRepository lessonsRepository;

    public ScheduleViewModel(Application application) {
        super(application);
        lessonsRepository = LessonsRepository.getInstance(application);
        lessonsLiveData = new MutableLiveData<>();
        lessonsLiveData = lessonsRepository.getLiveDataLessons();
    }

    public LiveData<List<Lesson>> getLessons() {
        return lessonsLiveData;
    }
}