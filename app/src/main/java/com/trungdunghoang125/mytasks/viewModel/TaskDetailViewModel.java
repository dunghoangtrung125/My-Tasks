package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

public class TaskDetailViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private final int taskId;
    private LiveData<Task> task;
    private MutableLiveData<Boolean> navigateBack = new MutableLiveData<Boolean>(false);

    public LiveData<Boolean> getNavigateBack() {
        return navigateBack;
    }

    public TaskDetailViewModel(@NonNull Application application, int taskId) {
        super(application);
        this.taskId = taskId;
        repository = new TaskRepository(application);
        task = repository.get(taskId);
    }

    public LiveData<Task> getTask() {
        return task;
    }

    public void updateTask() {
        repository.update(task.getValue());
        navigateBack.setValue(true);
    }

    public void deleteTask() {
        repository.delete(task.getValue());
        navigateBack.setValue(true);
    }

    public void isNavigated() {
        navigateBack.setValue(false);
    }
}
