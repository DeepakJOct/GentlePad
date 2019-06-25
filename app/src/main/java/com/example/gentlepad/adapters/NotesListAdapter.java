package com.example.gentlepad.adapters;

import android.content.Context;
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
    private DatabaseHelper db;
    private Context context;
    private NoteItem item;

    public NotesListAdapter(ArrayList<NoteItem> mNotesList, Context context) {
        this.savedNotesList = mNotesList;
        this.context = context;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item = getItemDataFromDb(tvNotesTitleItem.getText().toString());
                    if(item != null) {

                    }
                }
            });

        }
    }

    public NoteItem getItemDataFromDb(String itemNotesTitle) {
        db = new DatabaseHelper(context);
        NoteItem item = null;
        Cursor res = db.getSelectedItemDetails(itemNotesTitle);
        if (res.getCount() == 0) {
            return null;
        }
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                item = new NoteItem(res.getString(res.getColumnIndex("NOTES_TITLE")),
                        res.getString(res.getColumnIndex("NOTES_DESC")),
                        res.getString(res.getColumnIndex("DATE")));
                savedNotesList.add(item);
                res.moveToNext();
            }
        }
        res.close();
        return item;
    }



}
