package com.kevinsm12.researchmilestone;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analytics);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.analyticsRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String eventName = getIntent().getStringExtra("event_name");
        if (eventName != null && !eventName.isEmpty()) {
            TextView tvSubtitle = findViewById(R.id.tvAnalyticsSubtitle);
            tvSubtitle.setText(eventName.toUpperCase());
        }

        findViewById(R.id.btnAnalyticsBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnShareReport).setOnClickListener(v ->
                Toast.makeText(this, "Telemetry export will unlock upon challenge completion", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.btnExportReport).setOnClickListener(v ->
                Toast.makeText(this, "Full report unavailable: No telemetry recorded yet", Toast.LENGTH_SHORT).show()
        );
    }
}
