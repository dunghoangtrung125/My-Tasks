package com.trungdunghoang125.mytasks.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TaskDetailViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final Application application;
    private final int taskId;

    public TaskDetailViewModelFactory(@NonNull Application application, int taskId) {
        this.application = application;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TaskDetailViewModel.class) {
            return (T) new TaskDetailViewModel(application, taskId);
        }
        return null;
    }
}
