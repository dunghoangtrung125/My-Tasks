package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private MutableLiveData<Long> _navigateToTask = new MutableLiveData<Long>();
    private LiveData<Long> navigateToTask;
    private LiveData<Task> task;

    public LiveData<Long> getNavigateToTask() {
        return _navigateToTask;
    }

    private final LiveData<List<Task>> allTasks;

    public TasksViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAll();
    }

    public LiveData<List<Task>> getAll() {
        return allTasks;
    }

    public void onTaskClicked(Long taskId) {
        _navigateToTask.setValue(taskId);
    }

    public void onTaskNavigated() {
        _navigateToTask.setValue(null);
    }

    public void updateTaskDone(Task task, Boolean state) {
        task.taskDone = state;
        repository.update(task);
    }

    public void updateTaskImportance(Task task, Boolean state) {
        task.importance = state;
        repository.update(task);
    }
}
