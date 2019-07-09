package com.example.gentlepad;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.fragments.AddNewNoteFragment;
import com.example.gentlepad.fragments.NotesListFragment;
import com.example.gentlepad.fragments.ViewNotesFragment;
import com.example.gentlepad.models.NoteItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddNewNoteFragment.OnFragmentInteractionListener,
        NotesListFragment.OnNotesListFragmentInteractionListener,
        ViewNotesFragment.OnViewNotesFragmentInteractionListener {

    DatabaseHelper db;
    NoteItem item;
    ArrayList<NoteItem> savedNotesList = new ArrayList<>();
    RelativeLayout rlNoNotes;
    TextView tvNoNotes;
    FloatingActionButton fabAddNew;
    List<Fragment> fragmentsBackstackList;
    boolean isListViewChanged;
    String fragmentTag;
    Fragment topFragment;
    Toolbar mTopToolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlNoNotes = findViewById(R.id.rl_no_notes);
        tvNoNotes = findViewById(R.id.tv_no_notes);
        fabAddNew = findViewById(R.id.fab);
        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        if (getDataFromDb() != null) {
            startNewFragment(NotesListFragment.newInstance(), "NotesListFragment", false);
        } else {
            rlNoNotes.setVisibility(View.VISIBLE);
        }
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewFragment(AddNewNoteFragment.newInstance(), "AddNewNoteFragment", true);
            }
        });

        if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof AddNewNoteFragment) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_settings:
                CommonUtils.showToastMessage(this, "Development in progress");
                break;
            case R.id.app_close:
                CommonUtils.showToastMessage(this, "Close");
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    /*@Override
    protected void onStop() {
        fragmentsBackstackList = getAllFragments();
        if (fragmentsBackstackList == null || fragmentsBackstackList.size() < 0) {
            super.onStop();
        } else {
            topFragment = fragmentsBackstackList.get(fragmentsBackstackList.size() - 1);
            fragmentTag = topFragment.getTag();
        }
    }*/

    /*@Override
    protected void onResume() {
        resumeFragments();
        super.onResume();

        *//*if (getDataFromDb() != null) {
            startNewFragment(NotesListFragment.newInstance(), "NotesListFragment", true);
        }*//*

    }*/

    @Override
    protected void onStart() {
        resumeFragments();
        super.onStart();
    }

    public void resumeFragments() {
        if (fragmentTag != null) {
            CommonUtils.showToastMessage(this, "onResumeThrownFromActivity" + " " + fragmentTag);
            if (fragmentTag.contains("AddNewNoteFragment")) {
                startNewFragment(AddNewNoteFragment.newInstance(), "AddNewNoteFragment", false);
            } else {
                CommonUtils.showToastMessage(this, "Some Error");
                //startNewFragment(NotesListFragment.newInstance(), "NoteListFragment", false);
            }
        }
    }


    public ArrayList<Fragment> getAllFragments() {
        ArrayList<Fragment> lista = new ArrayList<>();

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            try {
                fragment.getTag();
                lista.add(fragment);
            } catch (NullPointerException e) {

            }
        }

        return lista;

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
        if (savedNotesList != null) {
            this.findViewById(R.id.rl_no_notes).setVisibility(View.GONE);
        }
    }


    @Override
    public void OnViewNotesFragmentInteractionListener() {
//        Collections.reverse(savedNotesList);
    }
}
