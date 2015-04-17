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
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
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

        /*Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_directions_bus_black_48dp);
        float[] colorTransform = {
                0, 1f, 0, 0, 0,
                0, 0, 0f, 0, 0,
                0, 0, 0, 0f, 0,
                0, 0, 0, 1f, 0};

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0f); //Remove Colour
        colorMatrix.set(colorTransform); //Apply the Red

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(colorFilter);

        Display display = getWindowManager().getDefaultDisplay();

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, (int)(display.getHeight() * 0.15), display.getWidth(), (int)(display.getHeight() * 0.75));

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);*/


        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                .setColor(context.getResources().getColor(R.color.blue))
                .setContentTitle(title)
                .setContentText(text)
                .addAction(R.mipmap.ic_done_white_48dp,
                        "Acknowledge", null)
                .extend(new Notification.WearableExtender()
                            .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_plusone_tall_off_client))
                )
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);

        Toast.makeText(context, context.getString(R.string.notification_posted), Toast.LENGTH_SHORT).show();
    }
}