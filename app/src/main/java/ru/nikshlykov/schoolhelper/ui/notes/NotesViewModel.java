package ru.nikshlykov.schoolhelper.ui.notes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.NotesRepository;
import ru.nikshlykov.schoolhelper.db.entities.Note;

public class NotesViewModel extends AndroidViewModel {

    private LiveData<List<Note>> notesMutableLiveData;

    private static NotesRepository notesRepository;

    public NotesViewModel(Application application) {
        super(application);
        notesRepository = NotesRepository.getInstance(application);
        notesMutableLiveData = new MutableLiveData<>();
        notesMutableLiveData = notesRepository.getLiveDataNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notesMutableLiveData;
    }

    public void deleteNote(Note note){
        notesRepository.delete(note);
    }
}