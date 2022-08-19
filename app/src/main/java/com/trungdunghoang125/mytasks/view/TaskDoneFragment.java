package com.trungdunghoang125.mytasks.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.adapter.ItemClick;
import com.trungdunghoang125.mytasks.adapter.TaskItemAdapter;
import com.trungdunghoang125.mytasks.databinding.FragmentTaskDoneBinding;
import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.viewModel.TaskDoneViewModel;

import java.util.List;

public class TaskDoneFragment extends Fragment implements ItemClick {
    FragmentTaskDoneBinding binding;
    TaskDoneViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskDoneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // view model instance
        viewModel = new ViewModelProvider(this).get(TaskDoneViewModel.class);

        // binding view model and set life cycle to update change on layout immediately
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // set adapter for recycler view
        TaskItemAdapter adapter = new TaskItemAdapter(new TaskItemAdapter.TaskDiff(), this);
        binding.rcViewTasksDone.setAdapter(adapter);

        // observer live data
        viewModel.getAllDoneTasks().observe(getViewLifecycleOwner(), value -> {
            adapter.submitList(value);
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
    public void onItemLongClick(Long pos) {

    }
}