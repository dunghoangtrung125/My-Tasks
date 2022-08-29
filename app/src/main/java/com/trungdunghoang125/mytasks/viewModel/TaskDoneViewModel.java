package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;

import java.util.List;

public class TaskDoneViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private MutableLiveData<Integer> _navigateToTask = new MutableLiveData<Integer>();
    private LiveData<Long> navigateToTask;

    public LiveData<Integer> getNavigateToTask() {
        return _navigateToTask;
    }

    private final LiveData<List<Task>> allDoneTasks;

    public TaskDoneViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        allDoneTasks = repository.getAllDoneTasks();
    }

    public LiveData<List<Task>> getAllDoneTasks() {
        return allDoneTasks;
    }

    public void onTaskClicked(int taskId) {
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
