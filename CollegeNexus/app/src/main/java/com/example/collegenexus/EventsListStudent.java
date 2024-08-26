package com.example.collegenexus;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EventsListStudent extends AppCompatActivity {
    private ListView eventListView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list_student);


        eventListView = findViewById(R.id.eventListView);


        db = AppDatabase.getInstance(this);

        loadEvents();
    }

    private void loadEvents() {
        new Thread(() -> {
            final List<Event> events = db.eventDao().getAllEvents();
            runOnUiThread(() -> {

                ArrayAdapter<Event> adapter = new ArrayAdapter<>(EventsListStudent.this, android.R.layout.simple_list_item_1, events);
                eventListView.setAdapter(adapter);
            });
        }).start();
    }
}