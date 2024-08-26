package com.example.collegenexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class AddEvent extends AppCompatActivity {
    private static final String TAG = "AddEvent";
    private EditText eventNameEditText, eventDescriptionEditText, eventDateEditText, eventTimeEditText;
    private AppDatabase db;
    private int eventId;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        db = AppDatabase.getInstance(this);
        eventNameEditText = findViewById(R.id.editTextText3);
        eventDescriptionEditText = findViewById(R.id.editTextText5);
        eventDateEditText = findViewById(R.id.editTextDate2);
        eventTimeEditText = findViewById(R.id.editTextTime2);

        Intent intent = getIntent();
        eventId = intent.getIntExtra("EVENT_ID", -1);
        if (eventId != -1) {
            isEditMode = true;
            eventNameEditText.setText(intent.getStringExtra("EVENT_NAME"));
            eventDescriptionEditText.setText(intent.getStringExtra("EVENT_DESCRIPTION"));
            eventDateEditText.setText(intent.getStringExtra("EVENT_DATE"));
            eventTimeEditText.setText(intent.getStringExtra("EVENT_TIME"));
        }

        Button addButton = findViewById(R.id.button10);
        addButton.setOnClickListener(v -> {
            if (isEditMode) {
                updateEvent();
            } else {
                addEvent();
            }
        });

        Button cancelButton = findViewById(R.id.button9);
        cancelButton.setOnClickListener(v -> clearInputFields());
    }

    private void addEvent() {
        String name = eventNameEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();
        String time = eventTimeEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
        } else {
            Event event = new Event(name, description, date, time);
            Log.d(TAG, "Event created: " + name);

            new Thread(() -> {
                db.eventDao().insert(event);
                Log.d(TAG, "Event inserted: " + name);

                runOnUiThread(() -> {
                    Toast.makeText(AddEvent.this, "Event Added Successfully: " + name, Toast.LENGTH_SHORT).show();
                    sendNotificationToAllUsers(name);  // Send notification to all users
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("NEW_EVENT", event);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                });
            }).start();
        }
    }

    private void updateEvent() {
        String name = eventNameEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();
        String time = eventTimeEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
        } else {
            Event event = new Event(eventId, name, description, date, time);
            Log.d(TAG, "Event updated: " + name);

            new Thread(() -> {
                db.eventDao().update(event);
                Log.d(TAG, "Event updated in database: " + name);

                runOnUiThread(() -> {
                    Toast.makeText(AddEvent.this, "Event Updated Successfully: " + name, Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_EVENT", event);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                });
            }).start();
        }
    }

    private void clearInputFields() {
        eventNameEditText.setText("");
        eventDescriptionEditText.setText("");
        eventDateEditText.setText("");
        eventTimeEditText.setText("");
    }

    private void sendNotificationToAllUsers(String eventName) {
        RemoteMessage message = new RemoteMessage.Builder("events")
                .addData("title", "New Event")
                .addData("body", "Check out the new event: " + eventName)
                .build();

       // FirebaseMessaging.getInstance().send(message);
        Log.d(TAG, "Notification sent for event: " + eventName);
    }
}
