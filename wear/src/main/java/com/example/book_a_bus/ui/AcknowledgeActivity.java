package com.example.book_a_bus.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_a_bus.R;


public class AcknowledgeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledge);
        TextView notifyTitle = (TextView) findViewById(R.id.notify_title);
        notifyTitle.setText("Testing");
        Toast.makeText(this, "Activity shown", Toast.LENGTH_SHORT).show();

    }

}
