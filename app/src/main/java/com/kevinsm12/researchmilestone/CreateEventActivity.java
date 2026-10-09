package com.kevinsm12.researchmilestone;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.concurrent.Executors;

public class CreateEventActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDate;
    private EditText etRoute;
    private EditText etBudget;
    private AppCompatButton btnSubmit;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_event);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createEventRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etRoute = findViewById(R.id.etRoute);
        etBudget = findViewById(R.id.etBudget);
        btnSubmit = findViewById(R.id.btnSubmit);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.boxUploadAd).setOnClickListener(v ->
                Toast.makeText(this, "Ad Image dropzone ready (1080x1920)", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.boxUploadLogo).setOnClickListener(v ->
                Toast.makeText(this, "Brand Logo dropzone ready (SVG/PNG)", Toast.LENGTH_SHORT).show()
        );

        btnSubmit.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String name = etName.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String route = etRoute.getText().toString().trim();
        String budget = etBudget.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Event name is required");
            etName.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            etDate.setError("Date & time is required");
            etDate.requestFocus();
            return;
        }

        if (route.isEmpty()) {
            etRoute.setError("Route details are required");
            etRoute.requestFocus();
            return;
        }

        if (budget.isEmpty()) {
            budget = "$250.00";
        } else if (!budget.startsWith("$")) {
            budget = "$" + budget;
        }

        String description = "Campus run challenge hosted by official sponsor.";

        btnSubmit.setEnabled(false);
        btnSubmit.setText("Processing Sponsorship...");

        String finalBudget = budget;
        Executors.newSingleThreadExecutor().execute(() -> {
            db.eventDao().insert(new EventEntity(name, date, route, finalBudget, description));
            runOnUiThread(() -> {
                Toast.makeText(this, "Event sponsored successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
