package com.kevinsm12.researchmilestone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private TextView tvSponsorName;
    private TextView tvUpcomingCount;
    private TextView tvEmptyState;
    private LinearLayout layoutMetrics;
    private LinearLayout layoutPastEvents;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboardRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        tvSponsorName = findViewById(R.id.tvSponsorName);
        tvUpcomingCount = findViewById(R.id.tvUpcomingCount);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        layoutMetrics = findViewById(R.id.layoutMetrics);
        layoutPastEvents = findViewById(R.id.layoutPastEvents);
        recyclerView = findViewById(R.id.recyclerViewUpcoming);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnSponsorNew).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CreateEventActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSponsorIdentity();
        loadEvents();
    }

    private void loadSponsorIdentity() {
        SharedPreferences prefs = getSharedPreferences("stride_prefs", MODE_PRIVATE);
        String name = prefs.getString("sponsor_name", "Powerade Inc.");
        tvSponsorName.setText(name);
    }

    private void loadEvents() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<EventEntity> events = db.eventDao().getAllEvents();
            runOnUiThread(() -> {
                if (events.isEmpty()) {
                    layoutMetrics.setVisibility(View.GONE);
                    layoutPastEvents.setVisibility(View.GONE);
                    tvEmptyState.setVisibility(View.VISIBLE);
                    tvUpcomingCount.setText("0 SCHEDULED");
                } else {
                    layoutMetrics.setVisibility(View.VISIBLE);
                    layoutPastEvents.setVisibility(View.VISIBLE);
                    tvEmptyState.setVisibility(View.GONE);
                    tvUpcomingCount.setText(events.size() + " SCHEDULED");
                }
                adapter.updateData(events);
            });
        });
    }
}
