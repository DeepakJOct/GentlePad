package com.example.gentlepad.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.adapters.NotesListAdapter;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.models.NoteItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesListFragment.OnNotesListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesListFragment extends Fragment {

    RecyclerView rcvNotes;
    NotesListAdapter notesListAdapter;
    NoteItem item;
    ArrayList<NoteItem> savedNotesList = new ArrayList<>();
    DatabaseHelper db;


    private OnNotesListFragmentInteractionListener mListener;

    public NotesListFragment() {
        // Required empty public constructor
    }

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedNotesList = getDataFromDb();
        rcvNotes = view.findViewById(R.id.rcv_notes);
        if (savedNotesList != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            rcvNotes.setLayoutManager(linearLayoutManager);
            notesListAdapter = new NotesListAdapter(savedNotesList, getContext());
            rcvNotes.setAdapter(notesListAdapter);
        } else {
            getActivity().onBackPressed();
        }
        mListener.OnNotesListFragmentInteractionListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNotesListFragmentInteractionListener) {
            mListener = (OnNotesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnViewNotesFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (savedNotesList != null) {
            savedNotesList.clear();
            savedNotesList = getDataFromDb();
            rcvNotes.setAdapter(new NotesListAdapter(savedNotesList, getContext()));
            rcvNotes.invalidate();
        } else {
            getActivity().onBackPressed();
            if (savedNotesList == null) {
                getActivity().getWindow().findViewById(R.id.rl_no_notes).setVisibility(View.VISIBLE);
                TextView tvNoNotes = getActivity().getWindow().findViewById(R.id.tv_no_notes);
                tvNoNotes.setText("Saved notes list is empty");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ArrayList<NoteItem> getDataFromDb() {
        db = new DatabaseHelper(getContext());
        Cursor res = db.getAllData();
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
        Log.d("ArrayListFromDb--> ", " " + savedNotesList.get(0));
        res.close();
        return savedNotesList;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNotesListFragmentInteractionListener {
        void OnNotesListFragmentInteractionListener();
    }
}
