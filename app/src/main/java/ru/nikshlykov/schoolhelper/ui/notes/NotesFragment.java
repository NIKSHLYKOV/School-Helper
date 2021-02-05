package ru.nikshlykov.schoolhelper.ui.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.db.entities.Note;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;
import ru.nikshlykov.schoolhelper.ui.adapters.NotesRecyclerViewAdapter;

public class NotesFragment extends Fragment {

    private NotesRecyclerViewAdapter adapter;
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteFAB;
    private Drawable deleteIcon;

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
        deleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_delete_24);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerViewWithAdapter();

        initAddNoteFAB();
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

        new ItemTouchHelper(new SwipeDeleteCallback(0, ItemTouchHelper.LEFT))
                .attachToRecyclerView(notesRecyclerView);

        notesViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            if (notes != null) {
                for (Note note : notes) {
                    Log.d(NotesFragment.class.getCanonicalName(), note.id + " " + note.name);
                }
                adapter.setNotes(notes);
            }
        });
    }

    private void initAddNoteFAB() {
        addNoteFAB.setOnClickListener(view -> {
            NavDirections navDirections = NotesFragmentDirections.actionNavNotesToNavNote();
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });
    }

    private class SwipeDeleteCallback extends ItemTouchHelper.SimpleCallback {

        public SwipeDeleteCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final Note note = adapter.getNoteAt(viewHolder.getAdapterPosition());
            if (direction == ItemTouchHelper.LEFT) {
                notesViewModel.deleteNote(note);
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState,
                                boolean isCurrentlyActive) {
            View itemView = viewHolder.itemView;
            ColorDrawable swipeBackground = new ColorDrawable();

            int deleteIconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;

            if (dX < 0) {
                swipeBackground.setColor(ContextCompat.getColor(getContext(),
                        R.color.swipe_delete));
                swipeBackground.setBounds(
                        itemView.getRight() + (int) dX,
                        itemView.getTop(),
                        itemView.getRight(),
                        itemView.getBottom());
            }
            swipeBackground.draw(c);

            if (dX < 0) {
                deleteIcon.setBounds(
                        itemView.getRight() - deleteIconMargin - deleteIcon.getIntrinsicWidth(),
                        itemView.getTop() + deleteIconMargin,
                        itemView.getRight() - deleteIconMargin,
                        itemView.getBottom() - deleteIconMargin);
                deleteIcon.draw(c);
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
}