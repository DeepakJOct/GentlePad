package com.example.gentlepad.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.adapters.NotesListAdapter;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.database.DatabaseHelper;
import com.example.gentlepad.models.NoteItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewNoteFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    EditText etNotesTitle;
    EditText etNotesDesc;
    Button btnCancel, btnSave;
    DatabaseHelper databaseHelper;
    String nTitle, nDesc, nDate;

    public AddNewNoteFragment() {
        // Required empty public constructor
    }


    public static AddNewNoteFragment newInstance() {
        return new AddNewNoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNotesTitle = view.findViewById(R.id.et_notes_title);
        etNotesDesc = view.findViewById(R.id.et_notes_desc);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(getContext());

        if (!TextUtils.isEmpty(etNotesTitle.getText().toString())) {
            nTitle = etNotesTitle.getText().toString();
        }
        if (!TextUtils.isEmpty(etNotesDesc.getText().toString())) {
            nDesc = etNotesDesc.getText().toString();
        }
        nDate = CommonUtils.getDate();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == (R.id.btn_cancel)) {
            CommonUtils.showToastMessage(getContext(), "Clicked");
            getFragmentManager().beginTransaction().remove(this).commit();
            mListener.onFragmentInteraction();
        } else if (view.getId() == (R.id.btn_save)) {
            if (validations()) {
                saveNotes(getContext(), nTitle, nDesc, nDate);
                startNewFragment(NotesListFragment.newInstance(), "NotesListFragment", true);
            }

            /*After saving the details to database, show the items by fetching from the list into the
            * recycler view
            * */
        }
    }

    private void saveNotes(Context context, String notesTitle, String notesDesc, String date) {
        NoteItem item = new NoteItem(notesTitle, notesDesc, date);
        databaseHelper.insertData(context, item.getNotesTitle(), item.getNotesDesc(), item.getDate());
        databaseHelper.close();
    }

    private boolean validations() {
        if (!TextUtils.isEmpty(etNotesTitle.getText().toString())) {
            nTitle = etNotesTitle.getText().toString();
        } else {
            etNotesTitle.setError("Please enter title");
            return false;
        }
        if (!TextUtils.isEmpty(etNotesDesc.getText().toString())) {
            nDesc = etNotesDesc.getText().toString();
        } else {
            etNotesDesc.setError("Please enter notes");
            return false;
        }
        if (!TextUtils.isEmpty(nDate)) {
            nDate = CommonUtils.getDate();
        }
        return true;
    }

    void startNewFragment(final android.support.v4.app.Fragment frag, final String tag, boolean backstack) {
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

}
