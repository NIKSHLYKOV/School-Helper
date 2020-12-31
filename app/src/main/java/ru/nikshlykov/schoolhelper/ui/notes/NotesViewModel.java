package ru.nikshlykov.schoolhelper.ui.notes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.App;
import ru.nikshlykov.schoolhelper.db.NotesRepository;
import ru.nikshlykov.schoolhelper.db.entities.Note;

public class NotesViewModel extends AndroidViewModel {

    private static NotesRepository notesRepository;

    public NotesViewModel(Application application) {
        super(application);
        notesRepository = NotesRepository.getInstance(application);
    }

    public LiveData<List<Note>> getNotes() {
        long userId = ((App)getApplication()).getUser().id;
        return notesRepository.getLiveDataNotes(userId);
    }

    public void deleteNote(Note note){
        notesRepository.delete(note);
    }
}