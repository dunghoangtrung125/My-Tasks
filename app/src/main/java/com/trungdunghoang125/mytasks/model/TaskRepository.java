package com.trungdunghoang125.mytasks.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDAO dao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getInstance(application);
        dao = db.dao();
        allTasks = dao.getAll();
    }

    public LiveData<List<Task>> getAll() {
        return allTasks;
    }

    public void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(task);
        });
    }
}
