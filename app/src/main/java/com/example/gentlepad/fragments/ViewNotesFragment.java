package com.example.gentlepad.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.gentlepad.R;
import com.example.gentlepad.models.NoteItem;

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
    Button btnCancel, btnSave, btnBack, btnEdit;
    RelativeLayout rlEditButtons;

    private OnViewNotesFragmentInteractionListener mListener;
    private RelativeLayout rlSaveButtons;

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

        etNotesTitle = view.findViewById(R.id.et_notes_title);
        etNotesDesc = view.findViewById(R.id.et_notes_desc);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnBack = view.findViewById(R.id.btn_back);
        btnEdit = view.findViewById(R.id.btn_edit);
        rlEditButtons = view.findViewById(R.id.rl_edit_buttons);
        rlSaveButtons = view.findViewById(R.id.rl_save_buttons);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        if (noteItem != null) {
            etNotesTitle.setText(noteItem.getNotesTitle());
            etNotesDesc.setText(noteItem.getNotesDesc());
        }
        etNotesTitle.setEnabled(false);
        etNotesDesc.setEnabled(false);
        rlSaveButtons.setVisibility(View.GONE);
        rlEditButtons.setVisibility(View.VISIBLE);


        mListener.OnViewNotesFragmentInteractionListener();
        //First of all, the fragment only shows the details of the item.
        //Secondly, an option to edit the notes will appear... if user clicks on it,
        //EditTexts will be enabled and Save and Cancel button will be visible.
        //on again click on save button, the current item details will get updated
        //with edited text and edited date will also be updated.


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
        mListener = null;
    }


    public void updateNotes() {
        //First of all, the fragment only shows the details of the item.
        //Secondly, an option to edit the notes will appear... if user clicks on it,
        //EditTexts will be enabled and Save and Cancel button will be visible.
        //on again click on save button, the current item details will get updated
        //with edited text and edited date will also be updated.


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_cancel) {
            getActivity().onBackPressed();
        } else if(view.getId() == R.id.btn_save){
            //get edit text data and update the database here,
            // then go back by using getActivity().onBackPressed();
        } else if(view.getId() == R.id.btn_edit) {
            rlEditButtons.setVisibility(View.GONE);
            rlSaveButtons.setVisibility(View.VISIBLE);
            etNotesTitle.setEnabled(true);
            etNotesTitle.setCursorVisible(true);
            etNotesDesc.setEnabled(true);
            etNotesDesc.setCursorVisible(true);
            btnCancel.setText("Back");
            etNotesTitle.setFocusable(true);
        } else if(view.getId() == R.id.btn_back) {
            getActivity().onBackPressed();
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