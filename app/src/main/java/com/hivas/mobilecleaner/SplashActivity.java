package com.hivas.mobilecleaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.core.splashscreen.SplashScreen;

public class SplashActivity extends Activity {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_ONBOARDING_DONE = "onboarding_done";
    private static final long SPLASH_DELAY_MS = 5000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Install the system splash (fast startup)
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // Show your custom splash layout (with logo, text, progress bar)
        setContentView(R.layout.activity_splash);

        // Delay before moving to the next screen
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean onboardingDone = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    .getBoolean(KEY_ONBOARDING_DONE, false);

            // Decide which screen to go to next
            Class<?> next = onboardingDone
                    ? com.hivas.mobilecleaner.home.HomeActivity.class
                    : com.hivas.mobilecleaner.onboarding.OnboardingActivity.class;

            startActivity(new Intent(this, next));
            finish();
        }, SPLASH_DELAY_MS);
    }
}
