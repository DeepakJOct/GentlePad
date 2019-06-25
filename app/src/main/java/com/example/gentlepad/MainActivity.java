package com.example.gentlepad;

import android.app.ActionBar;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.fragments.AddNewNoteFragment;
import com.example.gentlepad.fragments.NotesListFragment;
import com.example.gentlepad.fragments.ViewNotesFragment;
import com.example.gentlepad.models.NoteItem;
import com.example.gentlepad.splash.SplashActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddNewNoteFragment.OnFragmentInteractionListener,
        NotesListFragment.OnNotesListFragmentInteractionListener,
        ViewNotesFragment.OnViewNotesFragmentInteractionListener {

    //    android.support.v7.app.ActionBar actionBar;
    CardView cvAddNote;
    DatabaseHelper db;
    NoteItem item;
    ArrayList<NoteItem> savedNotesList = new ArrayList<>();
    TextView tvAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        actionBar = getSupportActionBar();
//        actionBar.setTitle(getString(R.string.app_title));
        cvAddNote = findViewById(R.id.cv_add_note);
        tvAddNote = findViewById(R.id.tv_add_note);
        if (getDataFromDb() != null) {
            startNewFragment(NotesListFragment.newInstance(), "NotesListFragment", true);
            tvAddNote.setText("Add new note");
        }
        cvAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAddNote.setVisibility(View.GONE);
                startNewFragment(AddNewNoteFragment.newInstance(), "AddNewNoteFragment", true);

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof AddNewNoteFragment) {
                getSupportFragmentManager().popBackStack();
            } else if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof ViewNotesFragment) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }

    }

    void startNewFragment(final android.support.v4.app.Fragment frag, final String tag, boolean backstack) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager()
                .beginTransaction();
        if (this.getSupportFragmentManager().findFragmentById(R.id.container) != null) {
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
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public ArrayList<NoteItem> getDataFromDb() {
        db = new DatabaseHelper(this);
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

    @Override
    public void onFragmentInteraction() {
        cvAddNote.setVisibility(View.VISIBLE);
        startNewFragment(NotesListFragment.newInstance(), "NoteListFragment", true);
    }

    @Override
    public void OnNotesListFragmentInteractionListener() {
        cvAddNote.setVisibility(View.VISIBLE);
        tvAddNote.setText("Add new note");
    }


    @Override
    public void OnViewNotesFragmentInteractionListener() {
        cvAddNote.setVisibility(View.GONE);
    }
}
