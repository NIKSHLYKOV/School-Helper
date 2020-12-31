package ru.nikshlykov.schoolhelper.db.daos;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.nikshlykov.schoolhelper.db.entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Users")
    List<User> getUsers();
}
