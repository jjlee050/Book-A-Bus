package com.example.book_a_bus.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.book_a_bus.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class NotificationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedInstanceState = getIntent().getExtras();
        String busStopNo = savedInstanceState.getString("busStopNo");
        String title = savedInstanceState.getString("busServiceNo");

        ParseQuery query = ParseQuery.getQuery("Flag");
        query.whereEqualTo("busStopNo", busStopNo);
        query.whereEqualTo("busServiceNo", title);
        query.whereEqualTo("busDeviceID", "leezx");
        query.whereEqualTo("userID", "testuser");
        try{
            List<ParseObject> objectArrList = query.find();
            if(objectArrList.size() > 0){
                for(int i = 0;i < objectArrList.size();i++){
                    objectArrList.get(i).delete();
                }
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(0);
                Intent i = new Intent(this, MainPageActivity.class);
                startActivity(i);
                finish();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}