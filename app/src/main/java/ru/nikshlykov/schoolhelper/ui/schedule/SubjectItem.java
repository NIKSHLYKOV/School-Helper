package ru.nikshlykov.schoolhelper.ui.schedule;

public class SubjectItem {
    public long id;
    public String name;

    public SubjectItem(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
