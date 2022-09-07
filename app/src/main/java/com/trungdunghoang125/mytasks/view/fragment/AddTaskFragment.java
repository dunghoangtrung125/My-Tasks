package com.trungdunghoang125.mytasks.view.fragment;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.trungdunghoang125.mytasks.Utilities;
import com.trungdunghoang125.mytasks.databinding.FragmentAddTaskBinding;
import com.trungdunghoang125.mytasks.reminder.TaskAlarm;
import com.trungdunghoang125.mytasks.viewModel.AddTaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddTaskFragment extends Fragment {
    private FragmentAddTaskBinding binding;
    private AddTaskViewModel viewModel;
    private int hour, minute;
    private Calendar calendar;
    private int requestCode = -1;
    private Long systemMillis = 0L;
    private Boolean isSetAlert = false;
    private Toast toast;

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
            Boolean isDailyTask = binding.cbDailyTask.isChecked();
            if (taskName.length() != 0) {
                binding.btnSaveTask.setTextColor(android.R.color.holo_blue_light);
                int ID = new Random().nextInt(Integer.MAX_VALUE);
                requestCode = ID;
                systemMillis = System.currentTimeMillis();
                viewModel.addTask(ID, taskName, taskDescription, false, taskImportance, systemMillis, isSetAlert, hour, minute, isDailyTask);
                requireActivity().onBackPressed();

                boolean everydayTask = binding.cbDailyTask.isChecked();
                if (requestCode != -1) {
                    if (everydayTask) {
                        setDailyTaskAlarm();
                    } else setTodayTask();
                }
            } else {
                Utilities.ShowToast.initToast(requireContext(), toast, "Please enter a task name");
            }
        });

        binding.imgShowDetail.setOnClickListener(view1 -> binding.edtAddTaskDetail.setVisibility(View.VISIBLE));

        binding.btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                        hour = selectHour;
                        minute = selectMinute;
                        isSetAlert = true;
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        updateTimeText(calendar);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setTodayTask() {
        TaskAlarm taskAlarm = new TaskAlarm();
        if (calendar != null) {
            taskAlarm.setTodayTask(requireContext(), requestCode, calendar);
        }
    }

    private void setDailyTaskAlarm() {
        TaskAlarm taskAlarm = new TaskAlarm();
        if (calendar != null) {
            taskAlarm.setDailyTask(requireContext(), requestCode, calendar);
        }
    }

    private void updateTimeText(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String timeText = "Time alert: ";
        timeText += simpleDateFormat.format(calendar.getTime());
        binding.tvTime.setVisibility(View.VISIBLE);
        binding.tvTime.setText(timeText);
    }
}