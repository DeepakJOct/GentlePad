package com.example.gentlepad.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.models.NoteItem;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.SavedNotesViewHolder> {

    DatabaseHelper db;
    NoteItem item;
    ArrayList<NoteItem> savedNotesList;

    @Override
    public NotesListAdapter.SavedNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SavedNotesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(NotesListAdapter.SavedNotesViewHolder holder, int position) {
        holder.tvNotesTitleItem.setText(getDataFromDb().get(position).getNotesTitle());
        holder.tvNotesDesc.setText(getDataFromDb().get(position).getNotesDesc());
        holder.tvNotesDate.setText(getDataFromDb().get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return 0;
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

    public ArrayList<NoteItem> getDataFromDb() {
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            return null;
        }
        while (res.moveToNext()) {
            item = new NoteItem(res.getString(1), res.getString(2), res.getString(3));
            savedNotesList.add(item);
        }
        return savedNotesList;
    }

}
