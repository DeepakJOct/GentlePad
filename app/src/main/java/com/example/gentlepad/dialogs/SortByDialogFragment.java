package com.example.gentlepad.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.listeners.OnResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Item Count Selecter Dialog fragment.
 */
@SuppressLint("ValidFragment")
public class SortByDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.close_btn)
    ImageView closeBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.iv_done)
    ImageView ivDone;
    /*@BindView(R.id.tv_font_small)
    TextView tvFontSmall;
    @BindView(R.id.tv_font_medium)
    TextView tvFontMedium;
    @BindView(R.id.tv_font_large)
    TextView tvFontLarge;*/

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.chk_btn_1)
    RadioButton chkBtn1;
    @BindView(R.id.chk_btn_2)
    RadioButton chkBtn2;
    @BindView(R.id.chk_btn_3)
    RadioButton chkBtn3;
    @BindView(R.id.chk_btn_4)
    RadioButton chkBtn4;

    private String sortOption;
    private OnResultListener onResultListener;
    int option;


    public SortByDialogFragment() {

    }

    public SortByDialogFragment(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ivCancel.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        /*tvFontSmall.setOnClickListener(this);
        tvFontMedium.setOnClickListener(this);
        tvFontLarge.setOnClickListener(this);*/

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int button) {
                if (button == R.id.chk_btn_1) {
                    option = 1;
                } else if (button == R.id.chk_btn_2) {
                    option = 2;
                } else if (button == R.id.chk_btn_3) {
                    option = 3;
                } else if (button == R.id.chk_btn_4) {
                    option = 4;
                }

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        if (view == ivCancel) {
            dismiss();
        } else if (view == ivDone) {
            switch (option) {
                case 1:
                    sortOption = chkBtn1.getText().toString();
                    onResultListener.getResult(sortOption, true);
                    break;
                case 2:
                    sortOption = chkBtn2.getText().toString();
                    onResultListener.getResult(sortOption, true);
                    break;
                case 3:
                    sortOption = chkBtn3.getText().toString();
                    onResultListener.getResult(sortOption, true);
                    break;
                case 4:
                    sortOption = chkBtn4.getText().toString();
                    onResultListener.getResult(sortOption, true);
                    break;
            }
            dismiss();
        }
    }
}