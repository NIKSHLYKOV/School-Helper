package ru.nikshlykov.schoolhelper.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.App;
import ru.nikshlykov.schoolhelper.db.LessonsRepository;
import ru.nikshlykov.schoolhelper.db.entities.Lesson;
import ru.nikshlykov.schoolhelper.ui.models.SubjectsAndDayLessons;

public class EditDayScheduleViewModel extends AndroidViewModel implements LessonsRepository.OnSubjectsAndDayLessonsLoadedListener {

    private static LessonsRepository lessonsRepository;

    private MutableLiveData<SubjectsAndDayLessons> subjectsAndDayLessonsLiveData;

    private int dayOfWeek;

    public EditDayScheduleViewModel(Application application) {
        super(application);
        lessonsRepository = LessonsRepository.getInstance(application);
        subjectsAndDayLessonsLiveData = new MutableLiveData<>();
    }

    @Override
    public void onSubjectsAndDayLessonsLoaded(SubjectsAndDayLessons subjectsAndDayLessons) {
        subjectsAndDayLessonsLiveData.postValue(subjectsAndDayLessons);
    }

    public LiveData<SubjectsAndDayLessons> getSubjectsAndDayLessons() {
        long classId = ((App)getApplication()).getUser().classId;
        lessonsRepository.getSubjectsAndDayLessons(this, classId, dayOfWeek);
        return subjectsAndDayLessonsLiveData;
    }

    public void setDayOfWeek(int dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }

    public void updateLessons(Long[] newSubjectIds){
        List<Lesson> lessonsForUpdate = subjectsAndDayLessonsLiveData.getValue().dayLessons;
        if (lessonsForUpdate != null) {
            for (int i = 0; i < 8; i++) {
                lessonsForUpdate.get(i).subjectId = newSubjectIds[i];
            }
        }
        lessonsRepository.update(lessonsForUpdate);
    }
}