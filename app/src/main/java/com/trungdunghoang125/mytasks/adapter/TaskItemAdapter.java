package com.trungdunghoang125.mytasks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder> {
    List<Task> data = new ArrayList<>();

    public void setData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TaskViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = data.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTaskTitle;
        private CheckBox cbTaskDone;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tv_task_name);
            cbTaskDone = itemView.findViewById(R.id.cb_task_done);
        }

        void bind(Task task) {
            tvTaskTitle.setText(task.taskName);
            if (task.taskDone != null) {
                cbTaskDone.setChecked(task.taskDone);
            }
        }

        static TaskViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);
            return new TaskViewHolder(view);
        }
    }
}
