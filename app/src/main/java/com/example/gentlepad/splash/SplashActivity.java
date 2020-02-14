package com.example.gentlepad.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gentlepad.activities.MainActivity;
import com.example.gentlepad.R;
import com.example.gentlepad.common.CommonUtils;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    TextView tvAppTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvAppTitle = findViewById(R.id.tv_app_title);
        CommonUtils.setFont(SplashActivity.this, tvAppTitle);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);


    }
}
