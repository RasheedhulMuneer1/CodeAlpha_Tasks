package com.example.collegenexus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class EventListAdapter extends BaseAdapter {

    private final Context context;
    private final List<Event> events;

    public EventListAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        }

        Event event = events.get(position);

        TextView eventTitle = convertView.findViewById(R.id.eventTitle);
        TextView eventDate = convertView.findViewById(R.id.eventDate);
        TextView eventDescription = convertView.findViewById(R.id.eventDescription);

        eventTitle.setText(event.getName());
        eventDate.setText(event.getDate() + " " + event.getTime());
        eventDescription.setText(event.getDescription());

        return convertView;
    }
}
