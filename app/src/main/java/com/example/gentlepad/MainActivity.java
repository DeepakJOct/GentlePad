package com.example.gentlepad;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
    DatabaseHelper db;
    NoteItem item;
    ArrayList<NoteItem> savedNotesList = new ArrayList<>();
    RelativeLayout rlNoNotes;
    FloatingActionButton fabAddNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlNoNotes = findViewById(R.id.rl_no_notes);
        fabAddNew = findViewById(R.id.fab);
        if (getDataFromDb() != null) {
            startNewFragment(NotesListFragment.newInstance(), "NotesListFragment", true);
        } else {
            rlNoNotes.setVisibility(View.VISIBLE);
        }
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewFragment(AddNewNoteFragment.newInstance(), "AddNewNoteFragment", true);
            }
        });

        if(getSupportFragmentManager().findFragmentById(R.id.container) instanceof AddNewNoteFragment) {
            fabAddNew.setVisibility(View.INVISIBLE);
        } else {
            fabAddNew.setVisibility(View.VISIBLE);
        }

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
                if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof NotesListFragment) {
                    if (savedNotesList == null) {
                        //changed here
                        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("NoteListFragment")).commit();
                        rlNoNotes.setVisibility(View.VISIBLE);
                    }
                } else {
                    finish();
                }
            }
        }

    }

    void startNewFragment(final android.support.v4.app.Fragment frag, final String tag, boolean backstack) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
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
        startNewFragment(NotesListFragment.newInstance(), "NoteListFragment", true);

    }

    @Override
    public void OnNotesListFragmentInteractionListener() {
        if(savedNotesList != null) {
            this.findViewById(R.id.rl_no_notes).setVisibility(View.GONE);
        }
    }


    @Override
    public void OnViewNotesFragmentInteractionListener() {

    }
}
