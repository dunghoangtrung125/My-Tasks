package com.trungdunghoang125.mytasks.view.adapter;

import com.trungdunghoang125.mytasks.model.Task;

public interface ItemClick {
    public void onItemClick(int pos);

    public void onCbTaskDoneClick(Task task, Boolean state);

    public void onCbImportanceClick(Task task, Boolean isImportant);
}
