package com.trungdunghoang125.mytasks.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.trungdunghoang125.mytasks.R;
import com.trungdunghoang125.mytasks.model.Task;
import com.trungdunghoang125.mytasks.model.TaskRepository;
import com.trungdunghoang125.mytasks.view.activity.MainActivity;


public class NotificationService extends Service {
    private static final String TAG = "trungdunghoang125";
    private static final String CHANNEL_ID = "NOTIFICATION_SERVICE_CHANNEL";
    private static final int NOTIFICATION_CHANNEL_ID = 1;
    private static final String NOTIFICATION_BUTTON_TITTLE = "Mark as completed";
    private static final String CANCEL_ALARM_MESSAGE = "cancel";
    private Task task;
    private TaskRepository repository;
    private int taskID;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d(TAG, "onStartCommand: " + "call service success");
        taskID = intent.getIntExtra(getString(R.string.task_id_code), 0);

        // query task from database by repository
        repository = new TaskRepository(getApplication());
        new Thread(() -> {
            task = repository.getTaskByID(taskID);
        }).start();

        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.task_id_code), taskID);

        PendingIntent fullScreenPendingIntent = new NavDeepLinkBuilder(getApplicationContext())
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.taskDetailFragment)
                .setArguments(bundle)
                .createPendingIntent();

        PendingIntent cancelPendingIntent = TaskBroadcastReceiver.cancelAlarmPendingIntent(this, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(task.taskName)
                .setContentText(task.taskDetail)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .addAction(R.drawable.ic_done, NOTIFICATION_BUTTON_TITTLE, cancelPendingIntent)
                .setFullScreenIntent(fullScreenPendingIntent, true);

        Notification taskNotification = notificationBuilder.build();

        startForeground(NOTIFICATION_CHANNEL_ID, taskNotification);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        new Thread(() -> {
            task = repository.getTaskByID(taskID);
        }).start();
        task.taskDone = true;
        repository.update(task);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}