package ru.nikshlykov.schoolhelper.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.LessonsRepository;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;

public class DayScheduleViewModel extends AndroidViewModel {

    private static LessonsRepository lessonsRepository;

    public DayScheduleViewModel(Application application) {
        super(application);
        lessonsRepository = LessonsRepository.getInstance(application);
    }

    public LiveData<List<Lesson>> getLessons(int dayOfWeek) {
        return lessonsRepository.getLiveDataLessons(dayOfWeek);
    }
}