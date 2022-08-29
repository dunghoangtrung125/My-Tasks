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

    public void addTask(int ID, String newTaskName, String newTaskDescription, Boolean newTaskDone, Boolean newTaskImportance, Long timeCreate, Boolean isSetAlert, int hour, int minute, Boolean isDailyTask) {
        Task task = new Task(ID, newTaskName, newTaskDescription, newTaskDone, newTaskImportance, timeCreate, isSetAlert, hour, minute, isDailyTask);
        repository.insert(task);
    }
}
