package com.hivas.mobilecleaner.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hivas.mobilecleaner.R;

public class OnboardingActivity extends AppCompatActivity {

    private Button btnContinue;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnContinue = findViewById(R.id.btn_continue);
        tvSkip = findViewById(R.id.tv_skip);
    }

    private void setupClickListeners() {
            btnContinue.setOnClickListener(v -> {
                Intent intent = new Intent(this, ScanningActivity.class);
                startActivity(intent);
            });


        tvSkip.setOnClickListener(v -> {
            // Skip onboarding and go to home
            // TODO: Navigate to HomeActivity and set onboarding_done = true
            finish();
        });


    }
}
