package com.example.gentlepad;

import android.app.ActionBar;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.gentlepad.fragments.AddNewNoteFragment;
import com.example.gentlepad.fragments.NotesListFragment;
import com.example.gentlepad.splash.SplashActivity;

public class MainActivity extends AppCompatActivity implements AddNewNoteFragment.OnFragmentInteractionListener,
        NotesListFragment.OnNotesListFragmentInteractionListener {

    //    android.support.v7.app.ActionBar actionBar;
    CardView cvAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        actionBar = getSupportActionBar();
//        actionBar.setTitle(getString(R.string.app_title));
        cvAddNote = findViewById(R.id.cv_add_note);
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
            getSupportFragmentManager().popBackStack();
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

    @Override
    public void onFragmentInteraction() {
        cvAddNote.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnNotesListFragmentInteractionListener() {
        cvAddNote.setVisibility(View.VISIBLE);

    }
}
