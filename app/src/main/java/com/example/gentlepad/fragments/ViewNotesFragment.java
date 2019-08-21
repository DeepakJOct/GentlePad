package com.example.gentlepad.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.gentlepad.R;
import com.example.gentlepad.Utilities.Constants;
import com.example.gentlepad.Utilities.Prefs;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.dialogs.HelpDialogFragment;
import com.example.gentlepad.models.NoteItem;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnViewNotesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewNotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewNotesFragment extends Fragment implements View.OnClickListener {

    private NoteItem noteItem;
    EditText etNotesTitle;
    EditText etNotesDesc;
    ImageButton btnCancel, btnSave, btnBack, btnEdit;
    RelativeLayout rlEditButtons;
    DatabaseHelper db;

    private OnViewNotesFragmentInteractionListener mListener;
    private LinearLayout llSaveButtons, llEditButtons;
    private String oldNotesTitle, oldNotesDesc, newNotesTitle, newNotesDesc;
    private boolean isTitleChanged, isDescChanged;
    private String[] noteItemsArray;
    Toolbar mTopToolbar;

    public ViewNotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment ViewNotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewNotesFragment newInstance(NoteItem noteItem) {
        ViewNotesFragment fragment = new ViewNotesFragment();
        fragment.noteItem = noteItem;
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
        return inflater.inflate(R.layout.fragment_view_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        db = new DatabaseHelper(getContext());
        etNotesTitle = view.findViewById(R.id.et_notes_title);
        etNotesDesc = view.findViewById(R.id.et_notes_desc);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnBack = view.findViewById(R.id.btn_back);
        btnEdit = view.findViewById(R.id.btn_edit);
        llEditButtons = view.findViewById(R.id.ll_edit_buttons);
        llSaveButtons = view.findViewById(R.id.ll_save_buttons);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

//        getActivity().setTitle("View Notes");

        if (noteItem != null) {
            //Getting and saving notes title for selection in database.
            oldNotesTitle = noteItem.getNotesTitle();
            oldNotesDesc = noteItem.getNotesDesc();
            etNotesTitle.setText(noteItem.getNotesTitle());
            etNotesDesc.setText(noteItem.getNotesDesc());
        }
        etNotesTitle.setEnabled(false);
        etNotesDesc.setEnabled(false);
        llSaveButtons.setVisibility(View.GONE);
        llEditButtons.setVisibility(View.VISIBLE);
        //Get changed notes and save
        getEditedNotes();


        mListener.OnViewNotesFragmentInteractionListener();
        //First of all, the fragment only shows the details of the item.
        //Secondly, an option to edit the notes will appear... if user clicks on it,
        //EditTexts will be enabled and Save and Cancel button will be visible.
        //on again click on save button, the current item details will get updated
        //with edited text and edited date will also be updated.
        FloatingActionButton fab = getActivity().getWindow().findViewById(R.id.fab);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewNotesFragmentInteractionListener) {
            mListener = (OnViewNotesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnViewNotesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        getActivity().setTitle("View Notes");
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        if (menu != null) {
            menu.findItem(R.id.view_change).setVisible(false);
            menu.findItem(R.id.menu_settings).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            /*case R.id.help:
                new HelpDialogFragment().show(getFragmentManager(), "HelpDialogFragment");
                break;*/
            /*case R.id.menu_settings:
                CommonUtils.showToastMessage(getContext(), "Development in progress");
                break;*/
            case R.id.app_close:
                CommonUtils.showToastMessage(getContext(), "Close");
                getActivity().finish();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void getEditedNotes() {
        noteItemsArray = new String[3];
        etNotesTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence editedNotesTitle, int start, int before, int count) {
                newNotesTitle = etNotesTitle.getText().toString();
                noteItemsArray[0] = newNotesTitle;
                isTitleChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNotesDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence editedNotesDesc, int start, int before, int count) {
                newNotesDesc = etNotesDesc.getText().toString();
                noteItemsArray[1] = newNotesDesc;
                isDescChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (!isTitleChanged) {
            noteItemsArray[0] = oldNotesTitle;
        }
        if (!isDescChanged) {
            noteItemsArray[1] = oldNotesDesc;
        }

        if (!TextUtils.isEmpty(CommonUtils.getDate())) {
            noteItemsArray[2] = CommonUtils.getDate();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            getActivity().onBackPressed();
        } else if (view.getId() == R.id.btn_save) {
            db.updateNotes(getContext(), noteItemsArray, oldNotesTitle);
//            CommonUtils.showToastMessage(getContext(), "noteItemsArray--> " + noteItemsArray[0] + "\n" + noteItemsArray[1]);
            getActivity().onBackPressed();
        } else if (view.getId() == R.id.btn_edit) {
            getActivity().getWindow().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            llEditButtons.setVisibility(View.GONE);
            llSaveButtons.setVisibility(View.VISIBLE);
            etNotesTitle.setEnabled(true);
            etNotesTitle.setCursorVisible(true);
            etNotesDesc.setEnabled(true);
            etNotesDesc.setCursorVisible(true);
            btnCancel.setImageResource(R.drawable.ic_back);
            etNotesTitle.setFocusable(true);
            etNotesTitle.requestFocus();
            /*InputMethodManager imeManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            imeManager.showInputMethodPicker();*/
        } else if (view.getId() == R.id.btn_back) {
            getActivity().onBackPressed();
        }
    }

    //If preference file holds the font size flag then change the font sizes of the textviews only.
    public void changeFontSizeofTextViews() {
        if (Prefs.getString(Constants.FONT_SIZE, "") != null) {
            String fontSize = Prefs.getString(Constants.FONT_SIZE, "");
            if (fontSize.equalsIgnoreCase(Constants.SMALL)) {
                //Put the textview font sizes as it is
            } else if (fontSize.equalsIgnoreCase(Constants.MEDIUM)) {
                //Increase the textview font sizes to 10 points
            } else if (fontSize.equalsIgnoreCase(Constants.LARGE)) {
                //Increase the textview font sizes to 10 more points
            }
        }
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
    public interface OnViewNotesFragmentInteractionListener {

        void OnViewNotesFragmentInteractionListener();
    }
}
