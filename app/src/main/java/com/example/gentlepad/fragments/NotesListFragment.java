package com.example.gentlepad.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
    RelativeLayout rlNoNotes, rlAllNotes;
    boolean isNotesViewAsList = false;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    boolean isAllNotesDeleted = false;
    private ViewGroup.MarginLayoutParams params;


    private OnNotesListFragmentInteractionListener mListener;
    private float selectedFontSize;
    private boolean isBtnclick;

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
        /*if (Prefs.getBoolean(Constants.IS_SORTED, false) && Prefs.getString(Constants.SAVED_SORT_OPTION, "") != null) {
            savedNotesList = getDataFromDbSorted(Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
        } else {*/
        savedNotesList = getDataFromDb();
//        }
        rcvNotes = view.findViewById(R.id.rcv_notes);
        rlNoNotes = view.findViewById(R.id.rl_no_notes);
        rlAllNotes = view.findViewById(R.id.rl_notes);
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
        final FloatingActionButton fab = getActivity().getWindow().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        params = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
        fab.setLayoutParams(params);

        rcvNotes.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, final int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                fab.show();
                            }
                        }
                    }, 2200);

//                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }
        });

    }

    private void setDataToAdapter(boolean isFromSort) {
        if (savedNotesList != null) {
            //if not from sort the default list will be reversed & served
            //else after sorting recyclerview will get sorted
            if (isFromSort) {
                notesListAdapter.setList(savedNotesList);
                notesListAdapter.setTimeForNotesAddedToday();
                rcvNotes.setAdapter(notesListAdapter);
            } else {
//                Collections.reverse(savedNotesList);
                notesListAdapter.setList(savedNotesList);
                notesListAdapter.setTimeForNotesAddedToday();
                rcvNotes.setAdapter(notesListAdapter);
            }
        }
    }


    private void showListOrGrid() {
        if (!isNotesViewAsList) {
            StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            /*Collections.reverse(savedNotesList);*/
            rcvNotes.setLayoutManager(mGridLayoutManager);
//            final GridLayoutAnimationController gridAnimationController = new GridLayoutAnimationController();
            if(isBtnclick) {
                TransitionManager.beginDelayedTransition(rcvNotes);
            }
            mGridLayoutManager.setSpanCount(2);
            notesListAdapter.notifyDataSetChanged();
        } else {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            /*linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);*/
            rcvNotes.setLayoutManager(mLinearLayoutManager);
            if(isBtnclick) {
                TransitionManager.beginDelayedTransition(rcvNotes);
            }
        }
        notesListAdapter.changeView(isNotesViewAsList);
        rcvNotes.setAdapter(notesListAdapter);
        isBtnclick = false;
    }

    public void sortNotesBy(String sortOption) {
        Toast.makeText(getActivity(), "Sort by: " + sortOption, Toast.LENGTH_SHORT).show();
        if (sortOption.equalsIgnoreCase(Constants.NONE)) {

            //No option

        } else if (sortOption.equalsIgnoreCase(Constants.ASCENDING)) {
            if (savedNotesList != null && savedNotesList.size() > 0) {
                savedNotesList.clear();
                savedNotesList = getDataFromDbSorted(sortOption);

                for (NoteItem n : savedNotesList) {
                    Log.d("Frag--> " + "title--> ", n.getNotesTitle());
                }
                /*Collections.sort(savedNotesList, new Comparator<NoteItem>() {
                    @Override
                    public int compare(NoteItem noteItem, NoteItem t1) {
                        return noteItem.getNotesTitle().compareTo(t1.getNotesTitle());
                    }
                });*/
            } else {
                CommonUtils.showToastMessage(getContext(), getString(R.string.could_note_sort_no_notes));
            }

        } else if (sortOption.equalsIgnoreCase(Constants.DESCENDING)) {
            if (savedNotesList != null && savedNotesList.size() > 0) {
//                Collections.reverse(savedNotesList);
                savedNotesList.clear();
                savedNotesList = getDataFromDbSorted(sortOption);
                for (NoteItem n : savedNotesList) {
                    Log.d("Frag--> " + "title--> ", n.getNotesTitle());
                }
            } else {
                CommonUtils.showToastMessage(getContext(), getString(R.string.could_note_sort_no_notes));
            }

        } else if (sortOption.equalsIgnoreCase(Constants.DATE_MODIFIED)) {
            if (savedNotesList != null && savedNotesList.size() > 0) {
                savedNotesList.clear();
                savedNotesList = getDataFromDbSorted(sortOption);
                for (NoteItem n : savedNotesList) {
                    Log.d("Frag--> " + "title--> ", n.getNotesTitle());
                }
                /*Collections.sort(savedNotesList, new Comparator<NoteItem>() {
                    public int compare(NoteItem o1, NoteItem o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });*/
            } else {
                CommonUtils.showToastMessage(getContext(), getString(R.string.could_note_sort_no_notes));
            }

        }
        setTimeForNotesAddedToday();
        notesListAdapter.notifyDataSetChanged();
    }

    public void setTimeForNotesAddedToday() {
        @SuppressLint("SimpleDateFormat")
        DateFormat parser = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        @SuppressLint("SimpleDateFormat")
        DateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        @SuppressLint("SimpleDateFormat")
        DateFormat timeParser = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        @SuppressLint("SimpleDateFormat")
        DateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
        for(NoteItem n : savedNotesList) {
            try {
                Date convertedDate = parser.parse(n.getDate());
                String notesDate_ddMMyyyy = formatter.format(convertedDate);
                Date todayDate = parser.parse(CommonUtils.getDate());
                String todaysDate_ddMMyyyy = formatter.format(todayDate);

                Date toTime = timeParser.parse(n.getDate());
                String todaysTime = timeFormatter.format(toTime);
                if(notesDate_ddMMyyyy.equals(todaysDate_ddMMyyyy)) {
                    n.setDate(todaysTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
            menu.getItem(3).setIcon(getResources().getDrawable(R.drawable.ic_grid));

        } else {
            menu.getItem(3).setIcon(getResources().getDrawable(R.drawable.ic_list));
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
                isBtnclick = true;
                isNotesViewAsList = !isNotesViewAsList;
                CommonUtils.saveBoolean(getContext(), Constants.LIST_VIEW, isNotesViewAsList);
                if (isAllNotesDeleted) {
                    CommonUtils.showToastMessage(getContext(), "Nothing to view");
                } else {
//                    CommonUtils.showToastMessage(getContext(), isNotesViewAsList ? "List View" : "Grid View");
                }
                //remove this in the end
//                CommonUtils.showToastMessage(getContext(), "isListView:" + isNotesViewAsList);
                if (savedNotesList != null) {
                    //show list or grid view
                    showListOrGrid();
                    //tell the adapter that view has changed
                    notesListAdapter.notifyDataSetChanged();
                    if (isNotesViewAsList) {
//                        item.setTitle("Switch to Grid");
                        item.setIcon(getResources().getDrawable(R.drawable.ic_grid));
                    } else {
//                        item.setTitle("Switch to List");
                        item.setIcon(getResources().getDrawable(R.drawable.ic_list));
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
        //for sorting the list if there is
        // already a sort option selected before application is resumed

        /*db = new DatabaseHelper(getContext());
        Cursor res;
        if (Prefs.getString(Constants.SAVED_SORT_OPTION, "") != null) {
            Log.d("NoteListFragment", "getDataFromDb--->isSorted--> " + Prefs.getBoolean(Constants.IS_SORTED, false));
            Log.d("NoteListFragment", "getDataFromDb--->isSorted--> " + Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
            res = db.orderBy(Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
        } else {
            res = db.getAllData();
        }*/


        db = new DatabaseHelper(getContext());
//        Cursor res = db.getAllData();
        Cursor res = db.orderBy(Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
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

    public ArrayList<NoteItem> getDataFromDbSorted(String option) {
        db = new DatabaseHelper(getContext());
        Cursor res = db.orderBy(option);
        Log.d("NoteListFragment", "getDataFromDbSorted--->orderByOption--> " + option);
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
