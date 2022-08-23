package com.trungdunghoang125.mytasks.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.trungdunghoang125.mytasks.databinding.FragmentTaskDetailBinding;
import com.trungdunghoang125.mytasks.viewModel.TaskDetailViewModel;
import com.trungdunghoang125.mytasks.viewModel.TaskDetailViewModelFactory;

public class TaskDetailFragment extends Fragment {
    FragmentTaskDetailBinding binding;
    TaskDetailViewModel viewModel;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Long taskId = getArguments().getLong("taskId");

        // create instance of view model by view model factory
        viewModel = new ViewModelProvider(this, new TaskDetailViewModelFactory(getActivity().getApplication(), taskId))
                .get(TaskDetailViewModel.class);

        // update latest data
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewModel.deleteTask();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        });

        viewModel.getNavigateBack().observe(getViewLifecycleOwner(), value -> {
            if (value) {
                requireActivity().onBackPressed();
                viewModel.isNavigated();
            }
        });

        return view;
    }
}