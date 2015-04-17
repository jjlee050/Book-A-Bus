package com.example.book_a_bus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class MyStubBroadcastActivity extends Activity {
    @Override
    protected void onStart() {
        super.onStart();

        Intent i = new Intent();
        i.setAction("com.example.book_a_bus.SHOW_NOTIFICATION");
        i.putExtra(MyPostNotificationReceiver.CONTENT_KEY, "Please go to the next bus stop.");
        sendBroadcast(i);
        finish();
    }
}
