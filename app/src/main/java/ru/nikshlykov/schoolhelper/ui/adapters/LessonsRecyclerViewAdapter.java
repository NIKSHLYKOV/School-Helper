package ru.nikshlykov.schoolhelper.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.models.Lesson;


public class LessonsRecyclerViewAdapter extends RecyclerView.Adapter<LessonsRecyclerViewAdapter.NoteViewHolder> {

    private List<Lesson> lessons;

    private OnEntryClickListener onEntryClickListener;

    public LessonsRecyclerViewAdapter(){ }

    @Override
    public int getItemCount() {
        return (lessons != null) ? lessons.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView timeTextView;
        private final TextView subjectNameTextView;

        NoteViewHolder(View itemView) {
            super(itemView);

            timeTextView = itemView.findViewById(R.id.lesson_item___text_view___time);
            subjectNameTextView = itemView.findViewById(R.id.lesson_item___text_view___subject_name);

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
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_item, parent, false);
        return new NoteViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {
        final Lesson lesson = lessons.get(position);

        holder.timeTextView.setText(lesson.lessonTime);
        holder.subjectNameTextView.setText(lesson.subjectName);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        this.onEntryClickListener = onEntryClickListener;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    public Lesson getLessonAt(int position) {
        return lessons.get(position);
    }
}
