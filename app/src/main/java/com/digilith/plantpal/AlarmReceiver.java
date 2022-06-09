package com.digilith.plantpal;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    // TODO: receive plant name
    @Override
    public void onReceive(Context context, Intent intent) {
        // Intent after clicking
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);


        // Notification itself
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, "PlantPal")
                .setSmallIcon(R.drawable.ic_baseline_eco_24)
                .setContentTitle("PlantPal Watering Notification")
                .setContentText("Time to water your plants!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());

    }
}
