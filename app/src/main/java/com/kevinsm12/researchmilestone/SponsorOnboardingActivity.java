package com.kevinsm12.researchmilestone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.slider.Slider;

public class SponsorOnboardingActivity extends AppCompatActivity {

    private EditText etCompanyName;
    private EditText etBillingEmail;
    private Spinner spinnerIndustry;
    private Slider sliderBudget;
    private TextView tvBudgetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sponsor_onboarding);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.onboardingRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCompanyName = findViewById(R.id.etCompanyName);
        etBillingEmail = findViewById(R.id.etBillingEmail);
        spinnerIndustry = findViewById(R.id.spinnerIndustry);
        sliderBudget = findViewById(R.id.sliderBudget);
        tvBudgetValue = findViewById(R.id.tvBudgetValue);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.boxUploadLogo).setOnClickListener(v ->
                Toast.makeText(this, "Logo upload is out of scope for research prototype", Toast.LENGTH_SHORT).show()
        );

        String[] industries = {"Sports & Fitness", "Apparel & Footwear", "Food & Nutrition", "Tech & Wearables", "Local Business"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, industries);
        spinnerIndustry.setAdapter(adapter);

        sliderBudget.addOnChangeListener((slider, value, fromUser) -> {
            int budget = (int) value;
            tvBudgetValue.setText("$" + String.format("%,d", budget));
        });

        findViewById(R.id.btnCompleteProfile).setOnClickListener(v -> validateAndFinish());
    }

    private void validateAndFinish() {
        String company = etCompanyName.getText().toString().trim();
        String email = etBillingEmail.getText().toString().trim();

        if (company.isEmpty()) {
            etCompanyName.setError("Company name is required");
            etCompanyName.requestFocus();
            return;
        }

        if (email.isEmpty() || !email.contains("@")) {
            etBillingEmail.setError("Valid billing email required");
            etBillingEmail.requestFocus();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("stride_prefs", MODE_PRIVATE);
        prefs.edit().putString("sponsor_name", company).apply();

        Intent intent = new Intent(SponsorOnboardingActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
