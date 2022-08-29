package com.trungdunghoang125.mytasks.view.adapter;

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
    private ItemClick itemClick;

    public TaskItemAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, ItemClick itemClick) {
        super(diffCallback);
        this.itemClick = itemClick;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(task.taskId);
            }
        });

        holder.cbTaskDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onCbTaskDoneClick(task, holder.cbTaskDone.isChecked());
            }
        });

        holder.cbImportantTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onCbImportanceClick(task, holder.cbImportantTask.isChecked());
            }
        });
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTaskTitle;
        private final TextView tvTaskDetail;
        private final CheckBox cbTaskDone;
        private final CheckBox cbImportantTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tv_task_name);
            tvTaskDetail = itemView.findViewById(R.id.tv_task_detail);
            cbTaskDone = itemView.findViewById(R.id.cb_task_done);
            cbImportantTask = itemView.findViewById(R.id.cb_star_item);
        }

        void bind(Task task) {
            tvTaskTitle.setText(task.taskName);
            tvTaskDetail.setText(task.taskDetail);
            cbTaskDone.setChecked(task.taskDone);
            cbImportantTask.setChecked(task.importance);
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
            return oldItem.taskName.equals(newItem.taskName)
                    && oldItem.taskDetail.equals(newItem.taskDetail)
                    && oldItem.taskDone.equals(newItem.taskDone)
                    && oldItem.importance.equals(newItem.importance);
        }
    }
}
