package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

public class TaskDetailViewModel extends AndroidViewModel {
    TaskRepository repository;
    private final Long taskId;
    private LiveData<Task> task;
    private MutableLiveData<Boolean> _navigateBack = new MutableLiveData<Boolean>(false);
    private LiveData<Boolean> navigateBack;

    public LiveData<Boolean> getNavigateBack() {
        return _navigateBack;
    }

    public TaskDetailViewModel(@NonNull Application application, Long taskId) {
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
        _navigateBack.setValue(true);
    }

    public void deleteTask() {
        repository.delete(task.getValue());
        _navigateBack.setValue(true);
    }

    public void isNavigated() {
        _navigateBack.setValue(false);
    }
}
