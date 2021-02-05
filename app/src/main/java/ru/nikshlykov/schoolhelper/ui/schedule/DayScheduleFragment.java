package ru.nikshlykov.schoolhelper.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;
import ru.nikshlykov.schoolhelper.ui.adapters.LessonsRecyclerViewAdapter;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;

public class DayScheduleFragment extends Fragment {
    public static final String ARG_DAY_OF_WEEK = "ARG_DAY_OF_WEEK";

    private LessonsRecyclerViewAdapter adapter;
    private RecyclerView lessonsRecyclerView;
    private CardView lessonsCardView;
    private TextView lessonsAreNotExistTextView;
    private FloatingActionButton editDayScheduleFAB;

    private DayScheduleViewModel dayScheduleViewModel;

    private int dayOfWeek;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    public static DayScheduleFragment newInstance(int dayOfWeek) {
        Log.i(DayScheduleFragment.class.getCanonicalName(), "newInstance()");
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_OF_WEEK, dayOfWeek);
        DayScheduleFragment fragment = new DayScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener =
                    (OnFragmentInteractionListener) getActivity();
        } else {
            throw new RuntimeException(getActivity().toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            dayOfWeek = args.getInt(ARG_DAY_OF_WEEK);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dayScheduleViewModel =
                new ViewModelProvider(this).get(DayScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_day_schedule, container, false);

        lessonsRecyclerView = root.findViewById(R.id.fragment_day_schedule___recycler_view___lessons);
        lessonsCardView = root.findViewById(R.id.fragment_day_schedule___card_view___lessons);
        lessonsAreNotExistTextView = root.findViewById(R.id.fragment_day_schedule___text_view___there_are_not_lessons);
        editDayScheduleFAB = root.findViewById(R.id.fragment_day_schedule___fab___edit);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(DayScheduleFragment.class.getCanonicalName(), "onViewCreated()");
        initRecyclerViewWithAdapter();

        initEditDayScheduleFAB();
    }

    private void initRecyclerViewWithAdapter() {
        adapter = new LessonsRecyclerViewAdapter();
        adapter.setOnEntryClickListener((view, position) -> {
            final Lesson currentLesson = adapter.getLessonAt(position);
            if (!currentLesson.subjectName.equals("-")) {
                NavDirections navDirections = WeekScheduleFragmentDirections
                        .actionNavScheduleToHomeworkFragment()
                        .setLessonId(currentLesson.id);
                onFragmentInteractionListener.onFragmentInteraction(navDirections);
            }
        });

        lessonsRecyclerView.setAdapter(adapter);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dayScheduleViewModel.getLessons(dayOfWeek).observe(getViewLifecycleOwner(), lessons -> {
            if (lessons == null){
                showLessonsAreNotExistInfo();
                return;
            }

            if (lessons.size() == 0){
                showLessonsAreNotExistInfo();
                return;
            }

            boolean lessonsAreNotExist = true;
            for (Lesson lesson: lessons){
                if (!lesson.subjectName.equals("-")){
                    lessonsAreNotExist = false;
                    break;
                }
            }

            if (lessonsAreNotExist) {
                showLessonsAreNotExistInfo();
                return;
            }

            lessonsCardView.setVisibility(View.VISIBLE);
            lessonsAreNotExistTextView.setVisibility(View.GONE);
            adapter.setLessons(lessons);

        });
    }

    private void showLessonsAreNotExistInfo(){
        lessonsCardView.setVisibility(View.GONE);
        lessonsAreNotExistTextView.setVisibility(View.VISIBLE);
    }

    private void initEditDayScheduleFAB() {
        editDayScheduleFAB.setOnClickListener(view -> {
            NavDirections navDirections = WeekScheduleFragmentDirections.actionNavScheduleToEditDayScheduleFragment(dayOfWeek);
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });
    }
}