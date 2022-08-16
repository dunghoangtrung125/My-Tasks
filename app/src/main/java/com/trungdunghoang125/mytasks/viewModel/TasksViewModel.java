package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    public String newTaskName = "";
    private TaskRepository repository;

    private final LiveData<List<Task>> allTasks;

    public TasksViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAll();
    }

    public LiveData<List<Task>> getAll() {
        return allTasks;
    }

    public void addTask() {
        Task task = new Task();
        task.taskName = newTaskName;
        repository.insert(task);
    }
}
