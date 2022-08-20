package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

public class AddTaskViewModel extends AndroidViewModel {
    public String newTaskName = "";
    public String newTaskDescription = "";
    private TaskRepository repository;

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void addTask(String newTaskName, String newTaskDescription, Boolean newTaskImportance) {
        Task task = new Task();
        task.taskName = newTaskName;
        task.taskDetail = newTaskDescription;
        task.importance = newTaskImportance;
        repository.insert(task);
    }
}
