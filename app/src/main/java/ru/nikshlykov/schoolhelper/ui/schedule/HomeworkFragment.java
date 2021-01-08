
package ru.nikshlykov.schoolhelper.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;

public class HomeworkFragment extends Fragment {

    private TextInputEditText homeworkTextEditText;
    private MaterialButton saveButton;

    private HomeworkViewModel homeworkViewModel;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener =
                    (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeworkViewModel =
                new ViewModelProvider(this).get(HomeworkViewModel.class);
        Bundle extras = getArguments();
        if (extras != null) {
            long lessonId = HomeworkFragmentArgs.fromBundle(extras).getLessonId();
            if (lessonId != 0L) {
                homeworkViewModel.setLessonLiveData(lessonId);
            }
        }

        View root = inflater.inflate(R.layout.fragment_homework, container, false);
        saveButton = root.findViewById(R.id.fragment_homework___material_button___save);
        homeworkTextEditText = root.findViewById(R.id.fragment_homework___text_input_edit_text);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setConfirmButtonClickListener();

        homeworkViewModel.getLessonLiveData().observe(getViewLifecycleOwner(), lesson -> {
            if (lesson != null) {
                homeworkTextEditText.setText(lesson.homework);
            }
        });
    }

    private void setConfirmButtonClickListener() {
        saveButton.setOnClickListener(v -> {
            String homework = homeworkTextEditText.getText().toString().trim();
            homeworkViewModel.updateHomeWork(homework);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(saveButton.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            // TODO Сделать popBackStack() в MainActivity. Иначе будет кнопка назад не так работать,
            //  т.к. фрагемты будут добавляться в стек.
            NavDirections navDirections = HomeworkFragmentDirections.actionHomeworkFragmentToNavSchedule();
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });
    }
}

