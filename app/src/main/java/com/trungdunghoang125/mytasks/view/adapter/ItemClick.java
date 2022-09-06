package com.trungdunghoang125.mytasks.view.adapter;

import com.trungdunghoang125.mytasks.model.Task;

public interface ItemClick {
    void onItemClick(int pos);

    void onCbTaskDoneClick(Task task, Boolean state);

    void onCbImportanceClick(Task task, Boolean isImportant);
}
