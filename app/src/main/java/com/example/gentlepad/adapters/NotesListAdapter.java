package com.example.gentlepad.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.models.NoteItem;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.SavedNotesViewHolder> {

    private ArrayList<NoteItem> savedNotesList;

    public NotesListAdapter(ArrayList<NoteItem> mNotesList) {
        this.savedNotesList = mNotesList;
    }

    @Override
    public NotesListAdapter.SavedNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SavedNotesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes, parent, false));
    }


    @Override
    public void onBindViewHolder(NotesListAdapter.SavedNotesViewHolder holder, int position) {
        holder.tvNotesTitleItem.setText(savedNotesList.get(position).getNotesTitle());
        holder.tvNotesDesc.setText(savedNotesList.get(position).getNotesDesc());
        holder.tvNotesDate.setText(savedNotesList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return savedNotesList.size();
    }

    class SavedNotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotesTitleItem, tvNotesDesc, tvNotesDate;

        public SavedNotesViewHolder(View itemView) {
            super(itemView);
            tvNotesTitleItem = itemView.findViewById(R.id.tv_notes_title_item);
            tvNotesDesc = itemView.findViewById(R.id.tv_notes_desc);
            tvNotesDate = itemView.findViewById(R.id.tv_notes_date);

        }
    }



}
