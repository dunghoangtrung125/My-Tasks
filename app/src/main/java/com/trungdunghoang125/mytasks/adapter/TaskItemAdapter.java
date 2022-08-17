package com.trungdunghoang125.mytasks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.model.Task;

public class TaskItemAdapter extends ListAdapter<Task, TaskItemAdapter.TaskViewHolder> {

    public TaskItemAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TaskViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = getItem(position);
        holder.bind(task);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTaskTitle;
        private final CheckBox cbTaskDone;

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

    public static class TaskDiff extends DiffUtil.ItemCallback<Task> {

        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.taskId == newItem.taskId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.taskName.equals(newItem.taskName);
        }
    }
}
