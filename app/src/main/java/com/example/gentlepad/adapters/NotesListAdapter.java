package com.example.gentlepad.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.dialogs.YesOrNoDialogFragment;
import com.example.gentlepad.fragments.ViewNotesFragment;
import com.example.gentlepad.listeners.DeleteItemListener;
import com.example.gentlepad.listeners.OnClickResultListener;
import com.example.gentlepad.listeners.OnResultListener;
import com.example.gentlepad.models.NoteItem;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.SavedNotesViewHolder> {

    private ArrayList<NoteItem> savedNotesList;
    private DatabaseHelper db;
    private Context context;
    private NoteItem item;
    boolean isDeleted = false;
    private String pendingDeleteItem;
    private OnResultListener onResultListener;
    private boolean isNotesViewAsList;
    private LayoutInflater layoutInflater;

    public NotesListAdapter(Context context, boolean isListView) {
        this.context = context;
        this.isNotesViewAsList = isListView;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<NoteItem> mNotesList) {
        this.savedNotesList = mNotesList;

    }

    public void resultListenOnDelete(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }


    @Override
    public NotesListAdapter.SavedNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*View view;
        if(viewType == 0) {
            view = layoutInflater.inflate(R.layout.item_notes, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.item_notes_grid, parent, false);
        }*/

        return new SavedNotesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(isNotesViewAsList ? R.layout.item_notes : R.layout.item_notes_grid, parent, false));
//        View view = LayoutInflater.from(parent.getContext()).inflate(isNotesViewAsList ? R.layout.item_notes : R.layout.item_notes_grid, null);
//        return new SavedNotesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NotesListAdapter.SavedNotesViewHolder holder, final int position) {
        if (savedNotesList != null) {

            holder.tvNotesTitleItem.setText(savedNotesList.get(position).getNotesTitle());
            holder.tvNotesDesc.setText(savedNotesList.get(position).getNotesDesc());
            holder.tvNotesDate.setText(savedNotesList.get(position).getDate());

            db = new DatabaseHelper(context);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pendingDeleteItem = savedNotesList.get(position).getNotesTitle();
                    if (pendingDeleteItem != null) {
                        final AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        FragmentManager fm = activity.getSupportFragmentManager();
                        YesOrNoDialogFragment.newInstance("Delete Note", "Action cannot be undone. Do you want to delete?", new DeleteItemListener() {
                            @Override
                            public void onSuccess(boolean isDelete) {

                                deleteItem(position);
                                notifyItemRemoved(position);
                                isDeleted = db.deleteNotes(pendingDeleteItem);
                                if (isDeleted) {
                                    CommonUtils.showToastMessage(context, "Deleted");
                                } else {
                                    CommonUtils.showToastMessage(context, "Error");
                                }
                                if (isDeleted && savedNotesList.size() == 0) {
                                    onResultListener.getResult(isDeleted, true);
                                }
                            }
                        }).show(fm, "YesOrNoDialogFragment");
                    }
                }
            });
        }
        //Put default text in views if data is not there
        if (TextUtils.isEmpty(savedNotesList.get(position).getNotesTitle())) {
            holder.tvNotesTitleItem.setText("No Title");
        }
        if (TextUtils.isEmpty(savedNotesList.get(position).getNotesDesc())) {
            holder.tvNotesDesc.setText("No Description");
        }


    }

    public void changeView(boolean isNotesViewShowing) {
        isNotesViewAsList = isNotesViewShowing;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return savedNotesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void deleteItem(int index) {
        savedNotesList.remove(index);
        notifyItemRemoved(index);
    }

    class SavedNotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotesTitleItem, tvNotesDesc, tvNotesDate;
        ImageView ivDelete;

        public SavedNotesViewHolder(View itemView) {
            super(itemView);
            tvNotesTitleItem = itemView.findViewById(R.id.tv_notes_title_item);
            tvNotesDesc = itemView.findViewById(R.id.tv_notes_desc);
            tvNotesDate = itemView.findViewById(R.id.tv_notes_date);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item = getItemDataFromDb(tvNotesTitleItem.getText().toString());
                    if (item != null) {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment mFragment = ViewNotesFragment.newInstance(item);
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.container, mFragment)
                                .addToBackStack("ViewNotesFragment")
                                .commit();
                    } else {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment mFragment = ViewNotesFragment.newInstance(item);
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.container, mFragment)
                                .addToBackStack("ViewNotesFragment")
                                .commit();
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

    /*void startNewFragment(final android.support.v4.app.Fragment frag, final String tag, boolean backstack) {
        AppCompatActivity activity = (AppCompatActivity)
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            if (backstack) {
                fragmentTransaction.replace(R.id.container, frag, tag);
                fragmentTransaction.addToBackStack(null);
            } else {
                fragmentTransaction.replace(R.id.container, frag, tag);
                fragmentTransaction.addToBackStack(tag);
            }
        } else {
            fragmentTransaction.add(R.id.container, frag, tag);
        }

        fragmentTransaction.commitAllowingStateLoss();

    }*/


}
