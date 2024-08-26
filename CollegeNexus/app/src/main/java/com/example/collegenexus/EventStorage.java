package com.example.collegenexus;

import java.util.ArrayList;
import java.util.List;

public class EventStorage {
    private static EventStorage instance;
    private List<Event> events;

    private EventStorage() {
        events = new ArrayList<>();
    }

    public static synchronized EventStorage getInstance() {
        if (instance == null) {
            instance = new EventStorage();
        }
        return instance;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }
}
