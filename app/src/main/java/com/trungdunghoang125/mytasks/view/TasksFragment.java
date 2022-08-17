package com.trungdunghoang125.mytasks.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.adapter.TaskItemAdapter;
import com.trungdunghoang125.mytasks.databinding.FragmentTasksBinding;
import com.trungdunghoang125.mytasks.viewModel.TasksViewModel;

public class TasksFragment extends Fragment {
    private FragmentTasksBinding binding;
    public TasksViewModel viewModel;

    public TasksFragment() {
        // Required empty public constructor
    }

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
        TaskItemAdapter adapter = new TaskItemAdapter(new TaskItemAdapter.TaskDiff());
        binding.rcViewTasksList.setAdapter(adapter);

        // observer livedata
        viewModel.getAll().observe(getViewLifecycleOwner(), tasks -> {
            adapter.submitList(tasks);
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_tasksFragment_to_addTaskFragment);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}