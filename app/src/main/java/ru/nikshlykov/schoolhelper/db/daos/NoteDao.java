package ru.nikshlykov.schoolhelper.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.entities.Note;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Note note);

    @Update
    int update(Note note);

    @Delete
    int delete(Note note);

    @Query("SELECT * FROM Notes WHERE UserId == :userId")
    LiveData<List<Note>> getLiveDataNotes(long userId);

    @Query("SELECT * FROM Notes WHERE Id == :noteId")
    LiveData<Note> getLiveDataNoteById(long noteId);

    @Query("SELECT max(Id) FROM Notes")
    int getMaxId();
}
