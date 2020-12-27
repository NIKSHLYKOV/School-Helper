package ru.nikshlykov.schoolhelper.ui.notes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.db.note.Note;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;
import ru.nikshlykov.schoolhelper.ui.adapters.NotesRecyclerViewAdapter;

public class NotesFragment extends Fragment {

    private NotesRecyclerViewAdapter adapter;
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteFAB;

    private NotesViewModel notesViewModel;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                new ViewModelProvider(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        notesRecyclerView = root.findViewById(R.id.fragment_notes___recycler_view___notes);
        addNoteFAB = root.findViewById(R.id.fragment_notes___fab___add_note);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerViewWithAdapter();

        initSaveButton();
    }

    private void initRecyclerViewWithAdapter() {
        adapter = new NotesRecyclerViewAdapter();
        adapter.setOnEntryClickListener((view, position) -> {
            final Note currentWord = adapter.getNotes().get(position);
            NavDirections navDirections = NotesFragmentDirections.actionNavNotesToNavNote().setNoteId(currentWord.id);
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });

        notesRecyclerView.setAdapter(adapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    addNoteFAB.hide();
                } else if (dy < 0) {
                    addNoteFAB.show();
                }
            }
        });

        notesViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            if (notes != null) {
                for (Note note : notes) {
                    Log.d(NotesFragment.class.getCanonicalName(), note.id + " " + note.name);
                }
                adapter.setNotes(notes);
            }
        });
    }

    private void initSaveButton() {
        addNoteFAB.setOnClickListener(view -> {
            NavDirections navDirections = NotesFragmentDirections.actionNavNotesToNavNote();
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });
    }
}