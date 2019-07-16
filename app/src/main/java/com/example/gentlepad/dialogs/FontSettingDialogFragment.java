package com.example.gentlepad.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.common.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Item Count Selecter Dialog fragment.
 */
@SuppressLint("ValidFragment")
public class FontSettingDialogFragment extends DialogFragment implements View.OnClickListener {

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
    @BindView(R.id.check_btn_one)
    RadioButton checkBtnOne;
    @BindView(R.id.check_btn_two)
    RadioButton checkBtnTwo;
    @BindView(R.id.check_btn_three)
    RadioButton checkBtnThree;


    public FontSettingDialogFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_font_setting, container, false);
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
            dismiss();
        } /*else if (view == tvFontSmall) {
            CommonUtils.showToastMessage(getContext(), "Small Font");
        } else if (view == tvFontMedium) {
            CommonUtils.showToastMessage(getContext(), "Medium Font");
        } else if (view == tvFontLarge) {
            CommonUtils.showToastMessage(getContext(), "Large Font");
        }*/
    }
}