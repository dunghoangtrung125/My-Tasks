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
import com.trungdunghoang125.mytasks.databinding.FragmentAddTaskBinding;
import com.trungdunghoang125.mytasks.viewModel.AddTaskViewModel;

public class AddTaskFragment extends Fragment {
    FragmentAddTaskBinding binding;
    AddTaskViewModel viewModel;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // create instance of view model
        viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);

        binding.btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = binding.tvTaskName.getText().toString();
                viewModel.addTask(taskName);
//                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//                navController.navigate(R.id.action_addTaskFragment_to_tasksFragment);
                getActivity().onBackPressed();
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