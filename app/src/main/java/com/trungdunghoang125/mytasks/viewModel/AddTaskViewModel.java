package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

public class AddTaskViewModel extends AndroidViewModel {
    public String newTaskName = "";
    private TaskRepository repository;

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void addTask(String newTaskName) {
        Task task = new Task();
        task.taskName = newTaskName;
        repository.insert(task);
    }
}
