package ru.nikshlykov.schoolhelper.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.nikshlykov.schoolhelper.db.daos.LessonDao;
import ru.nikshlykov.schoolhelper.db.daos.UserDao;
import ru.nikshlykov.schoolhelper.db.entities.Class;
import ru.nikshlykov.schoolhelper.db.entities.DayOfWeek;
import ru.nikshlykov.schoolhelper.db.entities.Lesson;
import ru.nikshlykov.schoolhelper.db.entities.LessonNumber;
import ru.nikshlykov.schoolhelper.db.entities.Pupil;
import ru.nikshlykov.schoolhelper.db.entities.Subject;
import ru.nikshlykov.schoolhelper.db.entities.User;
import ru.nikshlykov.schoolhelper.db.note.Note;
import ru.nikshlykov.schoolhelper.db.note.NoteDao;

@Database(entities = {Note.class, Class.class, DayOfWeek.class, Lesson.class,
LessonNumber.class, Pupil.class, Subject.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static String DATABASE_NAME = "schoolhelper_main.db";
    private static String DATABASE_DIR = "schoolhelper_main.db";

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room
                    .databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .createFromAsset(DATABASE_DIR)
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();
    public abstract LessonDao lessonDao();
    public abstract UserDao userDao();
}
