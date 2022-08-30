package com.trungdunghoang125.mytasks.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.databinding.FragmentTasksBinding;
import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.reminder.TaskAlarm;
import com.trungdunghoang125.mytasks.view.adapter.ItemClick;
import com.trungdunghoang125.mytasks.view.adapter.TaskItemAdapter;
import com.trungdunghoang125.mytasks.viewModel.TasksViewModel;

import java.util.Calendar;

public class TasksFragment extends Fragment implements ItemClick {
    private FragmentTasksBinding binding;
    public TasksViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // view model instance
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);

        // binding view model and set life cycle to update change on layout immediately
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // set adapter for recycler view
        TaskItemAdapter adapter = new TaskItemAdapter(new TaskItemAdapter.TaskDiff(), this);
        binding.rcViewTasksList.setAdapter(adapter);

        // observer all list livedata
        viewModel.getAll().observe(getViewLifecycleOwner(), tasks -> {
            adapter.submitList(tasks);
            if (tasks.size() == 0) {
                binding.imgAllTaskDone.setVisibility(View.VISIBLE);
            } else binding.imgAllTaskDone.setVisibility(View.GONE);
        });

        viewModel.getNavigateToTask().observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                NavController navController = Navigation.findNavController(requireView());
                Bundle bundle = new Bundle();
                bundle.putInt("taskId", value);
                navController.navigate(R.id.taskDetailFragment, bundle);
                viewModel.onTaskNavigated();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int pos) {
        viewModel.onTaskClicked(pos);
    }

    @Override
    public void onCbTaskDoneClick(Task task, Boolean done) {
        TaskAlarm taskAlarm = new TaskAlarm();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, task.hour);
        calendar.set(Calendar.MINUTE, task.minute);
        calendar.set(Calendar.SECOND, 0);
        if (done && task.isSetAlert) {
            task.isSetAlert = false;
            taskAlarm.cancelTaskAlarm(requireContext(), task.taskId);
        }

        if (!done && task.isSetAlert && task.isDailyTask) {
            task.isSetAlert = true;
            taskAlarm.setDailyTask(requireContext(), task.taskId, calendar);
        }

        if (!done && task.isSetAlert && !task.isDailyTask) {
            task.isSetAlert = false;
            taskAlarm.setTodayTask(requireContext(), task.taskId, calendar);
        }
        viewModel.updateTaskDone(task, done);
    }

    @Override
    public void onCbImportanceClick(Task task, Boolean isImportant) {
        viewModel.updateTaskImportance(task, isImportant);
    }
}