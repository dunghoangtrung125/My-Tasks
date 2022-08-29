package com.trungdunghoang125.mytasks.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class Task {
    @PrimaryKey
    public int taskId;

    @ColumnInfo(name = "task_name")
    public String taskName = "";

    @ColumnInfo(name = "task_detail")
    public String taskDetail = "";

    @ColumnInfo(name = "task_done")
    public Boolean taskDone = false;

    @ColumnInfo(name = "task_importance")
    public Boolean importance = false;

    @ColumnInfo(name = "time_create")
    public Long timeCreate = 0L;

    @ColumnInfo(name = "is_set_alert")
    public Boolean isSetAlert = false;

    @ColumnInfo(name = "task_hour")
    public int hour = -1;

    @ColumnInfo(name = "task_minute")
    public int minute = -1;

    @ColumnInfo(name = "daily_task")
    public Boolean isDailyTask = false;

    public Task(int taskId, String taskName, String taskDetail, Boolean taskDone, Boolean importance, Long timeCreate, Boolean isSetAlert, int hour, int minute, Boolean isDailyTask) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDetail = taskDetail;
        this.taskDone = taskDone;
        this.importance = importance;
        this.timeCreate = timeCreate;
        this.isSetAlert = isSetAlert;
        this.hour = hour;
        this.minute = minute;
        this.isDailyTask = isDailyTask;
    }
}
