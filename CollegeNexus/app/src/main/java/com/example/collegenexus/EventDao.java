package com.example.collegenexus;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);

    @Update
    void update(Event event); // method to update an existing event

    @Delete
    void delete(Event event); // method to delete an existing event

    @Query("SELECT * FROM events")
    List<Event> getAllEvents();
}
