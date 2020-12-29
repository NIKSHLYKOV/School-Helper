package ru.nikshlykov.schoolhelper.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.db.entities.Note;


public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder> {

    private List<Note> notes;

    private OnEntryClickListener onEntryClickListener;

    public NotesRecyclerViewAdapter(){ }

    @Override
    public int getItemCount() {
        return (notes != null) ? notes.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView nameTextView;
        private final TextView textTextView;

        NoteViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.note_item___text_view___name);
            textTextView = itemView.findViewById(R.id.note_item___text_view___text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEntryClickListener.onEntryClick(view, getLayoutPosition());
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {
        final Note note = notes.get(position);

        holder.nameTextView.setText(note.name);
        holder.textTextView.setText(note.text);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        this.onEntryClickListener = onEntryClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }
}
