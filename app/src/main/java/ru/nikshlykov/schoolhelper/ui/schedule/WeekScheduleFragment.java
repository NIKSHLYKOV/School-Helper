package ru.nikshlykov.schoolhelper.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.adapters.DayScheduleFragmentsAdapter;

public class WeekScheduleFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_week_schedule, container, false);

        ViewPager viewPager = root.findViewById(R.id.fragment_week_schedule___view_pager);
        viewPager.setAdapter(
                new DayScheduleFragmentsAdapter(getActivity().getSupportFragmentManager()));

        TabLayout tabLayout = root.findViewById(R.id.fragment_week_schedule___tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }
}