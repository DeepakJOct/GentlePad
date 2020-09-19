package com.example.gentlepad.dialogs;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.listeners.OnResultListener;
import com.example.gentlepad.views.AutofitTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Item Count Selecter Dialog fragment.
 */
@SuppressLint("ValidFragment")
public class HelpDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.close_btn)
    ImageView closeBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.iv_cancel)
//    ImageView ivCancel;
    @BindView(R.id.iv_done)
    ImageView ivDone;
    @BindView(R.id.tv_info)
    AutofitTextView tvInfo;

    @BindView(R.id.tv_help_1)
    AutofitTextView tvHelp1;
    @BindView(R.id.tv_help_2)
    AutofitTextView tvHelp2;
    @BindView(R.id.tv_help_3)
    AutofitTextView tvHelp3;
    @BindView(R.id.tv_help_4)
    AutofitTextView tvHelp4;
    @BindView(R.id.tv_help_5)
    AutofitTextView tvHelp5;
    @BindView(R.id.tv_help_6)
    AutofitTextView tvHelp6;
    @BindView(R.id.tv_help_7)
    AutofitTextView tvHelp7;
//    @BindView(R.id.tv_font_large)
//    TextView tvFontLarge;


    public HelpDialogFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
//        ivCancel.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        tvInfo.setOnClickListener(this);

//        ivCancel.setVisibility(View.GONE);


        CharSequence t1 = "Click (+) icon to add a new note.";
        SpannableString s1 = new SpannableString(t1);
        s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
        tvHelp1.setText(TextUtils.concat(s1));

        CharSequence t2 = "Tap on notes to view/edit.";
        SpannableString s2 = new SpannableString(t2);
        s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
        tvHelp2.setText(TextUtils.concat(s2));

        CharSequence t3 = "Edit notes by clicking on @ icon.";
        SpannableString spannableString = new SpannableString(t3);
        Drawable d = getResources().getDrawable(R.drawable.ic_edit_red);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(span, spannableString.toString().indexOf("@"),  spannableString.toString().indexOf("@")+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BulletSpan(15), 0, spannableString.length(), 0);
        tvHelp3.setText(spannableString);

        CharSequence t4 = "Click on @ icon to delete the note.";
        SpannableString spannableString1 = new SpannableString(t4);
        Drawable d1 = getResources().getDrawable(R.drawable.ic_delete_red);
        d1.setBounds(0, 0, d1.getIntrinsicWidth(), d1.getIntrinsicHeight());
        ImageSpan span1 = new ImageSpan(d1, ImageSpan.ALIGN_BOTTOM);
        spannableString1.setSpan(span1, spannableString1.toString().indexOf("@"),  spannableString1.toString().indexOf("@")+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new BulletSpan(15), 0, spannableString1.length(), 0);
        tvHelp4.setText(spannableString1);

        CharSequence t5 = "Use menu to view notes in List/Grid.";
        SpannableString s5 = new SpannableString(t5);
        s5.setSpan(new BulletSpan(15), 0, t5.length(), 0);
        tvHelp5.setText(TextUtils.concat(s5));

        CharSequence t6 = "Arrange notes by using the Sort options from the menu.";
        SpannableString s6 = new SpannableString(t6);
        s6.setSpan(new BulletSpan(15), 0, t6.length(), 0);
        tvHelp6.setText(TextUtils.concat(s6));

        CharSequence t7 = "Use menu to exit from the app.";
        SpannableString s7 = new SpannableString(t7);
        s7.setSpan(new BulletSpan(15), 0, t7.length(), 0);
        tvHelp7.setText(TextUtils.concat(s7));

    }

    public void openPlayStore() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
//        if (view == ivCancel) {
//            dismiss();
//        } else
            if (view == ivDone) {
            dismiss();
        }
    }
}