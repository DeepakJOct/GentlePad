package com.example.gentlepad.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gentlepad.R;
import com.example.gentlepad.Utilities.Constants;
import com.example.gentlepad.Utilities.Prefs;
import com.example.gentlepad.adapters.NotesListAdapter;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.dialogs.HelpDialogFragment;
import com.example.gentlepad.dialogs.SortByDialogFragment;
import com.example.gentlepad.listeners.OnResultListener;
import com.example.gentlepad.models.NoteItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    TextView tvNoNotes;
    ArrayList<NoteItem> savedNotesList = new ArrayList<>();
    DatabaseHelper db;
    RelativeLayout rlNoNotes;
    boolean isNotesViewAsList = false;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    boolean isAllNotesDeleted = false;
    private ViewGroup.MarginLayoutParams params;


    private OnNotesListFragmentInteractionListener mListener;
    private float selectedFontSize;

    public NotesListFragment() {
        // Required empty public constructor
    }

    public static NotesListFragment newInstance(float selectedFontSize) {
        NotesListFragment fragment = new NotesListFragment();
        fragment.selectedFontSize = selectedFontSize;
        return fragment;
    }

    public static NotesListFragment newInstance() {
        NotesListFragment fragment = new NotesListFragment();
        return fragment;
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

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        isNotesViewAsList = CommonUtils.getBoolean(getContext(), Constants.LIST_VIEW);
        savedNotesList = getDataFromDb();
        rcvNotes = view.findViewById(R.id.rcv_notes);
        rlNoNotes = view.findViewById(R.id.rl_no_notes);
        tvNoNotes = view.findViewById(R.id.tv_no_notes);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
        linearLayoutManager = new LinearLayoutManager(getContext());
        /*linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);*/


        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        /*gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);*/
//        rcvNotes.setHasFixedSize(true);
        /*if (!isNotesViewAsList) {
            Collections.reverse(savedNotesList);
        }*/
        rcvNotes.setLayoutManager(isNotesViewAsList ? linearLayoutManager : staggeredGridLayoutManager);
        notesListAdapter = new NotesListAdapter(getContext(), isNotesViewAsList);

        notesListAdapter.resultListenOnDelete(new OnResultListener() {
            @Override
            public void getResult(Object object, boolean isSuccess) {
                Log.d("onResultListen", "onResult" + object);
                boolean isAllEmpty = (boolean) object;
                if (isAllEmpty) {
                    rlNoNotes.setVisibility(View.VISIBLE);
                    tvNoNotes.setText("Oops..! Notes list is empty.");
                    isAllNotesDeleted = true;
                } else {
                    rlNoNotes.setVisibility(View.GONE);
                    tvNoNotes.setText("Just one click away to add notes.");
                }
            }
        });

        mListener.OnNotesListFragmentInteractionListener();
        FloatingActionButton fab = getActivity().getWindow().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        params = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
        fab.setLayoutParams(params);
    }

    private void setDataToAdapter(boolean isFromSort) {
        if (savedNotesList != null) {
            //if not from sort the default list will be reversed & served
            //else after sorting recyclerview will get sorted
            if (isFromSort) {
                notesListAdapter.setList(savedNotesList);
                rcvNotes.setAdapter(notesListAdapter);
            } else {
                Collections.reverse(savedNotesList);
                notesListAdapter.setList(savedNotesList);
                rcvNotes.setAdapter(notesListAdapter);
            }
        }
    }


    private void showListOrGrid() {
        if (!isNotesViewAsList) {
            StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            /*Collections.reverse(savedNotesList);*/
            rcvNotes.setLayoutManager(mGridLayoutManager);
        } else {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            /*linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);*/
            rcvNotes.setLayoutManager(mLinearLayoutManager);
        }
        notesListAdapter.changeView(isNotesViewAsList);
        rcvNotes.setAdapter(notesListAdapter);
    }

    public void sortNotesBy(String sortOption) {
        Toast.makeText(getActivity(), "Sort by: " + sortOption, Toast.LENGTH_SHORT).show();
        if (sortOption.equalsIgnoreCase(Constants.NONE)) {

            //No option

        } else if (sortOption.equalsIgnoreCase(Constants.ASCENDING)) {

            Collections.sort(savedNotesList, new Comparator<NoteItem>() {
                @Override
                public int compare(NoteItem noteItem, NoteItem t1) {
                    return noteItem.getNotesTitle().compareTo(t1.getNotesTitle());
                }
            });

        } else if (sortOption.equalsIgnoreCase(Constants.DESCENDING)) {

            Collections.reverse(savedNotesList);

        } else if (sortOption.equalsIgnoreCase(Constants.DATE_MODIFIED)) {

            Collections.sort(savedNotesList, new Comparator<NoteItem>() {
                public int compare(NoteItem o1, NoteItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });

        }
        notesListAdapter.notifyDataSetChanged();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        boolean isListView = CommonUtils.getBoolean(getActivity(), Constants.LIST_VIEW);
        if (isListView) {
            menu.getItem(1).setTitle("Switch to Grid");
        } else {
            menu.getItem(1).setTitle("Switch to List");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*case R.id.help:
                new HelpDialogFragment().show(getFragmentManager(), "HelpDialogFragment");
                break;*/
            case R.id.view_change:
                isNotesViewAsList = !isNotesViewAsList;
                CommonUtils.saveBoolean(getContext(), Constants.LIST_VIEW, isNotesViewAsList);
                if (isAllNotesDeleted) {
                    CommonUtils.showToastMessage(getContext(), "Nothing to view");
                } else {
                    CommonUtils.showToastMessage(getContext(), isNotesViewAsList ? "List View" : "Grid View");
                }
                //remove this in the end
//                CommonUtils.showToastMessage(getContext(), "isListView:" + isNotesViewAsList);
                if (savedNotesList != null) {
                    //show list or grid view
                    showListOrGrid();
                    //tell the adapter that view has changed
                    notesListAdapter.notifyDataSetChanged();
                    if (isNotesViewAsList) {
                        item.setTitle("Switch to Grid");
                    } else {
                        item.setTitle("Switch to List");
                    }
                } else {
                    CommonUtils.showToastMessage(getContext(), "Nothing to view. Click (+) button to add notes.");
                }
                break;
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        /*
         * to refresh the list whenever a new item is added in the db
         * */
        if (savedNotesList != null) {
            savedNotesList.clear();
            savedNotesList = getDataFromDb();
            isNotesViewAsList = CommonUtils.getBoolean(getContext(), Constants.LIST_VIEW);
            showListOrGrid();
            setDataToAdapter(false);
            notesListAdapter.notifyDataSetChanged();
        } else {
//            getActivity().onBackPressed();
            if (savedNotesList == null) {
                getActivity().getWindow().findViewById(R.id.rl_no_notes).setVisibility(View.VISIBLE);
                TextView tvNoNotes = getActivity().getWindow().findViewById(R.id.tv_no_notes);
                tvNoNotes.setText("Just one click away to add notes.");
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
