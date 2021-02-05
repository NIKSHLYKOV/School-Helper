package ru.nikshlykov.schoolhelper.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.db.entities.Lesson;
import ru.nikshlykov.schoolhelper.db.entities.Subject;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;

public class EditDayScheduleFragment extends Fragment {

    private List<Spinner> lessonsSpinners;
    private MaterialButton saveButton;

    private EditDayScheduleViewModel editDayScheduleViewModel;

    private OnFragmentInteractionListener onFragmentInteractionListener;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editDayScheduleViewModel =
                new ViewModelProvider(this).get(EditDayScheduleViewModel.class);
        Bundle extras = getArguments();
        if (extras != null) {
            int dayOfWeek = EditDayScheduleFragmentArgs.fromBundle(extras).getDayOfWeek();
            if (dayOfWeek >= 1 && dayOfWeek <= 6) {
                editDayScheduleViewModel.setDayOfWeek(dayOfWeek);
            } else {
                Toast.makeText(getContext(), "Произошла ошибка!", Toast.LENGTH_SHORT).show();

                NavDirections navDirections = EditDayScheduleFragmentDirections.actionEditDayScheduleFragmentToNavSchedule();
                onFragmentInteractionListener.onFragmentInteraction(navDirections);
            }
        }

        View root = inflater.inflate(R.layout.fragment_edit_day_schedule, container, false);

        Spinner lesson1Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson1);
        Spinner lesson2Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson2);
        Spinner lesson3Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson3);
        Spinner lesson4Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson4);
        Spinner lesson5Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson5);
        Spinner lesson6Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson6);
        Spinner lesson7Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson7);
        Spinner lesson8Spinner = root.findViewById(R.id.fragment_edit_day_schedule___spinner___lesson8);

        lessonsSpinners = new ArrayList<>();
        lessonsSpinners.add(lesson1Spinner);
        lessonsSpinners.add(lesson2Spinner);
        lessonsSpinners.add(lesson3Spinner);
        lessonsSpinners.add(lesson4Spinner);
        lessonsSpinners.add(lesson5Spinner);
        lessonsSpinners.add(lesson6Spinner);
        lessonsSpinners.add(lesson7Spinner);
        lessonsSpinners.add(lesson8Spinner);

        saveButton = root.findViewById(R.id.fragment_edit_day_schedule___material_button___save);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editDayScheduleViewModel.getSubjectsAndDayLessons().observe(getViewLifecycleOwner(), subjectsAndDayLessons -> {
            List<Subject> subjects = subjectsAndDayLessons.subjects;
            if (subjects != null) {
                List<SubjectItem> subjectItems = new ArrayList<>();
                List<Long> subjectsIds = new ArrayList<>();
                for (Subject subject : subjects){
                    subjectItems.add(new SubjectItem(subject.id, subject.name));
                    subjectsIds.add(subject.id);
                }

                Integer[] indexes = new Integer[8];
                List<Lesson> dayLessons = subjectsAndDayLessons.dayLessons;
                for (int i = 0; i < dayLessons.size(); i++){
                    indexes[i] = subjectsIds.indexOf(dayLessons.get(i).subjectId);
                }

                setAdaptersToSpinners(subjectItems, indexes);
            }
        });

        initSaveButton();
    }

    private void setAdaptersToSpinners(List<SubjectItem> data, Integer[] indexes) {
        for (int i = 0; i < lessonsSpinners.size(); i++) {
            ArrayAdapter<SubjectItem> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner lessonSpinner = lessonsSpinners.get(i);
            lessonSpinner.setAdapter(adapter);

            if (indexes[i] != null) {
                lessonSpinner.setSelection(indexes[i]);
            } else {
                lessonSpinner.setSelection(0);
            }
        }
    }

    private void initSaveButton() {
        saveButton.setOnClickListener(view -> {
            Long[] newSubjectsIds = new Long[8];
            for (int i = 0; i < 8; i++) {
                newSubjectsIds[i] = ((SubjectItem) lessonsSpinners.get(i).getSelectedItem()).id;
            }
            editDayScheduleViewModel.updateLessons(newSubjectsIds);

            NavDirections navDirections = EditDayScheduleFragmentDirections.actionEditDayScheduleFragmentToNavSchedule();
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });
    }
}
