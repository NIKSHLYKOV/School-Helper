package ru.nikshlykov.schoolhelper.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.entities.Note;
import ru.nikshlykov.schoolhelper.db.daos.NoteDao;

public class NotesRepository {

    private static NotesRepository instance;

    private static final String LOG_TAG = NotesRepository.class.getCanonicalName();

    private final NoteDao noteDao;

    private NotesRepository(Context context) {
        noteDao = AppDatabase.getInstance(context).noteDao();
    }

    public static NotesRepository getInstance(Context context){
        if (instance == null){
            instance = new NotesRepository(context);
        }
        return instance;
    }

    public LiveData<List<Note>> getLiveDataNotes() {
        return noteDao.getLiveDataNotes();
    }

    public LiveData<Note> getLiveDataNoteById(long noteId) {
        return noteDao.getLiveDataNoteById(noteId);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public void insert(String name, String text) {
        Note newNote = new Note(name, text);
        new InsertNoteAsyncTask(noteDao).execute(newNote);
    }
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Long> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Long doInBackground(Note... notes) {
            long newNoteId = noteDao.getCount() + 1;
            Note note = notes[0];
            note.id = newNoteId;
            return noteDao.insert(note);
        }
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
}
