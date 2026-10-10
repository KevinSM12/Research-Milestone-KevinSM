package com.kevinsm12.researchmilestone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailActivity extends AppCompatActivity {

    private Button rsvpButton;
    private Button cancelRsvpButton;
    private Button shareEventButton;
    private TextView eventAttending;

    private boolean isRsvped = false;
    private int attendingCount = 84;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        View backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        rsvpButton = findViewById(R.id.rsvpButton);
        cancelRsvpButton = findViewById(R.id.cancelRsvpButton);
        shareEventButton = findViewById(R.id.shareEventButton);
        eventAttending = findViewById(R.id.eventAttending);

        if (rsvpButton != null) {
            rsvpButton.setOnClickListener(v -> {
                if (!isRsvped) {
                    isRsvped = true;
                    rsvpButton.setText("✓ RSVP'd to Event");
                    attendingCount++;
                    if (eventAttending != null) {
                        eventAttending.setText(attendingCount + " Runners");
                    }
                    Toast.makeText(this, "RSVP Confirmed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You are already registered for this event!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (cancelRsvpButton != null) {
            cancelRsvpButton.setOnClickListener(v -> {
                if (isRsvped) {
                    isRsvped = false;
                    rsvpButton.setText("＋ RSVP to Event");
                    attendingCount--;
                    if (eventAttending != null) {
                        eventAttending.setText(attendingCount + " Runners");
                    }
                    Toast.makeText(this, "RSVP Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You haven't RSVP'd to this event yet", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (shareEventButton != null) {
            shareEventButton.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Join me at Powerade Arena Sprints today at 5:30 PM on STRIDE!");
                startActivity(Intent.createChooser(shareIntent, "Share Event via"));
            });
        }
    }
}
