package com.example.gentlepad.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gentlepad.R;
import com.example.gentlepad.Utilities.Constants;
import com.example.gentlepad.Utilities.Prefs;
import com.example.gentlepad.common.CommonUtils;
import com.example.gentlepad.dialogs.FontSettingDialogFragment;
import com.example.gentlepad.dialogs.SortByDialogFragment;
import com.example.gentlepad.listeners.OnResultListener;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mTopToolbar;
    @BindView(R.id.ll_font)
    LinearLayout llFont;
    @BindView(R.id.ll_sort)
    LinearLayout llSort;
    @BindView(R.id.tv_font_size)
    TextView tvFontSize;
    String fontSizeString;
    String currentFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Prefs.initPrefs(this);
        mTopToolbar = findViewById(R.id.toolbar);
        mTopToolbar.setTitle("Settings");
        setSupportActionBar(mTopToolbar);
        llFont.setOnClickListener(this);
        llSort.setOnClickListener(this);
        if (Prefs.getPreferences() != null) {
            tvFontSize.setText(Prefs.getString(Constants.FONT_SIZE, Constants.SMALL));
        }

        /*if (!TextUtils.isEmpty(tvFontSize.getText().toString())) {
            currentFontSize = tvFontSize.getText().toString();
        } else {
            currentFontSize = Prefs.getString(Constants.FONT_SIZE, Constants.SMALL);
            tvFontSize.setText(currentFontSize);
        }*/


    }

    private void setFontSize() {
        currentFontSize = tvFontSize.getText().toString();
        FontSettingDialogFragment fontSettingDialogFragment = new FontSettingDialogFragment(currentFontSize, new OnResultListener() {
            @Override
            public void getResult(Object object, boolean isSuccess) {
                if (isSuccess) {
                    fontSizeString = (String) object;
                    CommonUtils.showToastMessage(SettingsActivity.this, "String: " + fontSizeString);
                    tvFontSize.setText(fontSizeString);
                    Prefs.putString(Constants.FONT_SIZE, fontSizeString);

                    /*
                    * Set font size to intent, it will send
                    * to the resulting intent i.e., MainActivity
                    * */
                    Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
                    resultIntent.putExtra(Constants.FONT_SIZE, fontSizeString);
                    setResult(RESULT_OK, resultIntent);
                }
            }
        });
        fontSettingDialogFragment.show(getSupportFragmentManager(), "FontSettingDialogFragment");
    }

    private void sortNotesBy() {
        SortByDialogFragment sortByDialogFragment = new SortByDialogFragment();
        sortByDialogFragment.show(getSupportFragmentManager(), "SortByDialogFragment");
    }

    private void setFont() {
        if (fontSizeString.equalsIgnoreCase(Constants.SMALL)) {
            tvFontSize.setText(fontSizeString);
        } else if (fontSizeString.equalsIgnoreCase(Constants.MEDIUM)) {
            tvFontSize.setText(fontSizeString);
        } else if (fontSizeString.equalsIgnoreCase(Constants.LARGE)) {
            tvFontSize.setText(fontSizeString);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_font) {
            setFontSize();
        } else if (view.getId() == R.id.ll_sort) {
            sortNotesBy();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
