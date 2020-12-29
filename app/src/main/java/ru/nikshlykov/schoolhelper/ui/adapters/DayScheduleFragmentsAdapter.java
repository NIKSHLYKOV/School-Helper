package ru.nikshlykov.schoolhelper.ui.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.nikshlykov.schoolhelper.ui.schedule.DayScheduleFragment;

public class DayScheduleFragmentsAdapter extends FragmentPagerAdapter {

    final int PAGES_COUNT = 5;
    private String tabTitles[] = new String[] { "Пн", "Вт", "Ср", "Чт", "Пт" };

    public DayScheduleFragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return DayScheduleFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
