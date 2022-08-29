package com.trungdunghoang125.mytasks.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.trungdunghoang125.mytasks.model.TaskRepository;

import java.util.Calendar;

public class TaskAlarm {
    final long DAILY_INTERVAL = 24 * 60 * 60 * 1000;

    public void setTodayTask(Context context, int taskID, Calendar calendar) {
        // if alarm time is pass, don't create alarm
//        if (System.currentTimeMillis() < calendar.getTimeInMillis()) {
//
//        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        intent.putExtra("ALARM", "alarm");
        intent.putExtra("taskId", taskID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, taskID, intent, PendingIntent.FLAG_MUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.v("Alarm", "setTodayTask: ");
    }

    public void setDailyTask(Context context, int taskID, Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt("taskId", taskID);
        intent.putExtra("ALARM", "alarm");
        intent.putExtra("BUNDLE", bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, taskID, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                DAILY_INTERVAL,
                pendingIntent
        );
        Log.v("Alarm", "setDailyTask: ");
    }

    public void cancelTaskAlarm(Context context, int taskID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.v("Alarm", "Cancel");
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        intent.putExtra("ALARM", "cancel");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                taskID,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );
        alarmManager.cancel(pendingIntent);
    }
}
