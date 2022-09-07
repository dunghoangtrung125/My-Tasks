package com.trungdunghoang125.mytasks.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;

public class TaskAlarm {
    private static final String TAG = "trungdunghoang125";

    public void setTodayTask(Context context, int taskID, Calendar calendar) {
        // if alarm time is pass, don't create alarm
        if (System.currentTimeMillis() < calendar.getTimeInMillis()) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = TaskBroadcastReceiver.createAlarmPendingIntent(context, taskID);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.v(TAG, "setTodayTask: ");
        }
    }

    public void setDailyTask(Context context, int taskID, Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = TaskBroadcastReceiver.createAlarmPendingIntent(context, taskID);
        long DAILY_INTERVAL = 24 * 60 * 60 * 1000;
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                DAILY_INTERVAL,
                pendingIntent
        );
        Log.v(TAG, "setDailyTask: ");
    }

    public void cancelTaskAlarm(Context context, int taskID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d(TAG, "Cancel " + taskID);
        PendingIntent pendingIntent = TaskBroadcastReceiver.cancelAlarmPendingIntent(context, taskID);
        alarmManager.cancel(pendingIntent);
    }
}