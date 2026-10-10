package com.kevinsm12.researchmilestone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private boolean isSponsorSelected = true;
    private View cardAthlete;
    private View cardSponsor;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSharedPreferences("stride_prefs", MODE_PRIVATE).edit().clear().apply();
        AppDatabase db = AppDatabase.getInstance(this);
        Executors.newSingleThreadExecutor().execute(db::clearAllTables);

        cardAthlete = findViewById(R.id.cardAthlete);
        cardSponsor = findViewById(R.id.cardSponsor);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        cardAthlete.setOnClickListener(v -> {
            isSponsorSelected = true;
            cardAthlete.setBackgroundResource(R.drawable.bg_card_selected);
            cardSponsor.setBackgroundResource(R.drawable.bg_card_unselected);
            Toast.makeText(this, "Athlete onboarding is planned for the team PoC release", Toast.LENGTH_SHORT).show();
        });

        cardSponsor.setOnClickListener(v -> {
            isSponsorSelected = false;
            cardSponsor.setBackgroundResource(R.drawable.bg_card_selected);
            cardAthlete.setBackgroundResource(R.drawable.bg_card_unselected);
        });

        findViewById(R.id.btnGoogle).setOnClickListener(v ->
                Toast.makeText(this, "Google single sign-on not implemented in this milestone", Toast.LENGTH_SHORT).show()
        );
        findViewById(R.id.btnApple).setOnClickListener(v ->
                Toast.makeText(this, "Apple single sign-on not implemented in this milestone", Toast.LENGTH_SHORT).show()
        );
        findViewById(R.id.tvLogInLink).setOnClickListener(v ->
                Toast.makeText(this, "Existing accounts will be retrieved from MySQL in team PoC", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.btnCreateAccount).setOnClickListener(v -> validateAndProceed());
    }

    private void validateAndProceed() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || !email.contains("@")) {
            etEmail.setError("Valid university email required");
            etEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        if (!isSponsorSelected) {
            Toast.makeText(this, "Please select 'Join as Athlete' for this prototype slice", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
