package com.trungdunghoang125.mytasks.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDAO dao;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Task>> allDoneTasks;

    public TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getInstance(application);
        dao = db.dao();
        allTasks = dao.getAll();
        allDoneTasks = dao.getDoneTasks();
    }

    public LiveData<List<Task>> getAll() {
        return allTasks;
    }

    public LiveData<List<Task>> getAllDoneTasks() {
        return allDoneTasks;
    }

    public void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(task);
        });
    }

    public LiveData<Task> get(int taskId) {
        return dao.get(taskId);
    }

    public Task getTaskByID(int taskId) {
        return dao.getTaskByID(taskId);
    }

    public void update(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            dao.update(task);
        });
    }

    public void delete(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(task);
        });
    }
}
