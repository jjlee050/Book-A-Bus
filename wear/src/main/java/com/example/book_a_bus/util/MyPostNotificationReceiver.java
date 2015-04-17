package com.example.book_a_bus.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Toast;

import com.example.book_a_bus.R;
import com.example.book_a_bus.ui.AcknowledgeActivity;


public class MyPostNotificationReceiver extends BroadcastReceiver {
    public static final String CONTENT_KEY = "contentText";

    public MyPostNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent displayIntent = new Intent(context, AcknowledgeActivity.class);
        String title = "Book-A-Bus: Bus No. 43";
        String text = "Please go to the next bus stop.";

        int w = 300, h = 300;

        Bitmap.Config conf = Bitmap.Config.RGB_565; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        canvas.drawARGB(1,2,117,213);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                .setContentTitle(title)
                .setContentText(text)
                .addAction(R.mipmap.ic_done_white_48dp,
                        "Acknowledge", null)
                .extend(new Notification.WearableExtender()
                                .setBackground(bmp)
                )
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);

        Toast.makeText(context, context.getString(R.string.notification_posted), Toast.LENGTH_SHORT).show();
    }
}