package com.example.gentlepad.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.gentlepad.R;
import com.example.gentlepad.dialogs.FontSettingDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mTopToolbar;
    @BindView(R.id.ll_font)
    LinearLayout llFont;
    @BindView(R.id.ll_sort)
    LinearLayout llSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        mTopToolbar =  findViewById(R.id.toolbar);
        mTopToolbar.setTitle("Settings");
        setSupportActionBar(mTopToolbar);
        llFont.setOnClickListener(this);
        llSort.setOnClickListener(this);


    }

    private void setFontSize() {
        FontSettingDialogFragment fontSettingDialogFragment = new FontSettingDialogFragment();
        fontSettingDialogFragment.show(getSupportFragmentManager(), "FontSettingDialogFragment");
    }

    private void sortNotesBy() {
        FontSettingDialogFragment fontSettingDialogFragment = new FontSettingDialogFragment();
        fontSettingDialogFragment.show(getSupportFragmentManager(), "FontSettingDialogFragment");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ll_font) {
            setFontSize();
        } else if(view.getId() == R.id.ll_sort) {
            sortNotesBy();
        }
    }
}
