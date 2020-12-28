package ru.nikshlykov.schoolhelper.ui.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.adapters.LessonsRecyclerViewAdapter;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;
import ru.nikshlykov.schoolhelper.ui.notes.NotesFragment;

public class ScheduleFragment extends Fragment {

    private LessonsRecyclerViewAdapter adapter;
    private RecyclerView lessonsRecyclerView;

    private ScheduleViewModel scheduleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        lessonsRecyclerView = root.findViewById(R.id.fragment_schedule___recycler_view___lessons);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerViewWithAdapter();
    }

    private void initRecyclerViewWithAdapter() {
        adapter = new LessonsRecyclerViewAdapter();

        lessonsRecyclerView.setAdapter(adapter);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        scheduleViewModel.getLessons().observe(getViewLifecycleOwner(), lessons -> {
            if (lessons != null) {
                for (Lesson lesson : lessons) {
                    Log.d(NotesFragment.class.getCanonicalName(), lesson.id + " " + lesson.subjectName);
                }
                adapter.setLessons(lessons);
            }
        });
    }
}