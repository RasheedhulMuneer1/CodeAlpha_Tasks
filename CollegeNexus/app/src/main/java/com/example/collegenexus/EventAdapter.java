package com.example.collegenexus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;
    private final Context context;
    private final AppDatabase db;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
        this.db = AppDatabase.getInstance(context); // initialize the database
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventTitle.setText(event.getName());
        holder.eventDate.setText(event.getDate() + " " + event.getTime());
        holder.eventDescription.setText(event.getDescription());


        holder.buttonEdit.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Edit Event")
                    .setMessage("Do you want to edit this event?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // if oK is clicked, start AddEvent activity with event details
                        Intent intent = new Intent(context, AddEvent.class);
                        intent.putExtra("EVENT_ID", event.getId()); // pass event ID for editing
                        intent.putExtra("EVENT_NAME", event.getName()); // pass event name
                        intent.putExtra("EVENT_DATE", event.getDate()); // pass event date
                        intent.putExtra("EVENT_TIME", event.getTime()); // pass event time
                        intent.putExtra("EVENT_DESCRIPTION", event.getDescription()); // to pass event desc
                        context.startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });


        holder.buttonDelete.setOnClickListener(v -> showDeleteConfirmationDialog(event));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, eventDate, eventDescription;
        Button buttonEdit, buttonDelete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    private void showDeleteConfirmationDialog(Event event) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // for deletion of the event
                    deleteEvent(event); // call delete method
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteEvent(Event event) {

        deleteEventFromDatabase(event);

        events.remove(event);
        notifyDataSetChanged();


        Toast.makeText(context, "Event Deleted", Toast.LENGTH_SHORT).show();
    }

    private void deleteEventFromDatabase(Event event) {
        new Thread(() -> db.eventDao().delete(event)).start();
    }

    public void updateEvents(List<Event> newEvents) {
        this.events.clear();
        this.events.addAll(newEvents);
        notifyDataSetChanged();
    }
}
