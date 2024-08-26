package com.example.collegenexus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditEventActivity extends AppCompatActivity {

    private EditText editEventTitle, editEventDate, editEventTime, editEventDescription;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        // Initialize EditText fields and Save button
        editEventTitle = findViewById(R.id.editEventTitle);
        editEventDate = findViewById(R.id.editEventDate);
        editEventTime = findViewById(R.id.editEventTime);
        editEventDescription = findViewById(R.id.editEventDescription);
        buttonSave = findViewById(R.id.buttonSave);

        // Retrieve the event ID passed from the intent
        int eventId = getIntent().getIntExtra("EVENT_ID", -1);



        buttonSave.setOnClickListener(v -> {
            saveEvent(eventId);
        });
    }

    private void saveEvent(int eventId) {
        String updatedTitle = editEventTitle.getText().toString().trim();
        String updatedDate = editEventDate.getText().toString().trim();
        String updatedTime = editEventTime.getText().toString().trim();
        String updatedDescription = editEventDescription.getText().toString().trim();


        if (updatedTitle.isEmpty() || updatedDate.isEmpty() || updatedTime.isEmpty() || updatedDescription.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

         // confirmation message
        Toast.makeText(this, "Event updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
