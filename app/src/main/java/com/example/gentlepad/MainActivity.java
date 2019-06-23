package com.example.gentlepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar.setTitle(getString(R.string.app_title));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
