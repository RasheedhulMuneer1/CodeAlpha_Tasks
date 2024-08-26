
package com.example.collegenexus;

import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventViewModel extends ViewModel {
    private final AppDatabase db;

    private final ExecutorService executorService;

    public EventViewModel(@NonNull AppDatabase db) {
        this.db = db;

        this.executorService = Executors.newSingleThreadExecutor();
    }


    public void insert(Event event) {
        executorService.execute(() -> db.eventDao().insert(event));
    }

    public void update(Event event) {
        executorService.execute(() -> db.eventDao().update(event));
    }

    public void delete(Event event) {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}


