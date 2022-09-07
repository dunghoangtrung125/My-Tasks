package com.trungdunghoang125.mytasks;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.trungdunghoang125.mytasks.reminder.AlertDialogCallback;

/**
 * Created by trungdunghoang125 on 06/09/2022
 */
public class Utilities {
    public static class AlertDeleteTask {
        public static void Notice(Context context, AlertDialogCallback alertDialogCallback) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.alert_tittle)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialogCallback.delete();
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
    }

    public static class ShowToast {
        public static void initToast(Context context, Toast toast, String message) {
            cancelToast(toast);
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }

        public static void cancelToast(Toast toast) {
            if (toast != null) {
                toast.cancel();
            }
        }
    }
}
