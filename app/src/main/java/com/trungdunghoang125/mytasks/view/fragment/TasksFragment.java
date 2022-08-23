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
import com.trungdunghoang125.mytasks.view.adapter.ItemClick;
import com.trungdunghoang125.mytasks.view.adapter.TaskItemAdapter;
import com.trungdunghoang125.mytasks.viewModel.TasksViewModel;

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
                NavController navController = Navigation.findNavController(getView());
                Bundle bundle = new Bundle();
                bundle.putLong("taskId", value);
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
    public void onItemClick(Long pos) {
        viewModel.onTaskClicked(pos);
    }

    @Override
    public void onCbTaskDoneClick(Task task, Boolean state) {
        viewModel.updateTaskDone(task, state);
    }

    @Override
    public void onCbImportanceClick(Task task, Boolean isImportant) {
        viewModel.updateTaskImportance(task, isImportant);
    }
}