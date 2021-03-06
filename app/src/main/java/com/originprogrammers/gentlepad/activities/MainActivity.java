package com.example.gentlepad.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.Utilities.Constants;
import com.example.gentlepad.Utilities.Prefs;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.dialogs.HelpDialogFragment;
import com.example.gentlepad.dialogs.RateusDialogFragment;
import com.example.gentlepad.dialogs.SortByDialogFragment;
import com.example.gentlepad.dialogs.SupportDialogFragment;
import com.example.gentlepad.fragments.AddNewNoteFragment;
import com.example.gentlepad.fragments.NotesListFragment;
import com.example.gentlepad.fragments.ViewNotesFragment;
import com.example.gentlepad.listeners.OnResultListener;
import com.example.gentlepad.models.NoteItem;
import com.example.gentlepad.views.MovableFloatingActionButton;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class MainActivity extends AppCompatActivity implements AddNewNoteFragment.OnFragmentInteractionListener,
        NotesListFragment.OnNotesListFragmentInteractionListener,
        ViewNotesFragment.OnViewNotesFragmentInteractionListener {

    DatabaseHelper db;
    NoteItem item;
    ArrayList<NoteItem> savedNotesList = new ArrayList<>();
    RelativeLayout rlNoNotes;
    TextView tvNoNotes;
    MovableFloatingActionButton fabAddNew;
    List<Fragment> fragmentsBackstackList;
    boolean isListViewChanged;
    String fragmentTag;
    Fragment topFragment;
    Toolbar mTopToolbar;
    private boolean isFontSizeIncreased;
    private int FONT_SELECT_REQUEST = 100, SORT_BY_REQUEST = 101;
    private String fontSize;
    private float selectedFontSize;
    private NotesListFragment notesListFragment;
    private int REQUEST_APP_UPDATE = 111;
    private AppUpdateManager appUpdateManager;
    InstallStateUpdatedListener installStateUpdatedListener;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prefs.initPrefs(this);
        rlNoNotes = findViewById(R.id.rl_no_notes);
        tvNoNotes = findViewById(R.id.tv_no_notes);
        fabAddNew = findViewById(R.id.fab);
        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        //check for the app update
        appUpdateCheck();
        if (getDataFromDb() != null) {
            startNewFragment(NotesListFragment.newInstance(), "NotesListFragment", false);
            if (rlNoNotes.getVisibility() == View.VISIBLE) {
                rlNoNotes.setVisibility(View.GONE);
            }
        } else {
            rlNoNotes.setVisibility(View.VISIBLE);
        }
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new HelpDialogFragment().show(getSupportFragmentManager(), "HelpDialogFragment");
                startNewFragment(AddNewNoteFragment.newInstance(), "AddNewNoteFragment", true);
                if (rlNoNotes.getVisibility() == View.VISIBLE) {
                    rlNoNotes.setVisibility(View.GONE);
                }
            }
        });

        if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof AddNewNoteFragment) {
            fabAddNew.setVisibility(View.GONE);
        } else {
            fabAddNew.setVisibility(View.VISIBLE);
        }
        Prefs.putBoolean(Prefs.IS_SWIPE_FIRST_TIME, true);

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
            } else if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof NotesListFragment) {
                if (savedNotesList == null) {
                    //changed here
                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("NoteListFragment")).commit();
                    rlNoNotes.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
            } else {
                super.onBackPressed();
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
            case R.id.support:
                new SupportDialogFragment().show(getSupportFragmentManager(), "HelpDialogFragment");
                break;
            case R.id.help:
                new HelpDialogFragment().show(getSupportFragmentManager(), "HelpDialogFragment");
                break;
            case R.id.sort_by:
                new SortByDialogFragment(new OnResultListener() {
                    @Override
                    public void getResult(Object object, boolean isSuccess) {
                        if (isSuccess) {
                            String option = (String) object;
                            if (option != null) {
                                if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof NotesListFragment) {
                                    NotesListFragment f = (NotesListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                                    f.sortNotesBy(option);
                                    Prefs.putString(Constants.SAVED_SORT_OPTION, option);
                                    Prefs.putBoolean(Constants.IS_SORTED, true);
                                    Log.d("MainActivity", "sortResult--->sortOption--> " + option);
                                }
                            }
//                            Toast.makeText(MainActivity.this, "Option: " + option, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show(getSupportFragmentManager(), "SortByDialogFragment");

                /*Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, FONT_SELECT_REQUEST);*/
                break;
            case R.id.rate_us:
                new RateusDialogFragment().show(getSupportFragmentManager(), "RateusDialogFragment");
                break;
            case R.id.app_close:
//                CommonUtils.showToastMessage(this, "Close");
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FONT_SELECT_REQUEST) {
//            CommonUtils.showToastMessage(MainActivity.this, "requestCode--> " + requestCode + "data--> " + data);

            if (resultCode == RESULT_OK && data != null) {
                fontSize = data.getStringExtra(Constants.FONT_SIZE);
                //Yeheh....! We are getting the font size from settings activity here.
                CommonUtils.showToastMessage(MainActivity.this, "Selected font size is \"" + fontSize + "\"");
                Prefs.putString(Constants.FONT_SIZE, fontSize);

            }
        }
    }

    /*
     * Changing the font sizes for the whole app. Check the below site out,
     * https://stackoverflow.com/questions/12704216/how-to-change-the-font-size-in-a-whole-application-programmatically-android
     *
     * */

    @Override
    protected void onResume() {
        super.onResume();
        //Show the tutorials on first open
        if(!Prefs.getBoolean(Prefs.IS_TUTORIAL_SHOWN, false)) {
            ShowIntro("Add Notes","Tap here to start writing your notes.", R.id.fab, 1);
        }


        //check for the app update
        appUpdateCheck();
        /*
         * Get font size string from the font selection dialog in settings activity.
         * */
        if (fontSize != null) {
            CommonUtils.showToastMessage(this, fontSize);
            if (fontSize.equalsIgnoreCase(Constants.MEDIUM)) {
                selectedFontSize = 1.5f;
                CommonUtils.showToastMessage(this, "@Onresume-->" + selectedFontSize + "");
                Prefs.putFloat(Constants.SELECTED_FONT_SIZE, selectedFontSize);
            } else {
                selectedFontSize = 0;
                Prefs.putFloat(Constants.SELECTED_FONT_SIZE, selectedFontSize);
            }
        }
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fabAddNew.getLayoutParams();
        fabAddNew.setLayoutParams(params);
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
        Cursor res;
        if (Prefs.getBoolean(Constants.IS_SORTED, false)
                && Prefs.getString(Constants.SAVED_SORT_OPTION, "") != null) {
            Log.d("MainActivity", "getDataFromDb--->isSorted--> " + Prefs.getBoolean(Constants.IS_SORTED, false));
            Log.d("MainActivity", "getDataFromDb--->isSorted--> " + Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
            res = db.orderBy(Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
        } else {
            res = db.orderBy(Prefs.getString(Constants.SAVED_SORT_OPTION, ""));
        }
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

    //Manage App Update

    private void appUpdateCheck() {
        //Listen to update state
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState state) {
                if (state.installStatus() == InstallStatus.DOWNLOADED) {
                    popSnackBarForCompleteUpdate();
                } else if (state.installStatus() == InstallStatus.INSTALLED) {
                    if (appUpdateManager != null) {
                        appUpdateManager.unregisterListener(installStateUpdatedListener);
                    }
                } else {
                    Log.i("MainActivity", "InstallStateUpdatedListener: state: " + state.installStatus());
                }
            }
        };

        //Check for update availability and start if it's available
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        appUpdateManager.registerListener(installStateUpdatedListener);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    // Request the update.
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.IMMEDIATE,
                                MainActivity.this,
                                REQUEST_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popSnackBarForCompleteUpdate();
                } else {
                    Log.e("MainActivity", "checkForAppUpdateAvailability: something else");
                }
            }
        });


    }

    private void ShowIntro(String title, String text, int viewId, final int type) {

        new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(text)
                .setTargetView(findViewById(viewId))
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        if (type == 1) {
                            ShowIntro("Sort", "Tap here to arrange the list", R.id.sort_by, 6);
                        } else if (type == 6) {
                            ShowIntro("List/Grid", "Tap to change view from list or grid", R.id.view_change, 3);
                        } /*else if (type == 3) {
                            ShowIntro("Menu", "Click on menu for help & support", R.menu.menu, 4);
                        }*/ /*else if (type == 3) {
                            ShowIntro("Overlay", "Add your selected overlay effect on your video ", R.id.button_tool_overlay, 5);
                        } else if (type == 5) {
                            SharePrefUtils.putBoolean("showcase", false);
                        }*/
                    }
                })
                .build()
                .show();
        Prefs.putBoolean(Prefs.IS_TUTORIAL_SHOWN, true);
    }

    private void popSnackBarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rl_notes), "Update Install Successful", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(appUpdateManager != null) {
                    appUpdateManager.completeUpdate();
                }
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.text_color));
        snackbar.show();
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

    }
}
