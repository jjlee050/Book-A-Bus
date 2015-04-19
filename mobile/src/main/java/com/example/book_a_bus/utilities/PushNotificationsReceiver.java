package com.example.book_a_bus.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.book_a_bus.R;
import com.example.book_a_bus.ui.MainPageActivity;
import com.example.book_a_bus.ui.NotificationActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PushNotificationsReceiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushReceive(Context context, Intent intent) {
        //To track "App Opens"
        ParseAnalytics.trackAppOpenedInBackground(intent);
        //Here is data you sent
        Log.i("Data: ", intent.getExtras().getString( "com.parse.Data" ));

        String busStopNo = null;
        String title = null;
        String lat = "";
        String lon = "";

        try {
            JSONObject object = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            busStopNo = object.getString("busStopNo").toString();
            title = object.getString("busServiceNo").toString();
            lat = object.getString("lat").toString();
            lon = object.getString("lon").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(busStopNo + ", " + title);

        // prepare intent which is triggered if the
        // notification is selected
        Intent i = new Intent(context, MainPageActivity.class);
        i.putExtra("busStopNo", busStopNo);
        i.putExtra("busServiceNo", title);
        i.putExtra("busDeviceID", "leezx");
        i.putExtra("userID", "testuser");
        PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                .setContentTitle("Book-A-Bus")
                .setContentText("Someone flagged!")
                .addAction(R.mipmap.ic_done_white_48dp,
                        "Acknowledge", pIntent)
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);

    }
}