package com.kevinsm12.researchmilestone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Dashboard screen.
 */
public class DashboardActivity extends AppCompatActivity {

    private LinearLayout runStartedWidget;
    private TextView runTimerText;
    private TextView runDistanceText;
    private TextView runXpText;
    private Button startRunButton;
    private Button btnPauseRun;
    private Button btnStopRun;
    private ScrollView dashboardScroll;

    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private boolean isRunning = false;
    private boolean isPaused = false;
    private int secondsElapsed = 0;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning && !isPaused) {
                secondsElapsed++;
                updateTimerUI();
                timerHandler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Bind views
        dashboardScroll = findViewById(R.id.dashboardScroll);
        runStartedWidget = findViewById(R.id.runStartedWidget);
        runTimerText = findViewById(R.id.runTimerText);
        runDistanceText = findViewById(R.id.runDistanceText);
        runXpText = findViewById(R.id.runXpText);
        startRunButton = findViewById(R.id.startRunButton);
        btnPauseRun = findViewById(R.id.btnPauseRun);
        btnStopRun = findViewById(R.id.btnStopRun);

        // The event card at the bottom of the dashboard opens the event details screen
        View eventCard = findViewById(R.id.upcomingEventRow);
        if (eventCard != null) {
            eventCard.setOnClickListener(view -> {
                Intent intent = new Intent(DashboardActivity.this, EventDetailActivity.class);
                startActivity(intent);
            });
            eventCard.setClickable(true);
            eventCard.setFocusable(true);
        }

        // Start Run button action
        if (startRunButton != null) {
            startRunButton.setOnClickListener(v -> {
                if (!isRunning) {
                    startRun();
                } else {
                    if (dashboardScroll != null) {
                        dashboardScroll.smoothScrollTo(0, 0);
                    }
                }
            });
        }

        // Pause Run action
        if (btnPauseRun != null) {
            btnPauseRun.setOnClickListener(v -> {
                isPaused = !isPaused;
                if (isPaused) {
                    btnPauseRun.setText("▶ Resume");
                    Toast.makeText(this, "Run paused", Toast.LENGTH_SHORT).show();
                } else {
                    btnPauseRun.setText("⏸ Pause");
                    Toast.makeText(this, "Run resumed!", Toast.LENGTH_SHORT).show();
                    timerHandler.postDelayed(timerRunnable, 1000);
                }
            });
        }

        // Stop Run action
        if (btnStopRun != null) {
            btnStopRun.setOnClickListener(v -> stopRun());
        }

        // Ping Nearby action
        View pingButton = findViewById(R.id.pingNearbyButton);
        if (pingButton != null) {
            pingButton.setOnClickListener(v ->
                    Toast.makeText(this, "📡 Garnet Ping sent! 3 runners notified nearby.", Toast.LENGTH_SHORT).show()
            );
        }

        // Bottom Navigation actions
        View navDiscover = findViewById(R.id.navDiscover);
        if (navDiscover != null) {
            navDiscover.setOnClickListener(v ->
                Toast.makeText(this, "📡 Discover page planned for team PoC release.", Toast.LENGTH_SHORT).show()
            );
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v ->
                Toast.makeText(this, "📡 Profile page planned for team PoC release.", Toast.LENGTH_SHORT).show()
            );
        }
    }

    private void startRun() {
        isRunning = true;
        isPaused = false;
        secondsElapsed = 0;

        if (runStartedWidget != null) {
            runStartedWidget.setVisibility(View.VISIBLE);
        }

        if (startRunButton != null) {
            startRunButton.setText("🏃 Running...");
        }

        if (btnPauseRun != null) {
            btnPauseRun.setText("⏸ Pause");
        }

        updateTimerUI();

        if (dashboardScroll != null) {
            dashboardScroll.smoothScrollTo(0, 0);
        }

        Toast.makeText(this, "Run started! Tracking your stride...", Toast.LENGTH_SHORT).show();

        timerHandler.removeCallbacks(timerRunnable);
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    private void stopRun() {
        isRunning = false;
        isPaused = false;
        timerHandler.removeCallbacks(timerRunnable);

        if (runStartedWidget != null) {
            runStartedWidget.setVisibility(View.GONE);
        }

        if (startRunButton != null) {
            startRunButton.setText("▶ Start Run");
        }

        double distance = secondsElapsed * 0.002;
        String summary = String.format(Locale.US, "Run logged! Distance: %.2f mi | Time: %s", distance, formatTime(secondsElapsed));
        Toast.makeText(this, summary, Toast.LENGTH_LONG).show();

        secondsElapsed = 0;
    }

    private void updateTimerUI() {
        if (runTimerText != null) {
            runTimerText.setText(formatTime(secondsElapsed));
        }

        double miles = secondsElapsed * 0.002;
        if (runDistanceText != null) {
            runDistanceText.setText(String.format(Locale.US, "%.2f mi", miles));
        }

        if (runXpText != null) {
            int xp = 50 + (int) (miles * 100);
            runXpText.setText("+" + xp + " XP");
        }
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(timerRunnable);
    }
}
