package ru.nikshlykov.schoolhelper;

import android.app.Application;

import ru.nikshlykov.schoolhelper.db.entities.User;

public class App extends Application {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
