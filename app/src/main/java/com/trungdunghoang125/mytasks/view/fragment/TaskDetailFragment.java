package com.trungdunghoang125.mytasks.view.fragment;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.databinding.FragmentTaskDetailBinding;
import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.reminder.TaskAlarm;
import com.trungdunghoang125.mytasks.viewModel.TaskDetailViewModel;
import com.trungdunghoang125.mytasks.viewModel.TaskDetailViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class TaskDetailFragment extends Fragment {
    private static final String TAG = "trungdunghoang125";
    private FragmentTaskDetailBinding binding;
    private TaskDetailViewModel viewModel;
    private int hour, minute;
    private Boolean isSetAlert = false;
    private Calendar calendar;
    private int requestCode;
    private TaskAlarm taskAlarm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int taskId = requireArguments().getInt(getString(R.string.task_id_code));
        Log.d(TAG, "onCreateView: " + taskId);

        // create instance of view model by view model factory
        viewModel = new ViewModelProvider(this, new TaskDetailViewModelFactory(getActivity().getApplication(), taskId))
                .get(TaskDetailViewModel.class);

        // update latest data
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage(R.string.alert_tittle)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskAlarm = new TaskAlarm();
                                viewModel.getTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
                                    @Override
                                    public void onChanged(Task task) {
                                        Log.d(TAG, "onChanged: " + "delete confirm");
                                        taskAlarm.cancelTaskAlarm(requireContext(), task.taskId);
                                        task.isSetAlert = false;
                                    }
                                });
                                viewModel.deleteTask();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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

        viewModel.getTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                Log.d(TAG, "onChanged: " + "getTask call");
                if (Objects.requireNonNull(viewModel.getTask().getValue()).isSetAlert) {
                    binding.tvTimeDetail.setVisibility(View.VISIBLE);
                    binding.cbDailyTaskDetail.setVisibility(View.VISIBLE);
                    binding.cbDailyTaskDetail.setChecked(task.isDailyTask);
                    String timeText = "Time alert: " + task.hour + ":" + task.minute;
                    binding.tvTimeDetail.setText(timeText);

                    // set default time for calendar
                    hour = task.hour;
                    minute = task.minute;
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                }
            }
        });

        viewModel.getNavigateBack().observe(getViewLifecycleOwner(), value -> {
            if (value) {
                requireActivity().onBackPressed();
                viewModel.isNavigated();
            }
        });

        binding.btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSetAlert) {
                    Objects.requireNonNull(viewModel.getTask().getValue()).isSetAlert = true;
                    viewModel.getTask().getValue().hour = hour;
                    viewModel.getTask().getValue().minute = minute;
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    requestCode = viewModel.getTask().getValue().taskId;
                    taskAlarm = new TaskAlarm();
                    if (viewModel.getTask().getValue().isDailyTask) {
                        taskAlarm.cancelTaskAlarm(requireContext(), requestCode);
                        taskAlarm.setDailyTask(requireContext(), requestCode, calendar);
                    } else {
                        taskAlarm.cancelTaskAlarm(requireContext(), requestCode);
                        taskAlarm.setTodayTask(requireContext(), requestCode, calendar);
                    }
                }
                viewModel.updateTask();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void updateTimeText(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String timeText = "Time alert: ";
        timeText += simpleDateFormat.format(calendar.getTime());
        binding.tvTimeDetail.setVisibility(View.VISIBLE);
        binding.tvTimeDetail.setText(timeText);
    }
}