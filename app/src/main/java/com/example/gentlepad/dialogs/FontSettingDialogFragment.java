package com.originprogrammers.gentlepad.dialogs;

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

import com.originprogrammers.gentlepad.R;
import com.originprogrammers.gentlepad.Utilities.Constants;
import com.originprogrammers.gentlepad.common.CommonUtils;
import com.originprogrammers.gentlepad.listeners.OnResultListener;

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

    private OnResultListener onResultListener;
    private int whichButton;
    private String fontSize;
    String currentFontSize;


    public FontSettingDialogFragment(String currentFontSize, OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
        this.currentFontSize = currentFontSize;
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
        CommonUtils.showToastMessage(getContext(), "currentFont: " + currentFontSize);
        if(currentFontSize.equalsIgnoreCase(Constants.SMALL)) {
            checkBtnOne.setChecked(true);
        } else if(currentFontSize.equalsIgnoreCase(Constants.MEDIUM)) {
            checkBtnTwo.setChecked(true);
        }else if(currentFontSize.equalsIgnoreCase(Constants.LARGE)) {
            checkBtnThree.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int button) {
                if (button == R.id.check_btn_one) {
                    whichButton = 1;
                } else if (button == R.id.check_btn_two) {
                    whichButton = 2;
                } else if (button == R.id.check_btn_three) {
                    whichButton = 3;
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
            switch (whichButton) {
                case 1:
                    fontSize = checkBtnOne.getText().toString();
                    onResultListener.getResult(fontSize, true);
                    break;
                case 2:
                    fontSize = checkBtnTwo.getText().toString();
                    onResultListener.getResult(fontSize, true);
                    break;
                case 3:
                    fontSize = checkBtnThree.getText().toString();
                    onResultListener.getResult(fontSize, true);
                    break;
            }
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