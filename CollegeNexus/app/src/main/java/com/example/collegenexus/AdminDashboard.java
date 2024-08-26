package com.example.collegenexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private AppDatabase db;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize the database and event list
        db = AppDatabase.getInstance(this);
        eventList = new ArrayList<>();

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();

        Button addEventButton = findViewById(R.id.button4);
        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, AddEvent.class);
            startActivityForResult(intent, 1);
        });
    }


    private void loadEvents() {
        new Thread(() -> {
            try {
                List<Event> loadedEvents = db.eventDao().getAllEvents(); // Get all events from the database

                runOnUiThread(() -> {
                    eventList.clear(); // for Clear the existing list
                    if (loadedEvents != null) {
                        eventList.addAll(loadedEvents); // To Add all loaded events

                        // Check if eventList is empty and show a Toast if it is
                        if (eventList.isEmpty()) {
                            Toast.makeText(this, "No events found", Toast.LENGTH_SHORT).show();
                        }

                        // Create and set the adapter for the RecyclerView
                        eventAdapter = new EventAdapter(AdminDashboard.this, eventList);
                        recyclerView.setAdapter(eventAdapter);
                    } else {
                        Toast.makeText(this, "Failed to load events", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error loading events", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    public void addEvent(Event newEvent) {
        eventList.add(0, newEvent);
        eventAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Event newEvent = data.getParcelableExtra("NEW_EVENT");
            if (newEvent != null) {
                addEvent(newEvent);
            }
        }
    }
}
