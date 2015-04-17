package com.example.book_a_bus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Notification;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.Toast;


public class MyPostNotificationReceiver extends BroadcastReceiver {
    public static final String CONTENT_KEY = "contentText";

    public MyPostNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent displayIntent = new Intent(context, AcknowledgeActivity.class);
        String title = "Book-A-Bus: Bus No. 43";
        String text = "Please go to the next bus stop.";

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                .setContentTitle(title)
                .setContentText(text)
                .addAction(R.mipmap.ic_done_white_48dp,
                        "Acknowledge", null)
                .extend(new Notification.WearableExtender()

                )
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);

        Toast.makeText(context, context.getString(R.string.notification_posted), Toast.LENGTH_SHORT).show();
    }
}