package com.trungdunghoang125.mytasks.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public Long taskId;

    @ColumnInfo(name = "task_name")
    public String taskName = "";

    @ColumnInfo(name = "task_detail")
    public String taskDetail = "";

    @ColumnInfo(name = "task_done")
    public Boolean taskDone = false;

    @ColumnInfo(name = "task_importance")
    public Boolean importance = false;
}
