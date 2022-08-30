package com.trungdunghoang125.mytasks.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TaskBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "tranle1811";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("Alarm", "Success");
        Intent intentService = new Intent(context, NotificationService.class);
        int taskId = intent.getIntExtra("taskId", 0);
        intentService.putExtra("taskId", taskId);
        if (intent.getStringExtra("ALARM").equals("alarm")) {
            context.startService(intentService);
        } else if (intent.getStringExtra("ALARM").equals("cancel")) {
            context.stopService(intentService);
        }
        Log.d(TAG, "onReceive: " + taskId);
    }
}
