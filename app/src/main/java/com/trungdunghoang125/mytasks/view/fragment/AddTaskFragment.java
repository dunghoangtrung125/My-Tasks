package com.trungdunghoang125.mytasks.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.trungdunghoang125.mytasks.databinding.FragmentAddTaskBinding;
import com.trungdunghoang125.mytasks.viewModel.AddTaskViewModel;

public class AddTaskFragment extends Fragment {
    FragmentAddTaskBinding binding;
    AddTaskViewModel viewModel;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // create instance of view model
        viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);

        binding.btnSaveTask.setOnClickListener(view1 -> {
            String taskName = binding.tvTaskName.getText().toString();
            String taskDescription = binding.edtAddTaskDetail.getText().toString();
            Boolean taskImportance = binding.cbStarButton.isChecked();
            if (taskName.length() != 0) {
                binding.btnSaveTask.setTextColor(android.R.color.holo_blue_light);
                viewModel.addTask(taskName, taskDescription, taskImportance);
                requireActivity().onBackPressed();
            } else
                Toast.makeText(getContext(), "Please enter a task name", Toast.LENGTH_SHORT).show();
        });

        binding.imgShowDetail.setOnClickListener(view1 -> binding.edtAddTaskDetail.setVisibility(View.VISIBLE));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}