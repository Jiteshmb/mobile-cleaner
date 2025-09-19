package com.hivas.mobilecleaner.home;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;  // IMPORTANT: Use AppCompatActivity

import com.hivas.mobilecleaner.R;

public class HomeActivity extends AppCompatActivity {  // NOT Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create simple layout programmatically
        TextView textView = new TextView(this);
        textView.setText("Home Screen - Mobile Cleaner App\n\nOnboarding Complete!");
        textView.setTextSize(24);
        textView.setPadding(100, 200, 100, 100);
        textView.setGravity(android.view.Gravity.CENTER);

        setContentView(textView);
    }
}
