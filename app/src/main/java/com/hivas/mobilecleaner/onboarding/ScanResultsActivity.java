package com.hivas.mobilecleaner.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hivas.mobilecleaner.R;
import com.hivas.mobilecleaner.onboarding.JunkCleanerActivity;
import java.util.Random;

public class ScanResultsActivity extends AppCompatActivity {

    private static final String TAG = "ScanResultsActivity";

    private TextView tvTitle, tvSubtitle, tvDisclaimer, tvSkip;
    private Button btnCleanNow;
    private ConstraintLayout rootLayout;

    private int imageCount;
    private int videoCount;
    private int downloadCount;
    private long cacheSize;
    private long totalCleanableSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_results);

        Log.d(TAG, "ScanResultsActivity onCreate called");

        initViews();
        loadRealisticScanResults();
        setupClickListeners();
    }

    private void initViews() {
        Log.d(TAG, "Initializing views...");

        // Initialize all views - MAKE SURE IDs MATCH YOUR XML
        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);
        tvDisclaimer = findViewById(R.id.tv_disclaimer);
        tvSkip = findViewById(R.id.tv_skip);
        btnCleanNow = findViewById(R.id.btn_clean_now);
        rootLayout = findViewById(R.id.root_layout);

        // CHECK FOR NULL VIEWS
        if (btnCleanNow == null) {
            Log.e(TAG, "ERROR: btnCleanNow is null! Check your XML layout has id 'btn_clean_now'");
        } else {
            Log.d(TAG, "btnCleanNow found successfully");
        }
    }

    private void loadRealisticScanResults() {
        Log.d(TAG, "Loading scan results...");

        Intent intent = getIntent();
        imageCount = intent.getIntExtra("image_count", 5); // Default values
        videoCount = intent.getIntExtra("video_count", 3);
        downloadCount = intent.getIntExtra("download_count", 2);
        cacheSize = intent.getLongExtra("cache_size", 139 * 1024); // Default 139KB

        Log.d(TAG, "Cache size: " + cacheSize + " bytes");

        totalCleanableSize = calculateRealisticCleanableSize();
        Log.d(TAG, "Total cleanable size: " + totalCleanableSize + " bytes");

        updateCleanButtonText();
    }

    private long calculateRealisticCleanableSize() {
        long cleanableSize = cacheSize;

        cleanableSize += (imageCount * getRandomInt(10, 30) * 1024);
        cleanableSize += (videoCount * getRandomInt(50, 150) * 1024);
        cleanableSize += (downloadCount * getRandomInt(5, 15) * 1024);
        cleanableSize += getRandomInt(20, 80) * 1024;

        cleanableSize = Math.max(cleanableSize, 100 * 1024);
        cleanableSize = Math.min(cleanableSize, 5 * 1024 * 1024);

        return cleanableSize;
    }

    private int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private void updateCleanButtonText() {
        if (btnCleanNow != null) {
            String formattedSize = Formatter.formatFileSize(this, totalCleanableSize);
            String buttonText = "Clean Now (" + formattedSize + ")";
            btnCleanNow.setText(buttonText);
            Log.d(TAG, "Button text updated to: " + buttonText);
        } else {
            Log.e(TAG, "Cannot update button text - btnCleanNow is null");
        }
    }

    private void setupClickListeners() {
        Log.d(TAG, "Setting up click listeners...");

        // Clean Now button click - CHECK FOR NULL
        if (btnCleanNow != null) {
            btnCleanNow.setOnClickListener(v -> {
                Log.d(TAG, "Clean Now button clicked!");
                performClean();
            });
            Log.d(TAG, "Clean Now click listener set successfully");
        } else {
            Log.e(TAG, "ERROR: Cannot set click listener - btnCleanNow is null");
        }

        // Skip button click - CHECK FOR NULL
        if (tvSkip != null) {
            tvSkip.setOnClickListener(v -> {
                Log.d(TAG, "Skip button clicked!");
                completeOnboarding();
            });
            Log.d(TAG, "Skip click listener set successfully");
        } else {
            Log.e(TAG, "ERROR: Cannot set click listener - tvSkip is null");
        }
    }

    private void performClean() {
        Log.d(TAG, "performClean() called");

        // Mark onboarding as complete
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        prefs.edit().putBoolean("onboarding_done", true).apply();
        Log.d(TAG, "Onboarding marked as complete");

        // Navigate to JunkCleanerActivity
        try {
            Intent intent = new Intent(this, JunkCleanerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d(TAG, "Starting JunkCleanerActivity...");
            startActivity(intent);
            finish();
            Log.d(TAG, "Navigation successful");
        } catch (Exception e) {
            Log.e(TAG, "ERROR navigating to JunkCleanerActivity: " + e.getMessage(), e);
        }
    }

    private void completeOnboarding() {
        Log.d(TAG, "completeOnboarding() called");

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        prefs.edit().putBoolean("onboarding_done", true).apply();

        try {
            Intent intent = new Intent(this, JunkCleanerActivity.class);
            Log.d(TAG, "Starting JunkCleanerActivity...");
            startActivity(intent);
            finish();
            Log.d(TAG, "Navigation successful");
        } catch (Exception e) {
            Log.e(TAG, "ERROR navigating to JunkCleanerActivity: " + e.getMessage(), e);
        }
    }
}
