package com.hivas.mobilecleaner.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.hivas.mobilecleaner.R;

public class ScanningActivity extends AppCompatActivity {

    private TextView tvScanStatus;
    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        initViews();
        // Start scanning immediately - NO permission requests
        startBackgroundScanning();
    }

    private void initViews() {
        tvScanStatus = findViewById(R.id.tv_scan_status);
    }

    private void startBackgroundScanning() {
        tvScanStatus.setText("Starting scan...");

        OneTimeWorkRequest scanRequest = new OneTimeWorkRequest.Builder(FileScanWorker.class)
                .build();

        workManager = WorkManager.getInstance(this);
        workManager.enqueue(scanRequest);

        workManager.getWorkInfoByIdLiveData(scanRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            updateScanProgress(workInfo);
                        }
                    }
                });
    }

    private void updateScanProgress(WorkInfo workInfo) {
        Data progress = workInfo.getProgress();
        String status = progress.getString("status");

        if (status != null) {
            tvScanStatus.setText(status);
        }

        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
            Intent intent = new Intent(this, ScanResultsActivity.class);
            Data outputData = workInfo.getOutputData();
            intent.putExtra("scan_results", outputData.getString("results"));
            intent.putExtra("image_count", outputData.getInt("image_count", 0));
            intent.putExtra("video_count", outputData.getInt("video_count", 0));
            intent.putExtra("download_count", outputData.getInt("download_count", 0));
            intent.putExtra("cache_size", outputData.getLong("cache_size", 0));
            startActivity(intent);
            finish();
        }
    }
}
