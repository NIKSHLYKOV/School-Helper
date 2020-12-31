package ru.nikshlykov.schoolhelper.ui.notes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.nikshlykov.schoolhelper.App;
import ru.nikshlykov.schoolhelper.db.NotesRepository;
import ru.nikshlykov.schoolhelper.db.entities.Note;

public class NoteViewModel extends AndroidViewModel {

    private final NotesRepository notesRepository;

    private LiveData<Note> noteLiveData;

    public NoteViewModel(Application application) {
        super(application);
        notesRepository = NotesRepository.getInstance(application);
        noteLiveData = new MutableLiveData<>();
    }

    public void setNoteLiveData(long noteId) {
        noteLiveData = notesRepository.getLiveDataNoteById(noteId);
    }

    public LiveData<Note> getNoteLiveData() {
        return noteLiveData;
    }

    public void insertOrUpdateNote(String noteName, String noteText) {
        Note note = this.noteLiveData.getValue();
        if (note != null) {
            note.name = noteName;
            note.text = noteText;
            notesRepository.update(note);
        } else {
            long userId = ((App)getApplication()).getUser().id;
            notesRepository.insert(noteName, noteText, userId);
        }
    }
}

