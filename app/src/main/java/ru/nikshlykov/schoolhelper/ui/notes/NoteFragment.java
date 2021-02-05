
package ru.nikshlykov.schoolhelper.ui.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;

public class NoteFragment extends Fragment {

    private MaterialButton saveButton;
    private TextInputEditText noteNameEditText;
    private TextInputEditText noteTextEditText;

    private NoteViewModel noteViewModel;

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
        noteViewModel =
                new ViewModelProvider(this).get(NoteViewModel.class);
        Bundle extras = getArguments();
        if (extras != null) {
            long noteId = NoteFragmentArgs.fromBundle(extras).getNoteId();
            if (noteId != 0L) {
                noteViewModel.setNoteLiveData(noteId);
            }
        }

        View root = inflater.inflate(R.layout.fragment_note, container, false);
        saveButton = root.findViewById(R.id.fragment_note___material_button___save);
        noteNameEditText = root.findViewById(R.id.fragment_note___text_input_edit_text___name);
        noteTextEditText = root.findViewById(R.id.fragment_note___text_input_edit_text___text);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setConfirmButtonClickListener();

        noteViewModel.getNoteLiveData().observe(getViewLifecycleOwner(), note -> {
            if (note != null) {
                noteNameEditText.setText(note.name);
                noteTextEditText.setText(note.text);
            }
        });
    }

    private void setConfirmButtonClickListener() {
        saveButton.setOnClickListener(v -> {
            String noteName = noteNameEditText.getText().toString().trim();
            String noteText = noteTextEditText.getText().toString().trim();
            if (!noteName.isEmpty() && !noteText.isEmpty()) {
                noteViewModel.insertOrUpdateNote(noteName, noteText);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(saveButton.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                NavDirections navDirections = NoteFragmentDirections.actionNavNoteToNavNotes();
                onFragmentInteractionListener.onFragmentInteraction(navDirections);
            }
            else {
                Toast.makeText(getContext(), "Необходимо ввести название и текст",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

