package com.example.collegenexus;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventsListAdmin extends AppCompatActivity {
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list_admin);

        db = AppDatabase.getInstance(this);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents(); // load events when activity is created
    }

    private void loadEvents() {
        new Thread(() -> {
            final List<Event> events = db.eventDao().getAllEvents(); // fetch events
            runOnUiThread(() -> {

                eventAdapter = new EventAdapter(EventsListAdmin.this, events);
                eventRecyclerView.setAdapter(eventAdapter);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();  // reload
    }
}
