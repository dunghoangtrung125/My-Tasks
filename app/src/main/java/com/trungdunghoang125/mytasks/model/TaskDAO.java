package com.trungdunghoang125.mytasks.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks_table WHERE taskId = :taskId")
    LiveData<Task> get(int taskId);

    @Query("SELECT * FROM tasks_table WHERE task_done = 0 ORDER BY task_importance = 0, task_hour, task_minute ASC")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM tasks_table WHERE task_done = 1 ORDER BY time_create DESC")
    LiveData<List<Task>> getDoneTasks();

    @Query("SELECT * FROM tasks_table WHERE taskId = :taskId")
    Task getTaskByID(int taskId);
}
