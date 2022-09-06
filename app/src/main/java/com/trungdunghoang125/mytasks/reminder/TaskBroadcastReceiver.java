package com.trungdunghoang125.mytasks.reminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.trungdunghoang125.mytasks.R;

public class TaskBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "trungdunghoang125";
    private static final String ALARM_MESSAGE = "alarm";
    private static final String CANCEL_ALARM_MESSAGE = "cancel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("Alarm", "Success");
        Intent intentService = new Intent(context, NotificationService.class);
        int taskId = intent.getIntExtra(context.getString(R.string.task_id_code), 0);
        intentService.putExtra(context.getString(R.string.task_id_code), taskId);
        if (intent.getStringExtra(context.getString(R.string.create_alarm_key)).equals(ALARM_MESSAGE)) {
            context.startService(intentService);
        } else if (intent.getStringExtra(context.getString(R.string.create_alarm_key)).equals(CANCEL_ALARM_MESSAGE)) {
            context.stopService(intentService);
        }
        Log.d(TAG, "onReceive: " + taskId);
    }

    public static PendingIntent createAlarmPendingIntent(Context context, int taskID) {
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        intent.putExtra(context.getString(R.string.create_alarm_key), ALARM_MESSAGE);
        intent.putExtra(context.getString(R.string.task_id_code), taskID);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(context, taskID, intent, PendingIntent.FLAG_IMMUTABLE);
        }
        return pendingIntent;
    }

    public static PendingIntent cancelAlarmPendingIntent(Context context, int taskID) {
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        intent.putExtra(context.getString(R.string.create_alarm_key), CANCEL_ALARM_MESSAGE);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    taskID,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );
        }
        return pendingIntent;
    }
}
